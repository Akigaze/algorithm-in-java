package demo.akiagaze.algorithm.snowflake;

import java.util.Properties;

public interface SnowFlake {
  long generateKey();

  void setProps(Properties props);
}
