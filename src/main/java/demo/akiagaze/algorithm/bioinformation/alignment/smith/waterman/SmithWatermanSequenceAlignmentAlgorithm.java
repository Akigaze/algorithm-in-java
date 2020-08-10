package demo.akiagaze.algorithm.bioinformation.alignment.smith.waterman;

import demo.akiagaze.algorithm.bioinformation.alignment.Cell;
import demo.akiagaze.algorithm.bioinformation.alignment.SequenceAlignmentAlgorithm;

public class SmithWatermanSequenceAlignmentAlgorithm implements SequenceAlignmentAlgorithm {
  public static final int MATCHED_SCORE = 1;
  public static final int SPACE_SCORE = -2;
  public static final int UNMATCHED_SCORE = -1;

  public static final char SPACE = '-';

  private final String sequence1;
  private final String sequence2;

  public SmithWatermanSequenceAlignmentAlgorithm(String sequence1, String sequence2) {
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
    return null;
  }

  private int getInitialScore(int row, int col) {
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
        if (matchedOrUnmatchedScore > 0) {
          currentCell.setScore(matchedOrUnmatchedScore);
          currentCell.setPrev(aboveLeftCell);
        }
      } else {
        if (colSpaceScore > 0) {
          currentCell.setScore(colSpaceScore);
          currentCell.setPrev(leftCell);
        }
      }
    } else {
      if (rowSpaceScore >= colSpaceScore) {
        if (rowSpaceScore > 0) {
          currentCell.setScore(rowSpaceScore);
          currentCell.setPrev(aboveCell);
        }
      } else {
        if (colSpaceScore > 0) {
          currentCell.setScore(colSpaceScore);
          currentCell.setPrev(leftCell);
        }
      }
    }
  }

  private String[] getTraceBack(Cell[][] scoreMatrix) {
    Cell currentCell = this.getTraceBackStartingCell(scoreMatrix);
    StringBuilder sequence1Builder = new StringBuilder();
    StringBuilder sequence2Builder = new StringBuilder();

    while (this.traceBackIsNotDone(currentCell)) {
      Cell prevCell = currentCell.getPrev();

      if (currentCell.getRow() - prevCell.getRow() == 1) {
        sequence1Builder.insert(0, sequence1.charAt(currentCell.getRow() - 1));
      } else {
        sequence1Builder.insert(0, SPACE);
      }

      if (currentCell.getCol() - prevCell.getCol() == 1) {
        sequence2Builder.insert(0, sequence2.charAt(currentCell.getCol() - 1));
      } else {
        sequence2Builder.insert(0, SPACE);
      }
      currentCell = prevCell;
    }
    return new String[]{sequence1Builder.toString(), sequence2Builder.toString()};
  }

  private Cell getTraceBackStartingCell(Cell[][] scoreMatrix) {
    Cell highScoreCell = scoreMatrix[scoreMatrix.length - 1][scoreMatrix[0].length - 1];
    for (Cell[] row : scoreMatrix) {
      for (Cell cell : row) {
        if (cell.getScore() > highScoreCell.getScore()) {
          highScoreCell = cell;
        }
      }
    }
    return highScoreCell;
  }

  private boolean traceBackIsNotDone(Cell cell) {
    return cell.getPrev() != null || cell.getScore() > 0;
  }
}
