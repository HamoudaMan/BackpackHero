package game;

import game.model.backpack.BackPack;
import game.model.hero.Hero;
import game.model.item.Weapon;

public class Main {
  public static void main(String[] args) {
    var hero = new Hero("Test", 25, 25);
    var backpack = new BackPack(7, 5, 2, 1, 4, 3);
    var itemTest = new Weapon("Test", 0, 0, 0, new boolean[][] {{true, true, false}}); 
    System.out.println(printBooleanGrid(backpack.getUnlocked()));
    System.out.println(printBooleanGrid(itemTest.shape()));
    System.out.println(printBooleanGrid(backpack.rotateShapeCounterClockwiseBy90Degrees(itemTest.shape())));
    System.out.println(backpack.placeableItem(itemTest, 2, 1, false));
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
}
