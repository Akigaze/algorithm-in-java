package demo.akiagaze.algorithm.operation.bit;

public class BitwiseShiftOperation {
  public static long leftShift(long shift, int additive) {
    return shift << additive;
  }

  public static long rightShift(long shift, int additive) {
    return shift >> additive;
  }
}
