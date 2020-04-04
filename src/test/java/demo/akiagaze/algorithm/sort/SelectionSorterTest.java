package demo.akiagaze.algorithm.sort;

import demo.akiagaze.algorithm.constant.Sort.Direction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SelectionSorterTest {

  @Test
  public void test_sort_an_int_array_asc_as_default_1() {
    SelectionSorter sorter = new SelectionSorter();
    sorter.setLoggable(true);
    int[] array = new int[]{21, 4, 34, 8, 11, 9, 5, 14, 25, 11, 20, 36, 3, 1, 19, 8, 24};
    int[] expected = {1, 3, 4, 5, 8, 8, 9, 11, 11, 14, 19, 20, 21, 24, 25, 34, 36};

    sorter.selectionSort(array);

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_an_int_array_asc_as_default_2() {
    SelectionSorter sorter = new SelectionSorter();
    sorter.setLoggable(true);
    int[] array = new int[]{1, 3, 4, 5, 8, 8, 9, 11, 11, 14, 19, 20, 21, 28, 25, 34, 36};
    int[] expected = {1, 3, 4, 5, 8, 8, 9, 11, 11, 14, 19, 20, 21, 25, 28, 34, 36};

    sorter.selectionSort(array);

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_an_int_array_asc_as_default_3() {
    SelectionSorter sorter = new SelectionSorter();
    sorter.setLoggable(true);
    int[] array = new int[]{36, 34, 25, 28, 21, 20, 19, 14, 11, 11, 9, 8, 8, 5, 4, 3, 1};
    sorter.selectionSort(array);

    int[] expected = {1, 3, 4, 5, 8, 8, 9, 11, 11, 14, 19, 20, 21, 25, 28, 34, 36};

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_a_small_int_array_asc_as_default() {
    SelectionSorter sorter = new SelectionSorter();
    sorter.setLoggable(true);
    int[] array = new int[]{2, 7, 4, 1, 5};
    sorter.selectionSort(array);

    int[] expected = {1, 2, 4, 5, 7};

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_a_big_int_array_asc_as_default() {
    SelectionSorter sorter = new SelectionSorter();
    int[] array = DataFactory.getBigArray();
    long start = System.currentTimeMillis();
    sorter.selectionSort(array);
    System.out.println(String.format("cost: [%d] ms", System.currentTimeMillis() - start));

    System.out.println(Arrays.toString(array));
    assertArrayEquals(DataFactory.EXPECTED_BIG_ARRAY, array);
  }

  @Test
  public void test_sort_an_int_array_asc_as_default_to_new_array() {
    SelectionSorter sorter = new SelectionSorter();
    int[] array = new int[]{11, 4, 3, 18, 1, 9, 25, 14, 7, 11, 22, 36, 3, 21, 15, 8, 34};
    int[] expected = {1, 3, 3, 4, 7, 8, 9, 11, 11, 14, 15, 18, 21, 22, 25, 34, 36};

    int[] result = sorter.selectionSortToNewArray(array);

    System.out.println(Arrays.toString(result));
    assertArrayEquals(expected, result);
    assertArrayEquals(new int[]{11, 4, 3, 18, 1, 9, 25, 14, 7, 11, 22, 36, 3, 21, 15, 8, 34}, array);
  }

  @Test
  public void test_sort_an_int_array_with_direction_desc() {
    SelectionSorter sorter = new SelectionSorter();

    int[] array = new int[]{11, 4, 2, 9, 25, 7, 11, 36, 3, 21, 8, 34};
    int[] expected = new int[]{36, 34, 25, 21, 11, 11, 9, 8, 7, 4, 3, 2};

    sorter.selectionSort(array, Direction.DESC);

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_an_int_array_with_direction_asc() {
    SelectionSorter sorter = new SelectionSorter();

    int[] array = new int[]{11, 4, 2, 9, 25, 7, 11, 36, 3, 21, 8, 34};
    int[] expected = new int[]{2, 3, 4, 7, 8, 9, 11, 11, 21, 25, 34, 36};

    sorter.selectionSort(array, Direction.ASC);

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_for_any_element_type_which_implement_Comparable_1() {
    SelectionSorter sorter = new SelectionSorter();
    String[] array = new String[]{"ad", "2", "a", "!", "9", "x", "A", ","};
    String[] expected = new String[]{"!", ",", "2", "9", "A", "a", "ad", "x"};

    sorter.selectionSort(array);

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_for_any_element_type_which_implement_Comparable_2() {
    SelectionSorter sorter = new SelectionSorter();
    String[] array = new String[]{"ad", "2", "a", "!", "9", "x", "A", ","};
    String[] expected = new String[]{"x", "ad", "a", "A", "9", "2", ",", "!"};

    sorter.selectionSort(array, Direction.DESC);

    System.out.println(Arrays.toString(array));
    assertArrayEquals(expected, array);
  }

  @Test
  public void test_sort_for_collection_1() {
    SelectionSorter sorter = new SelectionSorter();
    List<String> list = new ArrayList<>();
    Collections.addAll(list, "ad", "2", "a", "!", "9", "x", "A", ",");
    List<String> expected = Arrays.asList("x", "ad", "a", "A", "9", "2", ",", "!");

    sorter.selectionSort(list, Direction.DESC);

    System.out.println(list);
    assertEquals(expected, list);
  }

  @Test
  public void test_sort_for_collection_2() {
    SelectionSorter sorter = new SelectionSorter();
    List<String> list = new ArrayList<>();
    Collections.addAll(list, "ad", "2", "a", "!", "9", "x", "A", ",");
    List<String> expected = Arrays.asList("!", ",", "2", "9", "A", "a", "ad", "x");

    sorter.selectionSort(list);

    System.out.println(list);
    assertEquals(expected, list);
  }
}