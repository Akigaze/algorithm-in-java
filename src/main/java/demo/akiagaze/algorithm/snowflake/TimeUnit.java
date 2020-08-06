package demo.akiagaze.algorithm.snowflake;

public enum TimeUnit {
  millisecond(1L, 8, true, "ms"),
  second(1000L, 16, true, "s"),
  minute(60 * 1000L, 20, false, "min");

  TimeUnit(long rate, int sequenceBits, boolean tolerable, String unit) {
    this.rate = rate;
    this.sequenceBits = sequenceBits;
    this.tolerable = tolerable;
    this.unit = unit;
  }

  private final long rate;
  private final int sequenceBits;
  private final boolean tolerable;
  private final String unit;

  public long getRate() {
    return rate;
  }

  public int getSequenceBits() {
    return sequenceBits;
  }

  public boolean isTolerable() {
    return tolerable;
  }

  public String getUnit() {
    return unit;
  }
}
