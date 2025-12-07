package game.state;

import java.util.List;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.ennemies.Enemy;
import game.ennemies.SmallRatWolf;
import game.hero.Hero;
import game.window.DrawRoom;
import game.window.Window;

public class GameState {
   public void start() {
     var window = new Window();
     window.OpenWindow();
     var context = window.GetContext();
     var screenWigth = context.getScreenInfo().width();
     var screenHeight = context.getScreenInfo().height();
     
//####### POUR DESSINER LE FLOOR ###########
//     var drawFloor = new DrawFloor(screenWigth, screenHeight);
//     var floor = new Floor(1);
//     var row = floor.getRows();
//     var col = floor.getCols();
//     var floorRoom = floor.floor();
//     var coordHero = floor.postionHero();
     
//     context.renderFrame(f -> drawFloor.drawAllCaseDungeon(f, floorRoom, coordHero, row, col));
     
//##########################################
     
//###### POUR DESSINER LA ROOM #############
     var drawRoom = new DrawRoom(screenWigth, screenHeight);
     var hero = new Hero("Hero");
     
     
//   POUR DESSINER LA ROOM AVEC DES ENNEMIES
     List<Enemy> ennemies = List.of(new SmallRatWolf(), new SmallRatWolf(), new SmallRatWolf());
     context.renderFrame(f -> drawRoom.DrawRoomEnemy(f, ennemies, hero));
//   ---------------------------------------
//   POUR DESSINER LA ROOM CORRIDOR
//     context.renderFrame(f -> drawRoom.DrawRoomCorridor(f, hero));
//   ---------------------------------------  
//   POUR DESSINER LA ROOM TREASURE
//     context.renderFrame(f -> drawRoom.DrawRoomTreasure(f, hero));
//##########################################
     
     while(true) {
       var pointerEvent = context.pollEvent();
       switch (pointerEvent) {
         case PointerEvent _ -> {context.dispose(); System.exit(0);}
         case KeyboardEvent _ -> {context.dispose(); System.exit(0);}
         case null -> {
//           ####### POUR DESSINER LE FLOOR ###########
//           context.renderFrame(f -> drawFloor.drawAllCaseDungeon(f, floorRoom, coordHero, row, col));
           
           
         }
       }
     }
   }
}
