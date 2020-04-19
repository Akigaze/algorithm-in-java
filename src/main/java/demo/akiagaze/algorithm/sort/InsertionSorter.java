package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

// 时间复杂度: O(n^2)/O(n)
public class InsertionSorter extends Loggable implements Sorter {
  @Override
  public void sort(int[] array) {
//    this.sort(array, Direction.ASC);
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;
    // 从第二个元素开始，依次与它之前的元素比较，找到第一个跟它一样大，或者比它小的元素，在它后面把这个元素插入
    // 所以对于每个元素，都认为它前面的元素都已经是排序完毕的了
    // 完全逆序时，元素比较的最大次数为 1+2+...+(length-1), 即: n(n-1)/2
    // 完全正序时，元素比较的最小次数为 length-1
    for (int i = 1; i < array.length; i++) {
      // 默认当前元素是最小的，因为通过之后的比较，会找出元素的正确位置
      int insertPosition = 0;
      int current = array[i];
      for (int j = i - 1; j >= 0; j--) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], current);
        // 当前元素与它之前的元素比较，找到合适的位置插入，因此前面的元素需要后移给当前元素腾出一个位置
        if (current >= array[j]) {
          // j 位置的元素不大于当前元素，因此记录 j 的下一个位置以便插入当前元素
          insertPosition = j + 1;
          break;
        } else {
          array[j + 1] = array[j];
          swapTimes++;
        }
      }
      if (insertPosition != i) {
        array[insertPosition] = current;
        this.log("swap No.%d", swapTimes);
      }
    }

    System.out.println(String.format("total compare times: %d, total swap times: %d", compareTimes, swapTimes));
  }
  @Override
  public void sort(int[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;
    // 从第二个元素开始，依次与它之前的元素比较，找到第一个跟它一样大，或者比它小的元素，在它后面把这个元素插入
    // 所以对于每个元素，都认为它前面的元素都已经是排序完毕的了
    for (int i = 1; i < array.length; i++) {
      // 默认当前元素是最小的，因为通过之后的比较，会找出元素的正确位置
      int insertPosition = 0;
      int current = array[i];
      for (int j = i - 1; j >= 0; j--) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], current);
        /*
          当前元素与它之前的元素比较，找到合适的位置插入，因此前面的元素需要后移给当前元素腾出一个位置
          从小到大(正序) a[i] <= a[indexOfCur]  ==> a[indexOfCur] - a[i] >= 0  ==>  (a[indexOfCur] - a[i]) >= 0
          从大到小(倒序) a[i] >= a[indexOfCur]  ==> a[i] - a[indexOfCur] >= 0  ==> -(a[indexOfCur] - a[i]) >= 0
         */
        if ((current - array[j]) * direction.positive >= 0) {
          // j 位置的元素不大于当前元素，因此记录 j 的下一个位置以便插入当前元素
          insertPosition = j + 1;
          break;
        } else {
          array[j + 1] = array[j];
          swapTimes++;
        }
      }
      if (insertPosition != i) {
        array[insertPosition] = current;
        this.log("swap No.%d", swapTimes);
      }
    }
  }
  @Override
  public <T extends Comparable<T>> void sort(T[] array) {
    this.sort(array, Direction.ASC);
  }
  @Override
  public <T extends Comparable<T>> void sort(T[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;
    // 从第二个元素开始，依次与它之前的元素比较，找到第一个跟它一样大，或者比它小的元素，在它后面把这个元素插入
    // 所以对于每个元素，都认为它前面的元素都已经是排序完毕的了
    for (int i = 1; i < array.length; i++) {
      // 默认当前元素是最小的，因为通过之后的比较，会找出元素的正确位置
      int insertPosition = 0;
      T current = array[i];
      for (int j = i - 1; j >= 0; j--) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], current);
        /*
          当前元素与它之前的元素比较，找到合适的位置插入，因此前面的元素需要后移给当前元素腾出一个位置
          从小到大(正序) a[i] <= a[indexOfCur]  ==> a[indexOfCur] - a[i] >= 0  ==>  (a[indexOfCur] - a[i]) >= 0
          从大到小(倒序) a[i] >= a[indexOfCur]  ==> a[i] - a[indexOfCur] >= 0  ==> -(a[indexOfCur] - a[i]) >= 0
          a.compareTo(b) 此处解释为  a - b
         */
        if ((current.compareTo(array[j])) * direction.positive >= 0) {
          // j 位置的元素不大于当前元素，因此记录 j 的下一个位置以便插入当前元素
          insertPosition = j + 1;
          break;
        } else {
          array[j + 1] = array[j];
          swapTimes++;
        }
      }
      if (insertPosition != i) {
        array[insertPosition] = current;
        this.log("swap No.%d", swapTimes);
      }
    }
  }
}
