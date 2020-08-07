package demo.akiagaze.algorithm.bioinformation.lcs;

class Cell {
  private Cell prev;
  private int score;
  private final int row;
  private final int col;

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public Cell getPrev() {
    return prev;
  }

  public void setPrev(Cell prev) {
    this.prev = prev;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}
