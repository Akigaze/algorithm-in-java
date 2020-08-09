package demo.akiagaze.algorithm.bioinformation.lcs;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class LongestCommonSubsequenceAlgorithmTest {
  @Test
  public void should_calculate_lsc_1() {
    String s1 = "AGCATTGCGA";
    String s2 = "ACCACTCCAG";

    LongestCommonSubsequenceAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm(s1, s2);
    String lsc = algorithm.lcs();

    Assert.assertThat(lsc, CoreMatchers.anyOf(CoreMatchers.is("ACATCG"), CoreMatchers.is("ACATCA")));
  }

  @Test
  public void should_calculate_lsc_2() {
    String s1 = "AGACGAGACCCAGTAGCCCTAGGGAGCGTGTGCAAAGTGTAGATCTAGCTAGCTAAAAA";
    String s2 = "ACCACTCCAGCGATGGGGCTTTATCTAGCTTAGGAGGATGTCA";

    LongestCommonSubsequenceAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm(s1, s2);
    String lsc = algorithm.lcs();

    System.out.println(lsc);
  }
}
