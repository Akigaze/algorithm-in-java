package demo.akiagaze.algorithm.sort;

public class SortUtil {
  public static <T> void swap(T[] array, int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void swap(int[] array, int i, int j) {
    if (i != j) {
      int temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }
}
