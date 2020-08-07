package demo.akiagaze.algorithm.bioinformation.lcs;

public class LongestCommonSubsequenceAlgorithm implements LCSAlgorithm {

  @Override
  public String lcs(String s1, String s2) {
    Cell[][] lcsMatrix = this.initLCSMatrix(s1, s2);
    this.fillLCSMatrix(lcsMatrix, s1, s2);
    return this.trackLCS(lcsMatrix, s1, s2);
  }

  private Cell[][] initLCSMatrix(String s1, String s2) {
    int nrow = s1.length() + 1;
    int ncol = s2.length() + 1;
    Cell[][] matrix = new Cell[nrow][ncol];

    for (int i = 0; i < nrow; i++) {
      for (int j = 0; j < ncol; j++) {
        matrix[i][j] = new Cell(i, j);
      }
    }

    return matrix;
  }

  private void fillLCSMatrix(Cell[][] matrix, String s1, String s2) {
    char[] cs1 = s1.toCharArray();
    char[] cs2 = s2.toCharArray();

    for (int i = 1; i < matrix.length; i++) {
      for (int j = 1; j < matrix[i].length; j++) {
        char c1 = cs1[i - 1];
        char c2 = cs2[j - 1];

        Cell l2 = matrix[i - 1][j];
        Cell l1 = matrix[i][j - 1];
        Cell l3 = matrix[i - 1][j - 1];

        Cell prevCell = l3;
        int score = l3.getScore();
        if (c1 == c2) {
          score = l3.getScore() + 1;
          if (l1.getScore() > score) {
            if (l2.getScore() > l1.getScore()) {
              score = l2.getScore();
              prevCell = l2;
            } else {
              score = l1.getScore();
              prevCell = l1;
            }
          }
          if (l2.getScore() > score) {
            if (l1.getScore() > l2.getScore()) {
              score = l1.getScore();
              prevCell = l1;
            } else {
              score = l2.getScore();
              prevCell = l2;
            }
          }
        } else {
          if (l1.getScore() > l3.getScore()) {
            if (l2.getScore() > l1.getScore()) {
              score = l2.getScore();
              prevCell = l2;
            } else {
              score = l1.getScore();
              prevCell = l1;
            }
          }
          if (l2.getScore() > l3.getScore()) {
            if (l1.getScore() > l2.getScore()) {
              score = l1.getScore();
              prevCell = l1;
            } else {
              score = l2.getScore();
              prevCell = l2;
            }
          }
        }

        Cell currentCell = matrix[i][j];
        currentCell.setPrev(prevCell);
        currentCell.setScore(score);
      }
    }
  }

  private String trackLCS(Cell[][] matrix, String s1, String s2) {
    char[] cs1 = s1.toCharArray();

    Cell currentCell = matrix[s1.length()][s2.length()];
    char[] lcs = new char[currentCell.getScore()];
    int i = 0;

    while (currentCell.getScore() > 0 || currentCell.getPrev() != null) {
      Cell prevCell = currentCell.getPrev();
      if (prevCell.getScore() < currentCell.getScore()) {
        lcs[i++] = cs1[currentCell.getRow() - 1];
      }
      currentCell = prevCell;
    }
    StringBuilder builder = new StringBuilder();
    for (i = lcs.length; i > 0; i--) {
      builder.append(lcs[i - 1]);
    }
    return builder.toString();
  }

}
