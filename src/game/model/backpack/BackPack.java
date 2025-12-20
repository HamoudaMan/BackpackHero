package game.model.backpack;

import java.util.Objects;

import game.model.item.Item;

public class BackPack {
  private final int xStuff;
  private final int yStuff;
  private final Item[][] stuff;
  private final boolean[][] unlocked;
  /**
   * Represent the mana of Hero.
   */
  private int mana;
  /**
   * Represent the gold of Hero
   */
  private int gold;
  
  public BackPack(int xStuff, int yStuff, int xMinUnlocked, int yMinUnlocked, int xMaxUnlocked, int yMaxUnlocked) {
    if(xStuff < 0 && xStuff < xMinUnlocked && xStuff < xMaxUnlocked) {
      throw new IllegalArgumentException("xStuff must be > 0 and xStuff > xMinUnlocked and xStuff > xMaxUnlocked");
    }
    if(yStuff < 0 && yStuff < yMinUnlocked && yStuff < yMaxUnlocked) {
      throw new IllegalArgumentException("yStuff must be > 0 and stuffRows > yMinUnlocked and stuffRows > yMaxUnlocked");
    }
    if(xMinUnlocked < 0 && xMinUnlocked > xMaxUnlocked) {
      throw new IllegalArgumentException("xMinUnlocked must be > 0 and xMinUnlocked < xMaxUnlocked");
    }
    if(yMinUnlocked < 0 && yMinUnlocked > yMaxUnlocked) {
      throw new IllegalArgumentException("yMinUnlocked must be > 0 and yMinUnlocked < yMaxUnlocked");
    }
    if(xMaxUnlocked < 0) {
      throw new IllegalArgumentException("xMaxUnlocked must be > 0");
    }
    if(yMaxUnlocked < 0) {
      throw new IllegalArgumentException("yMaxUnlocked must be > 0");
    }
    this.yStuff = yStuff;
    this.xStuff = xStuff;
    this.stuff = new Item[yStuff][xStuff];
    this.unlocked = new boolean[yStuff][xStuff];
    for(int i = yMinUnlocked; i <= yMaxUnlocked; i++) {
      for(int j = xMinUnlocked; j <= xMaxUnlocked; j++) {
      
        unlocked[i][j] = true;
      }
    }
    this.mana = 0;
    this.gold = 0;
  }
  
  public boolean placeableItem(Item item, int x, int y, boolean rotate) {
    Objects.requireNonNull(item);
    if(y < 0 && y > yStuff) {
      throw new IllegalArgumentException("y must be < 0 and y < yStuff");
    }
    if(x < 0 && x > xStuff) {
      throw new IllegalArgumentException("x must be < 0 and x < xStuff");
    }
    var shape = item.shape();
    if(rotate) {
      shape = rotateShapeCounterClockwiseBy90Degrees(shape);
    }
    for(int i = 0; i < shape.length; i++) {
      for(int j = 0; j < shape[i].length; j++) {
        if(shape[i][j]) {
          var actualRow = y + i;
          var actualCol = x + j;
          if(actualRow  > yStuff || actualCol > xStuff) {
            return false;
          }          
          System.out.println(stuff[actualRow][actualCol] != null || unlocked[actualRow][actualCol]);
          if(stuff[actualRow][actualCol] != null || !unlocked[actualRow][actualCol]) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  // need to change to private only public for test
  public boolean[][] rotateShapeCounterClockwiseBy90Degrees(boolean[][] shape) {
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
  
  public int getMana() {
    return mana;
  }
  
  public int getGold() {
    return gold;
  }
  
  public boolean[][] getUnlocked() {
    return unlocked;
  }
}
