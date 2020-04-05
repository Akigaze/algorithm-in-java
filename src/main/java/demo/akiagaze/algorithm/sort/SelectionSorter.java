package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

// 时间复杂度: O(n^2)/O(n^2)
public class SelectionSorter extends Loggable implements Sorter {

  public void sort(int[] array) {
    this.sort(array, Direction.ASC);
  }

  public void sort(int[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;
    // 稳定比较次数，1+2+...+(length-1), 即两两比较的次数: n(n-1)/2
    for (int i = 0; i < array.length - 1; i++) {
      int indexOfExtremeValue = i;
      for (int j = i + 1; j < array.length; j++) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], array[indexOfExtremeValue]);
        /*
          从小到大(正序) a[i] < a[indexOfMin]  ==> a[indexOfMin] - a[i] > 0  ==>  (a[indexOfMin] - a[i]) > 0
          从大到小(倒序) a[i] > a[indexOfMax]  ==> a[i] - a[indexOfMax] > 0  ==> -(a[indexOfMax] - a[i]) > 0
         */
        if ((array[indexOfExtremeValue] - array[j]) * direction.positive > 0) {
          indexOfExtremeValue = j;
        }
      }
      if (indexOfExtremeValue != i) {
        this.log("swap No.%d", ++swapTimes);
        int min = array[indexOfExtremeValue];
        array[indexOfExtremeValue] = array[i];
        array[i] = min;
      }
    }

    System.out.println(String.format("total compare times: %d, total swap times: %d", compareTimes, swapTimes));
  }

  public <T extends Comparable<T>> void sort(T[] array) {
    this.sort(array, Direction.ASC);
  }

  public <T extends Comparable<T>> void sort(T[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;
    // 稳定比较次数，1+2+...+(length-1), 即两两比较的次数
    for (int i = 0; i < array.length - 1; i++) {
      int indexOfExtremeValue = i;
      for (int j = i + 1; j < array.length; j++) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], array[indexOfExtremeValue]);
        /*
          从小到大(正序) a[i] < a[indexOfMin]  ==> a[indexOfMin] - a[i] > 0  ==>  (a[indexOfMin] - a[i]) > 0
          从大到小(倒序) a[i] > a[indexOfMax]  ==> a[i] - a[indexOfMax] > 0  ==> -(a[indexOfMax] - a[i]) > 0
          此处 a.compareTo(b) 解释为 a - b
         */
        if (array[indexOfExtremeValue].compareTo(array[j]) * direction.positive > 0) {
          indexOfExtremeValue = j;
        }
      }
      if (indexOfExtremeValue != i) {
        this.log("swap No.%d", ++swapTimes);
        T min = array[indexOfExtremeValue];
        array[indexOfExtremeValue] = array[i];
        array[i] = min;
      }
    }
  }

  public <T extends Comparable<T>> void sort(List<T> list, Direction direction) {
    if (list.isEmpty()) {
      return;
    }
    T[] array = list.toArray((T[]) Array.newInstance(list.get(0).getClass(), list.size()));
    this.sort(array, direction);

    list.clear();
    Collections.addAll(list, array);
  }

  public <T extends Comparable<T>> void sort(List<T> list) {
    this.sort(list, Direction.ASC);
  }

}
