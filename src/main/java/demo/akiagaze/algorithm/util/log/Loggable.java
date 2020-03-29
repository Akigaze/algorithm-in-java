package demo.akiagaze.algorithm.util.log;

public abstract class Loggable {
  private boolean loggable;

  public void setLoggable(boolean loggable) {
    this.loggable = loggable;
  }

  protected void log(String template, Object... args) {
    if (loggable) {
      System.out.println(String.format(template, args));
    }
  }
}
