package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

import static demo.akiagaze.algorithm.sort.SortUtil.swap;

// 时间复杂度: O(n^2)/O(nlog2n)
public class QuickSorter extends Loggable implements Sorter {

  @Override
  public void sort(int[] array, Direction direction) {
    this.log("[Sort] length of array: %d", array.length);
    new QSorter(direction).doSort(array, 0, array.length - 1);
  }

  @Override
  public <T extends Comparable<T>> void sort(T[] array, Direction direction) {
    this.log("[Sort] length of array: %d", array.length);
    new QSorter(direction).doSort(array, 0, array.length - 1);
  }

  private static class QSorter{
    private final Direction direction;

    public QSorter(Direction direction) {
      this.direction = direction;
    }

    private void doSort(int[] array, int left, int right) {
      if (left < right) {
        int pivot = this.partition(array, left, right);
        this.doSort(array, left, pivot - 1);
        this.doSort(array, pivot + 1, right);
      }
    }

    private int partition(int[] array, int left, int right) {
      int pivotIndex = left;
      int nextIndexOfMin = pivotIndex + 1;

      for (int i = nextIndexOfMin; i <= right; i++) {
        // 从小到大：array[i] < pivot  =>  array[i] - pivot < 0
        // 从大到小：array[i] > pivot  =>  -(array[i] - pivot) < 0
        if ((array[i] - array[pivotIndex]) * direction.positive < 0) {
          swap(array, i, nextIndexOfMin);
          nextIndexOfMin++;
        }
      }
      swap(array, pivotIndex, nextIndexOfMin - 1);

      return nextIndexOfMin - 1;
    }

    private <T extends Comparable<T>> void doSort(T[] array, int left, int right) {
      if (left < right) {
        int pivot = this.partition(array, left, right);
        this.doSort(array, left, pivot - 1);
        this.doSort(array, pivot + 1, right);
      }
    }

    private <T extends Comparable<T>> int partition(T[] array, int left, int right) {
      int pivotIndex = left;
      int nextIndexOfMin = pivotIndex + 1;

      for (int i = nextIndexOfMin; i <= right; i++) {
        // 从小到大：array[i] < pivot  =>  array[i] - pivot < 0
        // 从大到小：array[i] > pivot  =>  -(array[i] - pivot) < 0
        // a.compareTo(b)  =>  a - b
        if ((array[i].compareTo(array[pivotIndex])) * direction.positive < 0) {
          swap(array, i, nextIndexOfMin);
          nextIndexOfMin++;
        }
      }
      swap(array, pivotIndex, nextIndexOfMin - 1);

      return nextIndexOfMin - 1;
    }
  }
}
