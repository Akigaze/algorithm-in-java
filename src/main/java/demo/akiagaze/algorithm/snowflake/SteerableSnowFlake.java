package demo.akiagaze.algorithm.snowflake;

import demo.akiagaze.algorithm.util.assertion.Assert;

import java.time.Instant;
import java.util.Properties;

import static demo.akiagaze.algorithm.snowflake.AbstractSnowFlake.SnowFlakeProperty.*;

public class SteerableSnowFlake extends AbstractSnowFlake {

  public final int SEQUENCE_BIT;
  public final int WORKER_ID_SHIFT_BITS;
  public final int WORKER_ID_BIT;
  public final int TIMESTAMP_SHIFT_BITS;

  public final long SEQUENCE_MASK;
  public final long MAX_WORKER_ID;
  public final long EPOCH;

  private long lastTime;
  private long sequence;
  private long sequenceOffset;

  private final TimeUnit timeUnit;

  public SteerableSnowFlake() {
    this("2020-01-01T00:00:00Z");
  }

  public SteerableSnowFlake(String epochTime) {
    this(epochTime, 10, TimeUnit.second);
  }

  public SteerableSnowFlake(String epochTime, int workerBits, TimeUnit timeUnit) {
    super(new Properties());
    this.timeUnit = timeUnit;

    Instant epochInstant = Instant.parse(epochTime);
    EPOCH = epochInstant.toEpochMilli() / timeUnit.getRate();

    WORKER_ID_BIT = workerBits;
    SEQUENCE_BIT = timeUnit.getSequenceBits();

    WORKER_ID_SHIFT_BITS = SEQUENCE_BIT;
    TIMESTAMP_SHIFT_BITS = WORKER_ID_BIT + SEQUENCE_BIT;
    SEQUENCE_MASK = (1 << SEQUENCE_BIT) - 1;
    MAX_WORKER_ID = (1 << WORKER_ID_BIT) - 1;
  }

  @Override
  public synchronized long generateKey() {
    long currentTime = this.getCurrentTime();
    if (this.waitToleranceTimeDifferenceIfNeed(currentTime)) {
      currentTime = this.getCurrentTime();
    }
    if (lastTime == currentTime) {
      if (0 == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
        if(!this.isSequenceFullUtilizationEnabled()){
          currentTime = this.waitUntilNextTime(currentTime);
        }
      }
    } else {
      this.vibrateSequenceOffset();
      sequence = sequenceOffset;
    }
    lastTime = currentTime;
    long key = ((currentTime - EPOCH) << TIMESTAMP_SHIFT_BITS) | (this.getWorkerId() << WORKER_ID_SHIFT_BITS) | sequence;
    System.out.printf("[Generate key] thread %s, key: %d, time: %d, sequence: %d, offset: %d%n", Thread.currentThread().getName(), key, currentTime, sequence, sequenceOffset);
    return key;
  }

  private long getCurrentTime() {
    return System.currentTimeMillis() / this.getTimeUnitRate();
  }

  /**
   * 避免不同毫秒之间生成的key都是2^n(workerId的位数)的整数倍，加入sequence的偏移量，让key更多样，更复杂
   */
  private void vibrateSequenceOffset() {
    sequenceOffset = sequenceOffset >= this.getMaxSequenceVibrationOffset() ? 0 : sequenceOffset + 1;
  }

  private long waitUntilNextTime(final long lastTime) {
    long currentTime = this.getCurrentTime();
    while (currentTime <= lastTime) {
      System.out.printf("[Wait] wait until next time, current time: %d%n", lastTime);
      currentTime = this.getCurrentTime();
    }
    return currentTime;
  }

  private boolean waitToleranceTimeDifferenceIfNeed(long currentTime) {
    if (lastTime <= currentTime) {
      return false;
    }
    long timeDifference = lastTime - currentTime;
    TimeUnit timeUnit = this.getTimeUnit();
    String unit = timeUnit.getUnit();
    Assert.isTrue(this.tolerable(), "[Time Tolerance] not allow to wait for <%s> grading, time difference: %d %s", timeUnit, timeDifference, unit);
    long maxTolerance = this.getMaxTimeToleranceDifference();
    Assert.isTrue(timeDifference < maxTolerance, "[Time Tolerance] Exceed max time tolerance: %d %s, last time is %d %s, current time is %d %s", maxTolerance, unit, lastTime, unit, currentTime, unit);
    this.waitTimeDifference(timeDifference);
    return true;
  }

  private void waitTimeDifference(long timeDifference) {
    long sleepMillis = timeDifference * this.getTimeUnitRate();
    try {
      Thread.sleep(sleepMillis);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new WaitToleranceTimeDifferenceException("[Time Tolerance] exception occurred when sleep: " + sleepMillis + " ms", e);
    }
  }

  private long getMaxSequenceVibrationOffset() {
    long maxVibrationOffset = Long.parseLong(this.getProperty(MAX_SEQUENCE_VIBRATION_OFFSET));
    return Math.min(maxVibrationOffset, SEQUENCE_MASK);
  }

  private long getMaxTimeToleranceDifference() {
    return Long.parseLong(this.getProperty(MAX_TIME_TOLERANCE_DIFFERENCE));
  }

  private long getWorkerId() {
    long workerId = Long.parseLong(this.getProperty(WORKER_ID));
    return Math.min(workerId, MAX_WORKER_ID);
  }

  private boolean isSequenceFullUtilizationEnabled() {
    return Boolean.parseBoolean(this.getProperty(SEQUENCE_FULL_UTILIZATION_ENABLED));
  }

  public boolean tolerable() {
    return this.getTimeUnit().isTolerable();
  }

  public long getTimeUnitRate() {
    return this.getTimeUnit().getRate();
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }
}
