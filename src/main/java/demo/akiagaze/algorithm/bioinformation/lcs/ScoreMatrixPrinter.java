package demo.akiagaze.algorithm.bioinformation.lcs;

import demo.akiagaze.algorithm.util.constant.SpecificCharacter;

public class ScoreMatrixPrinter {

  private final Cell[][] scoreMatrix;
  private final String sequence1;
  private final String sequence2;

  private int cellWidth;

  public ScoreMatrixPrinter(Cell[][] scoreMatrix, String sequence1, String sequence2) {
    this.scoreMatrix = scoreMatrix;
    this.sequence1 = sequence1;
    this.sequence2 = sequence2;
    int maxScore = scoreMatrix[scoreMatrix.length - 1][scoreMatrix[0].length - 1].getScore();
    this.cellWidth = String.valueOf(maxScore).length();
  }

  public void print() {
    ConsoleCell[][] chars = this.initialConsoleCellTable();
    this.fillInHeaderRow(chars, sequence2);
    this.fillInConsoleCellTable(chars);
    printToConsole(chars);
  }

  private ConsoleCell[][] initialConsoleCellTable() {
    ConsoleCell[][] chars = new ConsoleCell[scoreMatrix.length * 2 + 1][scoreMatrix[0].length * 2 + 1];

    for (int row = 0; row < chars.length; row++) {
      for (int col = 0; col < chars[row].length; col++) {
        chars[row][col] = this.createConsoleCell(' ');
      }
    }
    return chars;
  }

  private void fillInHeaderRow(ConsoleCell[][] chars, String sequence) {
    for (int i = 0; i < sequence.length(); i++) {
      char c = sequence.charAt(i);
      chars[0][i * 2 + 4] = this.createConsoleCell(c);
    }
  }

  private void fillInConsoleCellTable(ConsoleCell[][] chars) {
    for (int row = 0; row < scoreMatrix.length; row++) {
      if (row - 1 >= 0) {
        chars[row * 2 + 2][0] = this.createConsoleCell(sequence1.charAt(row - 1));
      }
      for (int col = 0; col < scoreMatrix[row].length; col++) {
        this.fillInConsoleCell(scoreMatrix[row][col], chars, row * 2 + 2, col * 2 + 2);
      }
    }
  }

  private void fillInConsoleCell(Cell cell, ConsoleCell[][] chars, int row, int col) {
    chars[row][col] = this.createConsoleCell(cell.getScore(), cell.isTraced());
    Cell prevCell = cell.getPrev();
    if (prevCell == null) {
      return;
    }
    if (prevCell.getRow() < cell.getRow() && prevCell.getCol() < cell.getCol()) {
      chars[row - 1][col - 1] = this.createConsoleCell(SpecificCharacter.ABOVE_LEFT_ARROW, cell.isTraced());
    } else if (prevCell.getRow() < cell.getRow()) {
      chars[row - 1][col] = this.createConsoleCell(SpecificCharacter.ABOVE_ARROW, cell.isTraced());
    } else if (prevCell.getCol() < cell.getCol()) {
      chars[row][col - 1] = this.createConsoleCell(SpecificCharacter.LEFT_ARROW, cell.isTraced());
    }
  }

  private ConsoleCell createConsoleCell(Object content, boolean highlight) {
    String c = String.valueOf(content);
    int gap = cellWidth - c.length();
    if (gap > 0) {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < gap; i++) {
        builder.append(' ');
      }
      builder.append(c);
      return new ConsoleCell(builder.toString(), highlight);
    }
    return new ConsoleCell(content, highlight);
  }

  private ConsoleCell createConsoleCell(Object content) {
    return this.createConsoleCell(content, false);
  }

  private static void printToConsole(ConsoleCell[][] chars) {
    for (ConsoleCell[] aChar : chars) {
      for (ConsoleCell c : aChar) {
        if (c.isHighlight()) {
          System.out.print("\033[32;4m" + c.getContent() + "\033[0m");
        } else {
          System.out.print(c.getContent());
        }
        System.out.print('|');
      }
      System.out.println();
    }
  }

  static class ConsoleCell {
    private final String content;
    private boolean highlight;

    public ConsoleCell(Object content, boolean highlight) {
      this.content = String.valueOf(content);
      this.highlight = highlight;
    }

    public String getContent() {
      return content;
    }

    public boolean isHighlight() {
      return highlight;
    }
  }
}
