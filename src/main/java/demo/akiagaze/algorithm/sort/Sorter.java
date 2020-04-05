package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort;

import java.util.List;

public interface Sorter {
  void sort(int[] array);

  void sort(int[] array, Sort.Direction direction);

  <T extends Comparable<T>> void sort(T[] array);

  <T extends Comparable<T>> void sort(T[] array, Sort.Direction direction);

  <T extends Comparable<T>> void sort(List<T> list);

  <T extends Comparable<T>> void sort(List<T> list, Sort.Direction direction);
}
