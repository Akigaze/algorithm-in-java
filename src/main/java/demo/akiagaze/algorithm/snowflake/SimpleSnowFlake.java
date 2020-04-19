package demo.akiagaze.algorithm.snowflake;

import demo.akiagaze.algorithm.util.assertion.Assert;

import java.time.Instant;
import java.util.Properties;

import static demo.akiagaze.algorithm.snowflake.AbstractSnowFlake.SnowFlakeProperty.*;

public class SimpleSnowFlake extends AbstractSnowFlake {

  private final int SEQUENCE_BIT = 12;
  private final int WORKER_ID_SHIFT_BITS = SEQUENCE_BIT;
  private final int WORKER_ID_BIT = 10;
  private final int TIMESTAMP_SHIFT_BITS = WORKER_ID_BIT + SEQUENCE_BIT;

  private final long SEQUENCE_MASK = (1 << SEQUENCE_BIT) - 1;
  private final long MAX_WORKER_ID = (1 << WORKER_ID_BIT) - 1;
  private final long EPOCH;

  private long lastMilliseconds;
  private long sequence;
  private long sequenceOffset;

  public SimpleSnowFlake() {
    this("2020-01-01T00:00:00Z");
  }

  public SimpleSnowFlake(String epochTime) {
    super(new Properties());
    Instant epochInstant = Instant.parse(epochTime);
    EPOCH = epochInstant.toEpochMilli();
  }

  @Override
  public synchronized long generateKey() {
    long currentTimeMillis = System.currentTimeMillis();
    if (this.waitToleranceTimeDifferenceIfNeed(currentTimeMillis)) {
      currentTimeMillis = System.currentTimeMillis();
    }
    if (lastMilliseconds == currentTimeMillis) {
      if (0 == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
        currentTimeMillis = this.waitUntilNextMillisecond(currentTimeMillis);
      }
    } else {
      this.vibrateSequenceOffset();
      sequence = sequenceOffset;
    }
    lastMilliseconds = currentTimeMillis;
    long key = ((currentTimeMillis - EPOCH) << TIMESTAMP_SHIFT_BITS) | (this.getWorkerId() << WORKER_ID_SHIFT_BITS) | sequence;
    System.out.println(String.format("[Generate key] thread %s, key: %d, time: %d, sequence: %d, offset: %d", Thread.currentThread().getName(), key, currentTimeMillis, sequence, sequenceOffset));
    return key;
  }

  /**
   * 避免不同毫秒之间生成的key都是2^n(workerId的位数)的整数倍，加入sequence的偏移量，让key更多样，更复杂
   */
  private void vibrateSequenceOffset() {
    sequenceOffset = sequenceOffset >= this.getMaxSequenceVibrationOffset() ? 0 : sequenceOffset + 1;
  }

  private long waitUntilNextMillisecond(final long lastMillis) {
    long currentMillis = System.currentTimeMillis();
    while (currentMillis <= lastMillis) {
      System.out.println(String.format("[Wait] wait until next time, current time: %d", lastMillis));
      currentMillis = System.currentTimeMillis();
    }
    return currentMillis;
  }

  private boolean waitToleranceTimeDifferenceIfNeed(long currentTimeMillis) {
    if (lastMilliseconds <= currentTimeMillis) {
      return false;
    }
    long timeDifference = lastMilliseconds - currentTimeMillis;
    long maxTolerance = this.getMaxTimeToleranceDifference();
    Assert.isTrue(timeDifference < maxTolerance, "[Time Tolerance] Exceed max time tolerance: %d ms, last time is %d ms, current time is %d ms", maxTolerance, lastMilliseconds, currentTimeMillis);
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
}
