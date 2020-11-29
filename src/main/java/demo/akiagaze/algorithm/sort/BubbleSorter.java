package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

// 时间复杂度: O(n^2)/O(n)
public class BubbleSorter extends Loggable implements Sorter {
  @Override
  public void sort(int[] array) {
    this.sort(array, Direction.ASC);
  }

  @Override
  public void sort(int[] array, Direction direction) {
    long compareTimes = 0;
    long swapTimes = 0;
    this.log("length of array: %d", array.length);
    // 最大比较次数 (length - 1)^2
    for (int i = 0; i < array.length - 1; i++) {
      boolean swapped = false;
      // "- i" 可以实现比较次数减少 1+2+...+(length - 2) 次, 即总比较次数为: n(n-1)/2
      for (int j = 0; j < array.length - 1 - i; j++) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], array[j + 1]);
        /*
          从小到大(正序) a[i] > a[i+1]  ==> a[i] - a[i+1] > 0  ==>  (a[i] - a[i+1]) > 0
          从大到小(倒序) a[i] < a[i+1]  ==> a[i] - a[i+1] < 0  ==> -(a[i] - a[i+1]) > 0
         */
        if ((array[j] - array[j + 1]) * direction.positive > 0) {
          this.log("swap No.%d", ++swapTimes);
          int smaller = array[j + 1];
          array[j + 1] = array[j];
          array[j] = smaller;
          swapped = true;
        }
      }
      /*
        当本轮比较没有发生位置交换时，则表示排序已经完成
        可以减少后续不必要的比较，对于相对比较有序的数组可以很大提升排序效率
       */
      if (!swapped) {
        break;
      }
    }

    System.out.println(String.format("total compare times: %d, total swap times: %d", compareTimes, swapTimes));
  }

  @Override
  public <T extends Comparable<T>> void sort(T[] array, Direction direction) {
    long compareTimes = 0;
    long swapTimes = 0;
    this.log("length of array: %d", array.length);
    // 最大比较次数 (length - 1)^2
    for (int i = 0; i < array.length - 1; i++) {
      boolean swapped = false;
      // "- i" 可以实现比较次数减少 1+2+...+(length - 2) 次 : (n-1)(n-2)/2
      for (int j = 0; j < array.length - 1 - i; j++) {
        this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], array[j + 1]);
        /*
          从小到大(正序) a[i] > a[i+1]  ==> a[i] - a[i+1] > 0  ==>  (a[i] - a[i+1]) > 0
          从大到小(倒序) a[i] < a[i+1]  ==> a[i] - a[i+1] < 0  ==> -(a[i] - a[i+1]) > 0
          a.compareTo(b) 此处解释为  a - b
         */
        if (array[j].compareTo(array[j + 1]) * direction.positive > 0) {
          this.log("swap No.%d", ++swapTimes);
          T smaller = array[j + 1];
          array[j + 1] = array[j];
          array[j] = smaller;
          swapped = true;
        }
      }
      /*
        当本轮比较没有发生位置交换时，则表示排序已经完成
        可以减少后续不必要的比较，对于相对比较有序的数组可以很大提升排序效率
       */
      if (!swapped) {
        break;
      }
    }
  }

  @Override
  public <T extends Comparable<T>> void sort(T[] array) {
    this.sort(array, Direction.ASC);
  }
}
