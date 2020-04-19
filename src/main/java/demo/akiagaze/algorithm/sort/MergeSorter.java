package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 时间复杂度: O(nlog2n)
public class MergeSorter extends Loggable implements Sorter {
  @Override
  public void sort(int[] array) {
    this.sort(array, Direction.ASC);
//    this.mergeSort(array, 0 , array.length - 1);
  }

  @Override
  public void sort(int[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    int[] result = this.mergeSort(array, direction);
    System.arraycopy(result, 0, array, 0, array.length);
  }

  @Override
  public <T extends Comparable<T>> void sort(T[] array) {
    this.sort(array, Direction.ASC);
  }

  @Override
  public <T extends Comparable<T>> void sort(T[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    T[] result = this.mergeSort(array, direction);
    System.arraycopy(result, 0, array, 0, array.length);
  }

  // 有返回值算法
  private int[] mergeSort(int[] array, Direction direction) {
    int length = array.length;
    if (length < 2) {
      return array;
    }
    int middle = length >> 1;
    int[] left = Arrays.copyOfRange(array, 0, middle);
    int[] right = Arrays.copyOfRange(array, middle, length);
    // 通过递归将数组拆分连续的小的分组，对每个组进行排序，相邻的组再做排序
    return this.merge(mergeSort(left, direction), mergeSort(right, direction), direction);
  }
  // 有返回值算法
  private int[] merge(int[] left, int[] right, Direction direction) {
    int[] result = new int[left.length + right.length];
    int lIndex = 0;
    int rIndex = 0;
    int i = 0;
    // 归并排序，将两个数组合并成一个有序的数组
    // 两个数组分别从第一个元素开始，各拿出一个元素进行比较，小的或者大的一方将元素放入结果集，放入结果集的元素不在进行比较
    while (lIndex < left.length && rIndex < right.length) {
      /*
      从小到大：left[i] < right[j] ==> left[i] - right[j] < 0 ==> 1 * (left[i] - right[j]) < 0
      从大到小：left[i] > right[j] ==> left[i] - right[j] > 0 ==> -1 * (left[i] - right[j]) < 0
       */
      if ((left[lIndex] - right[rIndex]) * direction.positive <= 0) {
        result[i++] = left[lIndex++];
      } else {
        result[i++] = right[rIndex++];
      }
    }
    // 将余下的所有元素，依次放入结果集
    while (lIndex < left.length) {
      result[i++] = left[lIndex++];
    }
    while (rIndex < right.length) {
      result[i++] = right[rIndex++];
    }
    return result;
  }

  private <T extends Comparable<T>> T[] mergeSort(T[] array, Direction direction) {
    int length = array.length;
    if (length < 2) {
      return array;
    }
    int middle = length >> 1;
    T[] left = Arrays.copyOfRange(array, 0, middle);
    T[] right = Arrays.copyOfRange(array, middle, length);
    // 通过递归将数组拆分连续的小的分组，对每个组进行排序，相邻的组再做排序
    return this.merge(mergeSort(left, direction), mergeSort(right, direction), direction);
  }

  private <T extends Comparable<T>> T[] merge(T[] left, T[] right, Direction direction) {
    T[] result = (T[]) Array.newInstance(left[0].getClass(), left.length + right.length);
    int lIndex = 0;
    int rIndex = 0;
    int i = 0;
    // 归并排序，将两个数组合并成一个有序的数组
    // 两个数组分别从第一个元素开始，各拿出一个元素进行比较，小的或者大的一方将元素放入结果集，放入结果集的元素不在进行比较
    while (lIndex < left.length && rIndex < right.length) {
      /*
      从小到大：left[i] < right[j] ==> left[i] - right[j] < 0 ==> 1 * (left[i] - right[j]) < 0
      从大到小：left[i] > right[j] ==> left[i] - right[j] > 0 ==> -1 * (left[i] - right[j]) < 0
      left[i].compareTo(right[j]) 相当于 left[i] - right[j]
       */
      if ((left[lIndex].compareTo(right[rIndex])) * direction.positive <= 0) {
        result[i++] = left[lIndex++];
      } else {
        result[i++] = right[rIndex++];
      }
    }
    // 将余下的所有元素，依次放入结果集
    while (lIndex < left.length) {
      result[i++] = left[lIndex++];
    }
    while (rIndex < right.length) {
      result[i++] = right[rIndex++];
    }
    return result;
  }

  // 无返回值算法
  private void mergeSort(int[] array, int lIndex, int rIndex) {
    if (rIndex <= lIndex) {
      return;
    }
    int middle = (lIndex + rIndex) >> 1;
    // 通过子数组在原数组中的位置，对数组进行拆分
    this.mergeSort(array, lIndex, middle);
    this.mergeSort(array, middle + 1, rIndex);
    this.merge(array, lIndex, middle, rIndex);
  }
  // 无返回值算法
  private void merge(int[] array, int lIndex, int middle, int rIndex) {
    int[] result = new int[rIndex - lIndex + 1];
    int _lI = lIndex;
    int _rI = middle + 1;
    int i = 0;
    while (_lI <= middle && _rI <= rIndex) {
      if (array[_lI] <= array[_rI]) {
        result[i++] = array[_lI++];
      } else {
        result[i++] = array[_rI++];
      }
    }
    while (_lI <= middle) {
      result[i++] = array[_lI++];
    }
    while (_rI <= rIndex) {
      result[i++] = array[_rI++];
    }
    for (i = 0; i < result.length; i++) {
      array[lIndex + i] = result[i];
    }
  }
}
