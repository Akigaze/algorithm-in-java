package demo.akiagaze.algorithm.operation.bit;

import org.junit.Test;

import static org.junit.Assert.*;

public class BitwiseShiftOperationTest {
  @Test
  public void test_left_shift() {
    assertEquals(0, BitwiseShiftOperation.leftShift(0L, 1));
    assertEquals(2, BitwiseShiftOperation.leftShift(1L, 1));
    assertEquals(4, BitwiseShiftOperation.leftShift(2L, 1));
    assertEquals(8, BitwiseShiftOperation.leftShift(2L, 2));

    assertEquals(0, BitwiseShiftOperation.leftShift(-0L, 1));
    assertEquals(-2, BitwiseShiftOperation.leftShift(-1L, 1));
    assertEquals(-4, BitwiseShiftOperation.leftShift(-2L, 1));
    assertEquals(-8, BitwiseShiftOperation.leftShift(-2L, 2));
  }

  @Test
  public void test_right_shift() {
    assertEquals(0, BitwiseShiftOperation.rightShift(0L, 1));
    assertEquals(0, BitwiseShiftOperation.rightShift(1L, 1));
    assertEquals(0, BitwiseShiftOperation.rightShift(1L, 2));
    assertEquals(1, BitwiseShiftOperation.rightShift(2L, 1));
    assertEquals(0, BitwiseShiftOperation.rightShift(2L, 2));
    assertEquals(4, BitwiseShiftOperation.rightShift(8L, 1));
    assertEquals(2, BitwiseShiftOperation.rightShift(8L, 2));
    assertEquals(1, BitwiseShiftOperation.rightShift(8L, 3));
    assertEquals(0, BitwiseShiftOperation.rightShift(8L, 4));

    assertEquals(0, BitwiseShiftOperation.rightShift(-0L, 1));
    assertEquals(-1, BitwiseShiftOperation.rightShift(-1L, 1));
    assertEquals(-1, BitwiseShiftOperation.rightShift(-2L, 1));
    assertEquals(-1, BitwiseShiftOperation.rightShift(-2L, 2));
    assertEquals(-2, BitwiseShiftOperation.rightShift(-8L, 2));
  }
}