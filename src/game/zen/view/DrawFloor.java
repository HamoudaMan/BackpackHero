package game.zen.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;
import java.util.Set;

import game.dungeon.Coord;
import game.dungeon.Room;
import game.dungeon.RoomType;

public class DrawFloor {
  private final int outline = 6;
  private final int roomSeparator;
  private final int screenWidth;
  private final int screenHeight;
  
  
  public DrawFloor(int screenWidth, int screenHeight) {
    if(screenWidth <= 0 || screenHeight <= 0) {
      throw new IllegalArgumentException("screenWidth and screenHeight must be > 0");
    };
    
    this.roomSeparator = screenWidth/50;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }
  
  public void drawAllCaseDungeon(Graphics2D graphics, Room[][] floorRoom, Coord positionHero, int col, int row) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(floorRoom);
    Objects.requireNonNull(positionHero);    
    
    var roomCol = screenHeight / (col+2);
    var roomRow = screenWidth / (row+2); 
    
    graphics.setStroke(new BasicStroke(outline));
    
    for (int i = 0; i < col-1; i++) {
      for (int j = 0 ; j < row-1; j++) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(roomRow*j + roomSeparator/2, roomCol*i + roomSeparator/2, roomRow - roomSeparator, roomCol - roomSeparator);
        switch (floorRoom[i][j].type()) {
        case CORRIDOR -> {
          graphics.drawRect(roomRow*j + roomSeparator, roomCol*i + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);  
        }
     
        case TREASURE -> {
          graphics.setColor(Color.YELLOW);
          graphics.fillRect(roomRow*j + roomSeparator, roomCol*i + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + roomSeparator, roomCol*i + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);
        }
        
        case ENEMY -> {
          graphics.setColor(Color.RED);
          graphics.fillRect(roomRow*j + roomSeparator, roomCol*i + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + roomSeparator, roomCol*i + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);
        }
        default -> {}
        }
      }
    }
    // draw where the hero is;
    /*
    var colHero = positionHero.col();
    var rowHero = positionHero.row();
    graphics.setColor(Color.CYAN);
    graphics.fillRect(roomRow*rowHero + roomSeparator, roomCol*colHero + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(roomRow*rowHero + roomSeparator, roomCol*colHero + roomSeparator, roomRow - roomSeparator*2, roomCol - roomSeparator*2);
    */
 // draw where the hero is:
    var colHero = positionHero.col(); // 0 à COLS-1
    var rowHero = positionHero.row(); // 0 à ROWS-1

    graphics.setColor(Color.CYAN);
    graphics.fillRect(
        roomRow * (colHero + 1) + roomSeparator,
        roomCol * (rowHero + 1) + roomSeparator,
        roomRow - roomSeparator*2,
        roomCol - roomSeparator*2
    );

    graphics.setColor(Color.BLACK);
    graphics.drawRect(
        roomRow * (colHero + 1) + roomSeparator,
        roomCol * (rowHero + 1) + roomSeparator,
        roomRow - roomSeparator*2,
        roomCol - roomSeparator*2
    );
  }
}