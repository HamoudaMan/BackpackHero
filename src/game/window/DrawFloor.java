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
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(floorRoom);
    Objects.requireNonNull(positionHero);    
    
    var roomCol = screenHeight / (col+2);
    var roomRow = screenWidth / (row+2); 
    
    graphics.setStroke(new BasicStroke(outline));
    
    for (int i = 1; i < col+1; i++) {
      for (int j = 1 ; j < row+1; j++) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(roomRow*j + RoomSeparator/2, roomCol*i + RoomSeparator/2, roomRow - RoomSeparator, roomCol - RoomSeparator);
        switch (floorRoom[i-1][j-1].type()) {
        case null -> {
          graphics.drawRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);  
        }
     
        case TREASURE -> {
          graphics.setColor(Color.YELLOW);
          graphics.fillRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
        }
        
        case ENEMY -> {
          graphics.setColor(Color.RED);
          graphics.fillRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
          graphics.setColor(Color.BLACK);
          graphics.drawRect(roomRow*j + RoomSeparator, roomCol*i + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
        }
        default -> {}
        }
      }
    }
    // draw where the hero is;
    
    var colHero = positionHero.col()+1;
    var rowHero = positionHero.row()+1;
    graphics.setColor(Color.CYAN);
    graphics.fillRect(roomRow*rowHero + RoomSeparator, roomCol*colHero + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(roomRow*rowHero + RoomSeparator, roomCol*colHero + RoomSeparator, roomRow - RoomSeparator*2, roomCol - RoomSeparator*2);
  }
}
