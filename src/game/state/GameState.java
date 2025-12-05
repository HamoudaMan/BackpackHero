package game.state;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.dungeon.Floor;
import game.window.DrawFloor;
import game.window.Window;

public class GameState {
   public void start() {
     var window = new Window();
     window.OpenWindow();
     var context = window.GetContext();
     var screenWigth = context.getScreenInfo().width();
     var screenHeight = context.getScreenInfo().height();
     var drawFloor = new DrawFloor(screenWigth, screenHeight);
     
     var floor = new Floor(1);
     
     var row = floor.getRows();
     var col = floor.getCols();
     var floorRoom = floor.floor();
     var coordHero = floor.postionHero();
     
     
     context.renderFrame(f -> drawFloor.drawAllCaseDungeon(f, floorRoom, coordHero, row, col));
     
     while(true) {
       var pointerEvent = context.pollEvent();
       switch (pointerEvent) {
         case PointerEvent _ -> {context.dispose(); System.exit(0);}
         case KeyboardEvent _ -> {context.dispose(); System.exit(0);}
         case null -> {context.renderFrame(f -> drawFloor.drawAllCaseDungeon(f, floorRoom, coordHero, row, col));;}
       }
     }
   }
}
