package game;

import game.model.backpack.BackPack;
import game.model.hero.Hero;
import game.model.item.ItemInstance;
import game.model.item.Weapon;
import game.model.representation.Coord;
import game.model.representation.StateRotation;

public class Main {
  public static void main(String[] args) {
    var hero = new Hero("Test", 25, 25);
    var backpack = new BackPack(7, 5, 2, 1, 4, 3);
    var itemTest = new Weapon("Test", 0, 0, 0, new boolean[][] {{true, true, false}}); 
    var stateRotation = StateRotation.Base;
    var coord = new Coord(2, 1);
    var coord1 = new Coord(5, 3);
    System.out.println("table of unlocked");
    System.out.println(printBooleanGrid(backpack.getUnlocked()));
    System.out.println("shape item");
    System.out.println(printBooleanGrid(itemTest.shape()));
    System.out.println("shape item rotated");
    System.out.println(printBooleanGrid(backpack.rotateShapeCounterClockwiseBy90Degrees(itemTest.shape())));
    System.out.println("bool placeable item");
    System.out.println(backpack.placeableItem(itemTest, coord, stateRotation));
    System.out.println("place item instance");
    var itemInstance = backpack.createItemIntance(itemTest, coord, stateRotation);
    backpack.addItemInstanceToBackpack(itemInstance);    
    System.out.println(printItemGrid(backpack.getStuff()));
    System.out.println("bool can unlock");
    System.out.println(backpack.canUnlockCase(coord1));
    System.out.println("remove instance item");
    backpack.removeInstanceFromBackpack(coord);
    System.out.println(printItemGrid(backpack.getStuff()));
    System.out.println();
  }
  
  public static String printBooleanGrid(boolean[][] grid) {
    var builder = new StringBuilder();
    for(var i = 0; i < grid.length; i++) {
      for(var j = 0; j < grid[i].length; j++) {
        if (grid[i][j]) {
          builder.append("[1]");
        } else {
          builder.append("[0]");
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }
  
  public static String printItemGrid(ItemInstance[][] grid) {
    var builder = new StringBuilder();
    for(var i = 0; i < grid.length; i++) {
      for(var j = 0; j < grid[i].length; j++) {
        if (grid[i][j] != null) {
          builder.append("[1]");
        } else {
          builder.append("[0]");
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
