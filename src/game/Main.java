package game;

import game.model.backpack.BackPack;

public class Main {
  public static void main(String[] args) {
    var backpack = new BackPack(5, 7, 1, 2, 3, 4);
    System.out.println(printBooleanGrid(backpack.getUnlocked()));
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
