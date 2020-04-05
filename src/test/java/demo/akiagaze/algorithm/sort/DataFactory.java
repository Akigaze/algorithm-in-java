package demo.akiagaze.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataFactory {
  public static final int BIG_ARRAY_LENGTH = 10000;

  private static final int[] BIG_ARRAY = new int[BIG_ARRAY_LENGTH];

  public static final int[] EXPECTED_BIG_ARRAY = new int[BIG_ARRAY_LENGTH];

  static {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < BIG_ARRAY_LENGTH; i++) {
      list.add(i);
      EXPECTED_BIG_ARRAY[i] = i;
    }
    Collections.shuffle(list, new Random(BIG_ARRAY_LENGTH * 10));
    for (int i = 0; i < list.size(); i++) {
      BIG_ARRAY[i] = list.get(i);
    }
  }

  public static int[] getBigArray() {
    int[] result = new int[BIG_ARRAY_LENGTH];
    System.arraycopy(BIG_ARRAY, 0, result, 0, BIG_ARRAY_LENGTH);
    return result;
  }
}
