package demo.akiagaze.algorithm.constant;

public class Sort {

  public enum Direction {
    ASC(1), DESC(-1);

    public final int positive;

    Direction(int positive) {
      this.positive = positive;
    }
  }
}
