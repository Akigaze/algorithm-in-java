package demo.akiagaze.algorithm.bioinformation.lcs;

public class LongestCommonSubsequenceAlgorithm implements LCSAlgorithm {

  private final String sequence1;
  private final String sequence2;

  public LongestCommonSubsequenceAlgorithm(String sequence1, String sequence2) {
    this.sequence1 = sequence1;
    this.sequence2 = sequence2;
  }

  @Override
  public String lcs() {
    Cell[][] scoreMatrix = this.initializeScoreMatrix();
    this.fillInScoreMatrix(scoreMatrix);
    return this.getTraceBack(scoreMatrix);
  }

  private Cell[][] initializeScoreMatrix() {
    Cell[][] scoreMatrix = new Cell[sequence1.length() + 1][sequence2.length() + 1];

    for (int row = 0; row < scoreMatrix.length; row++) {
      for (int col = 0; col < scoreMatrix[row].length; col++) {
        scoreMatrix[row][col] = new Cell(row, col);
      }
    }

    return scoreMatrix;
  }

  private void fillInScoreMatrix(Cell[][] scoreMatrix) {
    for (int row = 1; row < scoreMatrix.length; row++) {
      for (int col = 1; col < scoreMatrix[row].length; col++) {
        Cell currentCell = scoreMatrix[row][col];
        Cell cellLeft = scoreMatrix[row][col - 1];
        Cell cellAbove = scoreMatrix[row - 1][col];
        Cell cellAboveLeft = scoreMatrix[row - 1][col - 1];
        this.fillInCell(currentCell, cellLeft, cellAbove, cellAboveLeft);
      }
    }
  }

  private void fillInCell(Cell currentCell, Cell cellLeft, Cell cellAbove, Cell cellAboveLeft) {
    int leftScore = cellLeft.getScore();
    int aboveScore = cellAbove.getScore();
    int matchScore;
    if (sequence1.charAt(currentCell.getRow() - 1) == sequence2.charAt(currentCell.getCol() - 1)) {
      matchScore = cellAboveLeft.getScore() + 1;
    } else {
      matchScore = cellAboveLeft.getScore();
    }
    int score;
    Cell prevCell;

    if (matchScore >= leftScore) {
      if (matchScore >= aboveScore) {
        score = matchScore;
        prevCell = cellAboveLeft;
      } else {
        score = aboveScore;
        prevCell = cellAbove;
      }
    } else {
      if (leftScore >= aboveScore) {
        score = leftScore;
        prevCell = cellLeft;
      } else {
        score = aboveScore;
        prevCell = cellAbove;
      }
    }
    currentCell.setPrev(prevCell);
    currentCell.setScore(score);
  }

  private String getTraceBack(Cell[][] scoreMatrix) {
    Cell currentCell = scoreMatrix[scoreMatrix.length - 1][scoreMatrix[0].length - 1];
    char[] lcsChars = new char[currentCell.getScore()];
    int i = lcsChars.length - 1;

    while (currentCell.getScore() > 0 || currentCell.getPrev() != null) {
      Cell prevCell = currentCell.getPrev();
      if (currentCell.getRow() - prevCell.getRow() == 1 && currentCell.getCol() - prevCell.getCol() == 1 && currentCell.getScore() - prevCell.getScore() == 1) {
        lcsChars[i--] = sequence1.charAt(currentCell.getRow() - 1);
      }
      currentCell.setTraced(true);
      currentCell = prevCell;
    }
    new ScoreMatrixPrinter(scoreMatrix, sequence1, sequence2).print();
    return new String(lcsChars);
  }

}
