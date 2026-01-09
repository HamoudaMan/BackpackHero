package game.model.backpack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import game.model.item.Item;
import game.model.item.ItemInstance;
import game.model.representation.Coord;
import game.model.representation.StateRotation;

public class BackPack {
  private final int xStuff;
  private final int yStuff;
  private final ItemInstance[][] stuff;
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
    this.stuff = new ItemInstance[yStuff][xStuff];
    this.unlocked = new boolean[yStuff][xStuff];
    for(int i = yMinUnlocked; i <= yMaxUnlocked; i++) {
      for(int j = xMinUnlocked; j <= xMaxUnlocked; j++) {
      
        unlocked[i][j] = true;
      }
    }
    this.mana = 0;
    this.gold = 0;
  }
  //managing the gold : 
  public boolean hasGold(int amount) {
  	if(amount < 0) {
  		throw new IllegalArgumentException();
  	}
  	return gold >= amount;
  }
  
  public void spendGold(int amount ) {
  	if(amount < 0) {
  		throw new IllegalArgumentException();
  	}
  	if(hasGold(amount)) {
  		gold -=amount;
  	}
  }
  
  public void addGold(int amount) {
  	if(amount < 0) {
  		throw new IllegalArgumentException();
  	}
  	gold+= amount ;
  }
  private boolean nextToUnlocked(Coord coord) {
    Objects.requireNonNull(coord);
    var x = coord.x();
    var y = coord.y();
    if(x - 1 >= 0 && unlocked[y][x-1]) {
      return true;
    }
    if(y - 1 >= 0 && unlocked[y-1][x]) {
      return true;
    }
    if(x + 1 < xStuff && unlocked[y][x+1]) {
      return true;
    }
    if(y + 1 < yStuff && unlocked[y+1][x]) {
      return true;
    }
    return false;
  }
  
  public boolean canUnlockCase(Coord coord) {
    Objects.requireNonNull(coord);
    if(coord.x() >= xStuff) {
      throw new IllegalArgumentException("coord x must be < xStuff");
    }
    if(coord.y() >= yStuff) {
      throw new IllegalArgumentException("coord y must be < yStuff");
    }
    if(unlocked[coord.y()][coord.x()]) {
      return false;
    } else if (nextToUnlocked(coord)) {
      return true;
    }
    return false;
  }
  
  public void UnlockCase(Coord coord) {
    Objects.requireNonNull(coord);
    if(coord.x() >= xStuff) {
      throw new IllegalArgumentException("coord x must be < xStuff");
    }
    if(coord.y() >= yStuff) {
      throw new IllegalArgumentException("coord y must be < yStuff");
    }
    unlocked[coord.y()][coord.x()] = true;
  }
  
  public boolean placeableItem(Item item, Coord coord, StateRotation rotation) {
    Objects.requireNonNull(item);
    Objects.requireNonNull(coord);
    var x = coord.x();
    var y = coord.y();
    if(y >= yStuff) {
      throw new IllegalArgumentException("coord y must be < yStuff");
    }
    if(x >= xStuff) {
      throw new IllegalArgumentException("coord x must be < xStuff");
    }
    var shape = item.shape();
    if(rotation == StateRotation.Rotate) {
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
          if(stuff[actualRow][actualCol] != null || !unlocked[actualRow][actualCol]) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public ItemInstance createItemIntance(Item item, Coord coord, StateRotation rotation) {
    Objects.requireNonNull(item);
    Objects.requireNonNull(coord);
    var x = coord.x();
    var y = coord.y();
    if(y >= yStuff) {
      throw new IllegalArgumentException("y < yStuff");
    }
    if(x >= xStuff) {
      throw new IllegalArgumentException("x < xStuff");
    }
    var shape = item.shape();
    if(rotation == StateRotation.Rotate) {
      shape = rotateShapeCounterClockwiseBy90Degrees(shape);
    }
    var coordItemInstance = new ArrayList<Coord>();
    for(int i = 0; i < shape.length; i++) {
      for(int j = 0; j < shape[i].length; j++) {
        if(shape[i][j]) {
          var oneCoordOfItem = new Coord(x+j, y+i);
          
          coordItemInstance.add(oneCoordOfItem);
        }
      }
    }
    return new ItemInstance(item, coordItemInstance);
  }
  
  public void addItemInstanceToBackpack(ItemInstance itemInstance) {
    Objects.requireNonNull(itemInstance);
    var coord = itemInstance.coord();
    for(var element: coord) {
      if(stuff[element.y()][element.x()] != null) {
        throw new IllegalArgumentException("can't place new itemInstance there is a itemInstance ");
      }
      stuff[element.y()][element.x()] = itemInstance;
    }
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
  
  public void removeInstanceFromBackpack(Coord coord) {
    var x = coord.x();
    var y = coord.y();
    if(y >= yStuff) {
      throw new IllegalArgumentException("coord y must be < yStuff");
    }
    if(x >= xStuff) {
      throw new IllegalArgumentException("coord x must be < xStuff");
    }
    var itemInstance = stuff[y][x];
    if(itemInstance == null) {
      throw new IllegalArgumentException("can't remove instance when there is null");
    }
    for(var element: itemInstance.coord()) {
      stuff[element.y()][element.x()] = null;
    }
  }
  
  public int getMana() {
    return mana;
  }
  
  public int getGold() {
    return gold;
  }
  
  public boolean[][] getUnlocked() {
    var copy = new boolean[yStuff][xStuff];
    for(int i = 0; i < yStuff; i++) {
      copy[i] = unlocked[i].clone();
    }
    return copy;
  }
  
  public ItemInstance[][] getStuff() {
    var copy = new ItemInstance[yStuff][xStuff];
    for(int i = 0; i < yStuff; i++) {
      copy[i] = stuff[i].clone();
    }
    return copy;
  }
}
