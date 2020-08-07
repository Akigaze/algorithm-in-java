package demo.akiagaze.algorithm.bioinformation.lcs;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class LongestCommonSubsequenceAlgorithmTest {
  @Test
  public void should_calculate_lsc_1() {
    String s1 = "AGCATTGCGA";
    String s2 = "ACCACTCCAG";

    LongestCommonSubsequenceAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm();
    String lsc = algorithm.lcs(s1, s2);

    Assert.assertThat(lsc, CoreMatchers.is("ACATCG"));
  }

  @Test
  public void should_calculate_lsc_2() {
    String s1 = "AGACGAGACCCAGTAGCCCTAGGGAGCGTGTGCAAAGTGTAGATCTAGCTAGCTAAAAA";
    String s2 = "ACCACTCCAGCGATGGGGCTTTATCTAGCTTAGGAGGATGTCA";

    LongestCommonSubsequenceAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm();
    String lsc = algorithm.lcs(s1, s2);

    System.out.println(lsc);
  }
}
