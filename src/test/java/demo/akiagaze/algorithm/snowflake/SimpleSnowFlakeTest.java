package demo.akiagaze.algorithm.snowflake;

import org.junit.Test;

import java.time.Instant;
import java.util.Properties;

public class SimpleSnowFlakeTest {
  @Test
  public void test_generateKey_1() throws InterruptedException {
    SnowFlake snowFlake = new SimpleSnowFlake(Instant.now().toString());
    Properties props = new Properties();
    props.put(SimpleSnowFlake.SnowFlakeProperty.MAX_SEQUENCE_VIBRATION_OFFSET.key, "3");
    snowFlake.setProps(props);
    Thread[] threadPool = new Thread[10];
    for (int n = 0; n < threadPool.length; n++) {

      threadPool[n] = new Thread("T_" + n) {
        @Override
        public void run() {
          for (int i = 0; i < 100; i++) {
            long key = snowFlake.generateKey();
//            System.out.println(String.format("[%s] %d", this.getName(), key));
          }
        }
      };
    }

    for (Thread thread : threadPool) {
      thread.start();
    }
    for (Thread thread : threadPool) {
      thread.join();
    }
  }
}