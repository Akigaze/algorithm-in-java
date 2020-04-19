package demo.akiagaze.algorithm.util.assertion;

public class Assert {
  public static void isTrue(boolean expression, String message, Object ...args){
    if (!expression){
      throw new IllegalStateException(String.format(message, args));
    }
  }
}
