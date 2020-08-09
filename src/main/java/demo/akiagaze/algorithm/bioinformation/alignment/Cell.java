package demo.akiagaze.algorithm.bioinformation.alignment;

public class Cell {
  private Cell prev;
  private int score;
  private final int row;
  private final int col;

  private boolean traced;

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

  public boolean isTraced() {
    return traced;
  }

  public void setTraced(boolean traced) {
    this.traced = traced;
  }
}
