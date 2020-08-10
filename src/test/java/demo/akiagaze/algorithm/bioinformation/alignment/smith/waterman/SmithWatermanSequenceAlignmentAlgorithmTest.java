package demo.akiagaze.algorithm.bioinformation.alignment.smith.waterman;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class SmithWatermanSequenceAlignmentAlgorithmTest {
  @Test
  public void should_get_final_sequences_with_highest_score() {
    String sequence1 = "GCCCTAGCG";
    String sequence2 = "GCGCAATG";

    SmithWatermanSequenceAlignmentAlgorithm alignmentAlgorithm = new SmithWatermanSequenceAlignmentAlgorithm(sequence1, sequence2);
    String[] alignment = alignmentAlgorithm.getAlignment();

    Assert.assertThat(alignment[0], CoreMatchers.is("GCG"));
    Assert.assertThat(alignment[1], CoreMatchers.is("GCG"));
  }
}
