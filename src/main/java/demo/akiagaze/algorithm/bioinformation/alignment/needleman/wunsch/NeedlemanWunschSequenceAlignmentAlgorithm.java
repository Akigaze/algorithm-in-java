package demo.akiagaze.algorithm.bioinformation.alignment.needleman.wunsch;

import demo.akiagaze.algorithm.bioinformation.alignment.Cell;
import demo.akiagaze.algorithm.bioinformation.alignment.SequenceAlignmentAlgorithm;


public class NeedlemanWunschSequenceAlignmentAlgorithm implements SequenceAlignmentAlgorithm {
  public static final int MATCHED_SCORE = 1;
  public static final int SPACE_SCORE = -2;
  public static final int UNMATCHED_SCORE = -1;

  public static final char SPACE = '-';

  private final String sequence1;
  private final String sequence2;

  public NeedlemanWunschSequenceAlignmentAlgorithm(String sequence1, String sequence2) {
    this.sequence1 = sequence1;
    this.sequence2 = sequence2;
  }

  @Override
  public String[] getAlignment() {
    Cell[][] scoreMatrix = this.initializeScoreMatrix();
    this.fillInScoreMatrix(scoreMatrix);
    return this.getTraceBack(scoreMatrix);
  }

  private Cell[][] initializeScoreMatrix() {
    Cell[][] scoreMatrix = new Cell[sequence1.length() + 1][sequence2.length() + 1];

    for (int row = 0; row < scoreMatrix.length; row++) {
      for (int col = 0; col < scoreMatrix[row].length; col++) {
        Cell cell = new Cell(row, col);
        cell.setScore(this.getInitialScore(row, col));
        cell.setPrev(this.getInitialPreviousCell(scoreMatrix, row, col));
        scoreMatrix[row][col] = cell;
      }
    }
    return scoreMatrix;
  }

  private Cell getInitialPreviousCell(Cell[][] scoreMatrix, int row, int col) {
    if (row == 0 && col != 0) {
      return scoreMatrix[row][col - 1];
    }
    if (row != 0 && col == 0) {
      return scoreMatrix[row - 1][col];
    }
    return null;
  }

  private int getInitialScore(int row, int col) {
    if (row == 0 && col != 0) {
      return col * SPACE_SCORE;
    }
    if (row != 0 && col == 0) {
      return row * SPACE_SCORE;
    }
    return 0;
  }

  private void fillInScoreMatrix(Cell[][] scoreMatrix) {

    for (int row = 1; row < scoreMatrix.length; row++) {
      for (int col = 1; col < scoreMatrix[row].length; col++) {
        Cell currentCell = scoreMatrix[row][col];
        Cell leftCell = scoreMatrix[row][col - 1];
        Cell aboveCell = scoreMatrix[row - 1][col];
        Cell aboveLeftCell = scoreMatrix[row - 1][col - 1];
        this.fillInCell(currentCell, aboveCell, leftCell, aboveLeftCell);
      }
    }

  }

  private void fillInCell(Cell currentCell, Cell aboveCell, Cell leftCell, Cell aboveLeftCell) {
    int rowSpaceScore = aboveCell.getScore() + SPACE_SCORE;
    int colSpaceScore = leftCell.getScore() + SPACE_SCORE;
    int matchedOrUnmatchedScore = aboveLeftCell.getScore();

    if (sequence1.charAt(currentCell.getRow() - 1) == sequence2.charAt(currentCell.getCol() - 1)) {
      matchedOrUnmatchedScore += MATCHED_SCORE;
    } else {
      matchedOrUnmatchedScore += UNMATCHED_SCORE;
    }
    if (matchedOrUnmatchedScore >= rowSpaceScore) {
      if (matchedOrUnmatchedScore >= colSpaceScore) {
        currentCell.setScore(matchedOrUnmatchedScore);
        currentCell.setPrev(aboveLeftCell);
      } else {
        currentCell.setScore(colSpaceScore);
        currentCell.setPrev(leftCell);
      }
    } else {
      if (rowSpaceScore >= colSpaceScore) {
        currentCell.setScore(rowSpaceScore);
        currentCell.setPrev(aboveCell);
      } else {
        currentCell.setScore(colSpaceScore);
        currentCell.setPrev(leftCell);
      }
    }
  }

  private String[] getTraceBack(Cell[][] scoreMatrix) {
    Cell currentCell = scoreMatrix[scoreMatrix.length - 1][scoreMatrix[0].length - 1];
    int len = Math.max(sequence1.length(), sequence2.length());
    char[] sequence1Chars = new char[len];
    char[] sequence2Chars = new char[len];
    int i = len - 1;

    while (this.traceBackIsNotDone(currentCell)) {
      Cell prevCell = currentCell.getPrev();

      if (currentCell.getRow() - prevCell.getRow() == 1) {
        sequence1Chars[i] = sequence1.charAt(currentCell.getRow() - 1);
      } else {
        sequence1Chars[i] = SPACE;
      }

      if (currentCell.getCol() - prevCell.getCol() == 1) {
        sequence2Chars[i] = sequence2.charAt(currentCell.getCol() - 1);
      } else {
        sequence2Chars[i] = SPACE;
      }
      i--;
      currentCell = prevCell;
    }


    return new String[]{new String(sequence1Chars), new String(sequence2Chars)};
  }

  private boolean traceBackIsNotDone(Cell cell) {
    return cell.getPrev() != null;
  }
}
