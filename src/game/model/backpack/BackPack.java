package game.model.backpack;

import java.util.Objects;

import game.model.item.Item;

public class BackPack {
  private final int stuffCols;
  private final int stuffRows;
  private final Item[][] stuff;
  private final boolean[][] unlocked;
  
  public BackPack(int stuffCols, int stuffRows, int unlockedMinCols, int unlockedMinRows, int unlockedMaxCols, int unlockedMaxRows) {
    if(stuffCols < 0) {
      throw new IllegalArgumentException("stuffCols must be > 0");
    }
    if(stuffRows < 0) {
      throw new IllegalArgumentException("stuffRows must be > 0");
    }
    if(unlockedMinCols < 0 && unlockedMinCols > stuffCols) {
      throw new IllegalArgumentException("unlockedMinCols must be > 0 and < stuffCols");
    }
    if(unlockedMinRows < 0 && unlockedMinRows > stuffRows) {
      throw new IllegalArgumentException("unlockedMinRows must be > 0 and < stuffRows");
    }
    if(unlockedMaxCols < 0 && unlockedMaxCols > stuffCols) {
      throw new IllegalArgumentException("unlockedMaxCols must be > 0 and < stuffCols");
    }
    if(unlockedMaxRows < 0 && unlockedMaxRows > stuffRows) {
      throw new IllegalArgumentException("unlockedMaxRows must be > 0 and < stuffRows");
    }
    
    this.stuffCols = stuffCols;
    this.stuffRows = stuffRows;
    this.stuff = new Item[stuffCols][stuffRows];
    this.unlocked = new boolean[stuffCols][stuffRows];
    for(int i = unlockedMinCols; i <= unlockedMaxCols; i++) {
      for(int j = unlockedMinRows; j <= unlockedMaxRows; j++) {
      
        unlocked[i][j] = true;
      }
    }
  }
  
  public boolean placeableItem(Item item, int row, int col, boolean rotate) {
    Objects.requireNonNull(item);
    if(row < 0 && row < stuffRows) {
      throw new IllegalArgumentException("row must be < 0 and stuffRows");
    }
    if(col < 0 && col < stuffCols) {
      throw new IllegalArgumentException("col must be < 0 and stuffCols");
    }
    var shape = item.shape();
    if(rotate) {
      shape = rotateBy90DegreesShape(shape);
    }
    for(int i = 0; i < shape.length; i++) {
      for(int j = 0; j < shape[i].length; j++) {
        if(shape[i][j]) {
          var actualRow = row + j;
          var actualCol = col + i;
          if(actualRow  > stuffRows || actualCol > stuffCols) {
            return false;
          }
          if(stuff[actualCol][actualRow] != null || unlocked[actualCol][actualRow]) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public boolean[][] rotateBy90DegreesShape(boolean[][] shape) {
    Objects.requireNonNull(shape);
    var row = shape.length;
    var col = shape[0].length;
    boolean[][] newShape = new boolean[col][row];
    for(int i = 0; i < row; i++) {
      for(int j = 0; j < col; j++) {
        newShape[col - 1 - j][i] = shape[i][j];
      }
    }
    return newShape;
  }
  
  public boolean[][] getUnlocked() {
    return unlocked;
  }
}
