package demo.akiagaze.algorithm.sort;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public class BubbleSorter {
  public enum Direction {
    ASC(1), DESC(-1);

    private int positive;

    Direction(int positive) {
      this.positive = positive;
    }
  }

  private boolean loggable = false;

  public void bubbleSort(int[] array) {
    this.bubbleSort(array, Direction.ASC);
  }

  public int[] bubbleSortToNewArray(int[] array) {
    int[] result = new int[array.length];
    System.arraycopy(array, 0, result, 0, array.length);
    bubbleSort(result);
    return result;
  }

  public void bubbleSort(int[] array, Direction direction) {
    int compareTimes = 0;
    this.log("array length: %d", array.length);
    // 最大比较次数 (length - 1)^2
    for (int i = 0; i < array.length - 1; i++) {
      boolean swapped = false;
      // "- i" 可以实现比较次数减少 1+2+...+(length - 2) 次
      for (int j = 0; j < array.length - 1 - i; j++) {
        this.log("compare: %d, %d, %d", ++compareTimes, array[j], array[j + 1]);
        /*
          从小到大(正序) a[i] > a[i+1]  => a[i] - a[i+1] > 0  ==>  (a[i] - a[i+1]) > 0
          从大到小(倒序) a[i] < a[i+1]  => a[i] - a[i+1] < 0  ==> -(a[i] - a[i+1]) > 0
         */
        if ((array[j] - array[j + 1]) * direction.positive > 0) {
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
  }

  public <T extends Comparable<T>> void bubbleSort(T[] array, Direction direction) {
    int compareTimes = 0;
    this.log("array length: %d", array.length);
    // 最大比较次数 (length - 1)^2
    for (int i = 0; i < array.length - 1; i++) {
      boolean swapped = false;
      // "- i" 可以实现比较次数减少 1+2+...+(length - 2) 次
      for (int j = 0; j < array.length - 1 - i; j++) {
        this.log("compare: %d, %d, %d", ++compareTimes, array[j], array[j + 1]);
        /*
          从小到大(正序) a[i] > a[i+1]  => a[i] - a[i+1] > 0  ==>  (a[i] - a[i+1]) > 0
          从大到小(倒序) a[i] < a[i+1]  => a[i] - a[i+1] < 0  ==> -(a[i] - a[i+1]) > 0
          a.compareTo(b) 此处解释为  a - b
         */
        if (array[j].compareTo(array[j + 1]) * direction.positive > 0) {
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

  public <T extends Comparable<T>> void bubbleSort(T[] array) {
    this.bubbleSort(array, Direction.ASC);
  }

  public <T extends Comparable<T>> void bubbleSort(List<T> list, Direction direction) {
    if (list.isEmpty()) {
      return;
    }
    T[] array = list.toArray((T[]) Array.newInstance(list.get(0).getClass(), list.size()));
    bubbleSort(array, direction);

    list.clear();
    Collections.addAll(list, array);
  }

  public <T extends Comparable<T>> void bubbleSort(List<T> collection) {
    this.bubbleSort(collection, Direction.ASC);
  }

  public void setLoggable(boolean loggable) {
    this.loggable = loggable;
  }

  private void log(String template, Object... args) {
    if (loggable) {
      System.out.println(String.format(template, args));
    }
  }
}
