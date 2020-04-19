package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

import static demo.akiagaze.algorithm.sort.SortUtil.swap;

// 时间复杂度: O(n^2)/O(nlog2n)
public class QuickSorter extends Loggable implements Sorter {

  @Override
  public void sort(int[] array, Direction direction) {
    this.log("[Sort] length of array: %d", array.length);
//    new PivotAtFirstSorter(direction).doSort(array, 0, array.length - 1);
    new PivotAtMiddleSorter(direction).doSort(array, 0, array.length - 1);
  }

  @Override
  public <T extends Comparable<T>> void sort(T[] array, Direction direction) {
    this.log("[Sort] length of array: %d", array.length);
//    new PivotAtFirstSorter(direction).doSort(array, 0, array.length - 1);
    new PivotAtMiddleSorter(direction).doSort(array, 0, array.length - 1);
  }

  private abstract class PivotSorter {
    protected final Direction direction;

    public PivotSorter(Direction direction) {
      this.direction = direction;
    }

    protected abstract void doSort(int[] array, int left, int right);

    protected abstract int partition(int[] array, int left, int right);

    protected abstract <T extends Comparable<T>> void doSort(T[] array, int left, int right);

    protected abstract <T extends Comparable<T>> int partition(T[] array, int left, int right);
  }

  private class PivotAtFirstSorter extends PivotSorter {

    public PivotAtFirstSorter(Direction direction) {
      super(direction);
    }

    @Override
    protected void doSort(int[] array, int left, int right) {
      if (left < right) {
        int pivot = this.partition(array, left, right);
        this.doSort(array, left, pivot - 1);
        this.doSort(array, pivot + 1, right);
      }
    }

    @Override
    protected int partition(int[] array, int left, int right) {
      int pivot = array[left];
      int nextIndexOfMin = left + 1;

      for (int i = nextIndexOfMin; i <= right; i++) {
        // 从小到大：array[i] < pivot  =>  array[i] - pivot < 0
        // 从大到小：array[i] > pivot  =>  -(array[i] - pivot) < 0
        if ((array[i] - pivot) * direction.positive < 0) {
          swap(array, i, nextIndexOfMin);
          nextIndexOfMin++;
        }
      }
      swap(array, left, nextIndexOfMin - 1);

      return nextIndexOfMin - 1;
    }

    @Override
    protected <T extends Comparable<T>> void doSort(T[] array, int left, int right) {
      if (left < right) {
        int pivot = this.partition(array, left, right);
        this.doSort(array, left, pivot - 1);
        this.doSort(array, pivot + 1, right);
      }
    }

    @Override
    protected <T extends Comparable<T>> int partition(T[] array, int left, int right) {
      T pivot = array[left];
      int nextIndexOfMin = left + 1;

      for (int i = nextIndexOfMin; i <= right; i++) {
        // 从小到大：array[i] < pivot  =>  array[i] - pivot < 0
        // 从大到小：array[i] > pivot  =>  -(array[i] - pivot) < 0
        // a.compareTo(b)  =>  a - b
        if ((array[i].compareTo(pivot)) * direction.positive < 0) {
          swap(array, i, nextIndexOfMin);
          nextIndexOfMin++;
        }
      }
      swap(array, left, nextIndexOfMin - 1);

      return nextIndexOfMin - 1;
    }
  }

  private class PivotAtMiddleSorter extends PivotSorter {

    public PivotAtMiddleSorter(Direction direction) {
      super(direction);
    }

    @Override
    protected void doSort(int[] array, int left, int right) {
      if (left < right) {
        int pivot = this.partition(array, left, right);
        this.doSort(array, left, pivot - 1);
        this.doSort(array, pivot, right);
      }
    }

    @Override
    protected int partition(int[] array, int left, int right) {
      int mid = (left + right) >> 1;
      int pivot = array[mid];

      while (left <= right) {
        // 从小到大：array[left] < pivot  =>  array[left] - pivot < 0
        // 从大到小：array[left] > pivot  =>  -(array[left] - pivot) < 0
        while ((array[left] - pivot) * direction.positive < 0) {
          left++;
        }
        // 从小到大：array[right] > pivot  =>  array[right] - pivot > 0
        // 从大到小：array[right] < pivot  =>  -(array[right] - pivot) > 0
        while ((array[right] - pivot) * direction.positive > 0) {
          right--;
        }
        if (left <= right) {
          swap(array, left, right);
          left++;
          right--;
        }
      }
      return left;
    }

    @Override
    protected <T extends Comparable<T>> void doSort(T[] array, int left, int right) {
      if (left < right) {
        int pivot = this.partition(array, left, right);
        this.doSort(array, left, pivot - 1);
        this.doSort(array, pivot, right);
      }
    }

    @Override
    protected <T extends Comparable<T>> int partition(T[] array, int left, int right) {
      int mid = (left + right) >> 1;
      T pivot = array[mid];

      while (left <= right) {
        while ((array[left].compareTo(pivot)) * direction.positive < 0) {
          left++;
        }
        while ((array[right].compareTo(pivot)) * direction.positive > 0) {
          right--;
        }
        if (left <= right) {
          swap(array, left, right);
          left++;
          right--;
        }
      }
      return left;
    }
  }
}
