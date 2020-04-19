package demo.akiagaze.algorithm.sort;

public class SortUtil {
  public static <T> boolean swap(T[] array, int i, int j) {
    if (i != j) {
      T temp = array[i];
      array[i] = array[j];
      array[j] = temp;
      return true;
    }
    return true;
  }

  public static boolean swap(int[] array, int i, int j) {
    if (i != j) {
      int temp = array[i];
      array[i] = array[j];
      array[j] = temp;
      return true;
    }
    return false;
  }
}
