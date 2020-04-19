package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public interface Sorter {
  default void sort(int[] array) {
    this.sort(array, Sort.Direction.ASC);
  }

  void sort(int[] array, Sort.Direction direction);

  default <T extends Comparable<T>> void sort(T[] array) {
    this.sort(array, Sort.Direction.ASC);
  }

  <T extends Comparable<T>> void sort(T[] array, Sort.Direction direction);

  default <T extends Comparable<T>> void sort(List<T> list) {
    this.sort(list, Sort.Direction.ASC);
  }

  default <T extends Comparable<T>> void sort(List<T> list, Sort.Direction direction) {
    if (list.isEmpty()) {
      return;
    }
    T[] array = list.toArray((T[]) Array.newInstance(list.get(0).getClass(), list.size()));
    this.sort(array, direction);

    list.clear();
    Collections.addAll(list, array);
  }

}
