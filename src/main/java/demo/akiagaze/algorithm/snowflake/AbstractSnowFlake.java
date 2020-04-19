package demo.akiagaze.algorithm.snowflake;

import java.util.Properties;

public abstract class AbstractSnowFlake implements SnowFlake {
  private Properties props;

  public AbstractSnowFlake(Properties props) {
    this.props = props;
  }

  public Properties getProps() {
    return props;
  }

  public void setProps(Properties props) {
    props.forEach((k, v) -> this.props.put(k, v));
  }

  public String getProperty(String key, String defaultValue) {
    return props.getProperty(key, defaultValue);
  }

  public String getProperty(SnowFlakeProperty property) {
    return props.getProperty(property.key, property.defaultValue);
  }

  public static class WaitToleranceTimeDifferenceException extends RuntimeException {
    public WaitToleranceTimeDifferenceException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public enum SnowFlakeProperty {
    MAX_SEQUENCE_VIBRATION_OFFSET("max.sequence.vibration.offset", 63),
    MAX_TIME_TOLERANCE_DIFFERENCE("max.time.tolerance.difference", 10),
    WORKER_ID("worker.id", 0);

    public final String key;
    public final String defaultValue;

    SnowFlakeProperty(String key, Object defaultValue) {
      this.key = key;
      this.defaultValue = String.valueOf(defaultValue);
    }
  }
}
