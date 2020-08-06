package demo.akiagaze.algorithm.snowflake;

import org.junit.Test;

import java.time.Instant;
import java.util.Properties;

import static org.junit.Assert.*;

public class SteerableSnowFlakeTest {
  @Test
  public void test_generateKey_1() throws InterruptedException {
    SnowFlake snowFlake = new SteerableSnowFlake(Instant.now().toString(), 4, TimeUnit.second);
    Properties props = new Properties();
    props.put(SimpleSnowFlake.SnowFlakeProperty.MAX_SEQUENCE_VIBRATION_OFFSET.key, "10");
    snowFlake.setProps(props);
    Thread[] threadPool = new Thread[10];
    for (int n = 0; n < threadPool.length; n++) {

      threadPool[n] = new Thread("T_" + n) {
        @Override
        public void run() {
          for (int i = 0; i < 20; i++) {
            long key = snowFlake.generateKey();
//            System.out.println(String.format("[%s] %d", this.getName(), key));
          }
        }
      };
    }

    for (Thread thread : threadPool) {
      thread.start();
    }
    Thread.sleep(3000);

    for (int n = 0; n < threadPool.length; n++) {

      threadPool[n] = new Thread("S_" + n) {
        @Override
        public void run() {
          for (int i = 0; i < 20; i++) {
            long key = snowFlake.generateKey();
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
