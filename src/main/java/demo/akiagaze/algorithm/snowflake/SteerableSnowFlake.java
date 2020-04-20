package demo.akiagaze.algorithm.snowflake;

import demo.akiagaze.algorithm.util.assertion.Assert;

import java.time.Instant;
import java.util.Properties;

import static demo.akiagaze.algorithm.snowflake.AbstractSnowFlake.SnowFlakeProperty.*;

public class SteerableSnowFlake extends AbstractSnowFlake {

  private final int SEQUENCE_BIT;
  private final int WORKER_ID_SHIFT_BITS;
  private final int WORKER_ID_BIT;
  private final int TIMESTAMP_SHIFT_BITS;

  private final long SEQUENCE_MASK;
  private final long MAX_WORKER_ID;
  private final long EPOCH;

  private long lastTime;
  private long sequence;
  private long sequenceOffset;

  private final TimeGrading grading;

  public SteerableSnowFlake() {
    this("2020-01-01T00:00:00Z");
  }

  public SteerableSnowFlake(String epochTime) {
    this(epochTime, 10, TimeGrading.second);
  }

  public SteerableSnowFlake(String epochTime, int workerBits, TimeGrading grading) {
    super(new Properties());
    this.grading = grading;

    Instant epochInstant = Instant.parse(epochTime);
    EPOCH = epochInstant.toEpochMilli() / grading.rate;

    WORKER_ID_BIT = workerBits;
    SEQUENCE_BIT = grading.sequenceBits;

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
    System.out.println(String.format("[Generate key] thread %s, key: %d, time: %d, sequence: %d, offset: %d", Thread.currentThread().getName(), key, currentTime, sequence, sequenceOffset));
    return key;
  }

  private long getCurrentTime() {
    return System.currentTimeMillis() / grading.rate;
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
      System.out.println(String.format("[Wait] wait until next time, current time: %d", lastTime));
      currentTime = this.getCurrentTime();
    }
    return currentTime;
  }

  private boolean waitToleranceTimeDifferenceIfNeed(long currentTime) {
    if (lastTime <= currentTime) {
      return false;
    }
    long timeDifference = lastTime - currentTime;
    Assert.isTrue(grading.waiting, "[Time Tolerance] not allow to wait for <%s> grading, time difference: %d %s", grading, timeDifference, grading.unit);
    long maxTolerance = this.getMaxTimeToleranceDifference();
    Assert.isTrue(timeDifference < maxTolerance, "[Time Tolerance] Exceed max time tolerance: %d %s, last time is %d %s, current time is %d %s", maxTolerance, grading.unit, lastTime, grading.unit, currentTime, grading.unit);
    this.waitTimeDifference(timeDifference);
    return true;
  }

  private void waitTimeDifference(long timeDifference) {
    try {
      Thread.sleep(timeDifference);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new WaitToleranceTimeDifferenceException("[Time Tolerance] exception occurred when sleep: " + timeDifference + " ms", e);
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

  public enum TimeGrading {
    millisecond(1L, 8, true, "ms"),
    second(1000L, 16, true, "s"),
    minute(60 * 1000L, 20, false, "min");

    TimeGrading(long rate, int sequenceBits, boolean waiting, String unit) {
      this.rate = rate;
      this.sequenceBits = sequenceBits;
      this.waiting = waiting;
      this.unit = unit;
    }

    public long rate;
    public int sequenceBits;
    public boolean waiting;
    public String unit;

  }
}
