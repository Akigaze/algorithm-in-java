package demo.akiagaze.algorithm.bioinformation.lcs;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class LongestCommonSubsequenceAlgorithmTest {
  @Test
  public void should_calculate_lsc_1() {
    String s1 = "AGCATTGCGA";
    String s2 = "ACCACTCCAG";

    LCSAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm(s1, s2);
    String lcs = algorithm.lcs();

    Assert.assertThat(lcs, CoreMatchers.anyOf(CoreMatchers.is("ACATCG"), CoreMatchers.is("ACATCA")));
  }

  @Test
  public void should_calculate_lsc_2() {
    String s1 = "AGACGAGACCCAGTAGCCCTAGGGAGCGTGTGCAAAGTGTAGATCTAGCTAGCTAAAAA";
    String s2 = "ACCACTCCAGCGATGGGGCTTTATCTAGCTTAGGAGGATGTCA";

    LCSAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm(s1, s2);
    String lcs = algorithm.lcs();

    System.out.println(lcs);
  }

  @Test
  public void should_calculate_lsc_3() {
    String s1 = "ATATGCTAGCTAGCCGTTAAAGTCGATCGATCGCCCGGAAATAGCTGAGCTTTTAGAGAGAGAGAGACGCGATGTGTGATCGCTGCTAGCTAGCTAGGGGA";
    String s2 = s1.substring(10);

    LCSAlgorithm algorithm = new LongestCommonSubsequenceAlgorithm(s1, s2);
    String lcs = algorithm.lcs();

    Assert.assertThat(lcs, CoreMatchers.is(s2));
  }
}
