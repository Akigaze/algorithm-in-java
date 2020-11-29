package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import demo.akiagaze.algorithm.util.log.Loggable;

// 时间复杂度: O(n^1.3)
public class ShellSorter extends Loggable implements Sorter {

  private final int incrementFactor;

  public ShellSorter() {
    this(2);
  }

  public ShellSorter(int incrementFactor) {
    this.incrementFactor = incrementFactor;
  }

  @Override
  public void sort(int[] array) {
//    this.sort(array, Direction.ASC);
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;

    /*
    最极端的计算：数组有 n 个元素
    每次分组所得的组的数量N为: n/(2^k)，每组的元素个数M为 2^k，组内比较的最大次数S为 (2^k)((2^k)-1))/2
    总的比较次数为 N1*S1 + N2*S2 + ... + Nk*Sk，k为 n/(2^k) <= 1 时最大值的k
     */

    // 对元素进行分组，每组进行组内排序，采用插入排序的方式进行排序
    // 从最小的两两一组开始，并将距离相对较远的元素分为一组，通过减少组内元素之间的距离的方式扩大分组
    // 最后是相邻元素进行插入排序，但此时的排序已经基本完成，所以不用进行过多的比较和位置交换

    // 设定元素之间的距离，将元素进行分组，最开始是两两一组
    for (int gap = Math.floorDiv(array.length, incrementFactor); gap > 0; gap = Math.floorDiv(gap, incrementFactor)) {

      // 类似插入排序的方式，对每个组的元素进行排序
      for (int i = gap; i < array.length; i++) {
        int current = array[i];
        // j 最初是前一个元素的位置
        int insertPosition = 0;
        // j 会随着循环落在组内元素的位置，当没有找到符合的元素时，j 最终会落在最前面的元素的位置
        for (int j = i - gap; j >= 0; j -= gap) {
          this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], current);
          if (current >= array[j]) {
            // 当找到第一个小于或等于当前元素的元素时，就要在它的后一个位置插入
            insertPosition = j + gap;
            break;
          } else {
            array[j + gap] = array[j];
            swapTimes++;
          }
          insertPosition = j;
        }
        if (insertPosition != i) {
          array[insertPosition] = current;
          this.log("swap No.%d", swapTimes);
        }

      }
    }

    System.out.println(String.format("total compare times: %d, total swap times: %d", compareTimes, swapTimes));
  }

  @Override
  public void sort(int[] array, Direction direction) {
    this.log("length of array: %d", array.length);
    long compareTimes = 0;
    long swapTimes = 0;

    /*
    最极端的计算：数组有 n 个元素
    每次分组所得的组的数量N为: n/(2^k)，每组的元素个数M为 2^k，组内比较的最大次数S为 (2^k)((2^k)-1))/2
    总的比较次数为 N1*S1 + N2*S2 + ... + Nk*Sk，k为 n/(2^k) <= 1 时最大值的k
     */

    // 对元素进行分组，每组进行组内排序，采用插入排序的方式进行排序
    // 从最小的两两一组开始，并将距离相对较远的元素分为一组，通过减少组内元素之间的距离的方式扩大分组
    // 最后是相邻元素进行插入排序，但此时的排序已经基本完成，所以不用进行过多的比较和位置交换

    // 设定元素之间的距离，将元素进行分组，最开始是两两一组
    for (int gap = Math.floorDiv(array.length, incrementFactor); gap > 0; gap = Math.floorDiv(gap, incrementFactor)) {

      // 类似插入排序的方式，对每个组的元素进行排序
      for (int i = gap; i < array.length; i++) {
        int current = array[i];
        // j 最初是前一个元素的位置
        int insertPosition = 0;
        // j 会随着循环落在组内元素的位置，当没有找到符合的元素时，j 最终会落在最前面的元素的位置
        for (int j = i - gap; j >= 0; j -= gap) {
          this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], current);
          /*
          当前元素与它之前的元素比较，找到合适的位置插入，因此前面的元素需要后移给当前元素腾出一个位置
          从小到大(正序) a[i] <= a[indexOfCur]  ==> a[indexOfCur] - a[i] >= 0  ==>  (a[indexOfCur] - a[i]) >= 0
          从大到小(倒序) a[i] >= a[indexOfCur]  ==> a[i] - a[indexOfCur] >= 0  ==> -(a[indexOfCur] - a[i]) >= 0
         */
          if ((current - array[j]) * direction.positive >= 0) {
            // 当找到第一个小于或等于当前元素的元素时，就要在它的后一个位置插入
            insertPosition = j + gap;
            break;
          } else {
            array[j + gap] = array[j];
            swapTimes++;
          }
          insertPosition = j;
        }
        if (insertPosition != i) {
          array[insertPosition] = current;
          this.log("swap No.%d", swapTimes);
        }

      }
    }

    System.out.println(String.format("total compare times: %d, total swap times: %d", compareTimes, swapTimes));
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

    /*
    最极端的计算：数组有 n 个元素
    每次分组所得的组的数量N为: n/(2^k)，每组的元素个数M为 2^k，组内比较的最大次数S为 (2^k)((2^k)-1))/2
    总的比较次数为 N1*S1 + N2*S2 + ... + Nk*Sk，k为 n/(2^k) <= 1 时最大值的k
     */

    // 对元素进行分组，每组进行组内排序，采用插入排序的方式进行排序
    // 从最小的两两一组开始，并将距离相对较远的元素分为一组，通过减少组内元素之间的距离的方式扩大分组
    // 最后是相邻元素进行插入排序，但此时的排序已经基本完成，所以不用进行过多的比较和位置交换

    // 设定元素之间的距离，将元素进行分组，最开始是两两一组
    for (int gap = Math.floorDiv(array.length, incrementFactor); gap > 0; gap = Math.floorDiv(gap, incrementFactor)) {

      // 类似插入排序的方式，对每个组的元素进行排序
      for (int i = gap; i < array.length; i++) {
        T current = array[i];
        // j 最初是前一个元素的位置
        int insertPosition = 0;
        // j 会随着循环落在组内元素的位置，当没有找到符合的元素时，j 最终会落在最前面的元素的位置
        for (int j = i - gap; j >= 0; j -= gap) {
          this.log("compare No.%d: %d vs. %d", ++compareTimes, array[j], current);
          /*
          当前元素与它之前的元素比较，找到合适的位置插入，因此前面的元素需要后移给当前元素腾出一个位置
          从小到大(正序) a[i] <= a[indexOfCur]  ==> a[indexOfCur] - a[i] >= 0  ==>  (a[indexOfCur] - a[i]) >= 0
          从大到小(倒序) a[i] >= a[indexOfCur]  ==> a[i] - a[indexOfCur] >= 0  ==> -(a[indexOfCur] - a[i]) >= 0
          此处 a.compareTo(b) 理解为 a - b
         */
          if ((current.compareTo(array[j])) * direction.positive >= 0) {
            // 当找到第一个小于或等于当前元素的元素时，就要在它的后一个位置插入
            insertPosition = j + gap;
            break;
          } else {
            array[j + gap] = array[j];
            swapTimes++;
          }
          insertPosition = j;
        }
        if (insertPosition != i) {
          array[insertPosition] = current;
          this.log("swap No.%d", swapTimes);
        }

      }
    }

    System.out.println(String.format("total compare times: %d, total swap times: %d", compareTimes, swapTimes));
  }
}
