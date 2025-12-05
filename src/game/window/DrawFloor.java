package game.window;

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
  private final int RoomSeparator;
  private final int screenWidth;
  private final int screenHeight;
  
  
  public DrawFloor(int screenWidth, int screenHeight) {
    if(screenWidth <= 0 || screenHeight <= 0) {
      throw new IllegalArgumentException("screenWidth and screenHeight must be > 0");
    };
    
    this.RoomSeparator = screenWidth/50;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }
  
  public void drawAllCaseDungeon(Graphics2D graphics, Room[][] floorRoom, Coord positionHero, int col, int row) {
    var backRectangleGray = Color.LIGHT_GRAY;
    
    
    var roomCol = screenHeight / (col+2);
    var roomRow = screenWidth / (row+2); 
    
    graphics.setStroke(new BasicStroke(outline));
    
    for (int i = 1; i < col+1; i++) {
      for (int j = 1 ; j < row+1; j++) {
        switch (floorRoom[i-1][j-1].type()) {
        case null -> {
          graphics.setColor(backRectangleGray);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator/2, roomCol*i + RoomSeparator/2, roomRow - RoomSeparator, roomCol - RoomSeparator);
          graphics.drawRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);  
        }
     
        case TREASURE -> {
          graphics.setColor(backRectangleGray);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator/2, roomCol*i + RoomSeparator/2, roomRow - RoomSeparator, roomCol - RoomSeparator);
          graphics.setColor(Color.YELLOW);
          graphics.fillRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
        }
        
        case ENEMY -> {
          graphics.setColor(backRectangleGray);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator/2, roomCol*i + RoomSeparator/2, roomRow - RoomSeparator, roomCol - RoomSeparator);
          graphics.setColor(Color.RED);
          graphics.fillRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
        }
        default -> {}
        }
      }
    }
  }
}
