package game.zen.controller;

import java.util.List;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.ennemies.Enemy;
import game.ennemies.SmallRatWolf;
import game.hero.Hero;
import game.items.MagicBackPack;
import game.items.weapons.WoodenSword;
import game.zen.view.DrawFloor;
import game.zen.view.DrawHero;
import game.zen.view.DrawMiniMap;
import game.zen.view.DrawRoom;
import game.zen.view.DrawTreasureRoom;
import game.zen.view.Window;
import game.zen.view.DrawBackGround;
import game.zen.view.DrawBackPack;

public class ZenController {
	private Coord posHero;//obligé de passé posHero ici sinn j'ai des pobleme avec le swithc et le render
	
	public void start() {
		var window = new Window();
		window.open(context -> {
			var screenWidth = context.getScreenInfo().width();
			var screenHeight = context.getScreenInfo().height();
			
			Hero hero = new Hero("JOTARO KUJO");
			hero.addToBackPack(new WoodenSword());
			var floor = new Floor(1);
			this.posHero = floor.postionHero();//pos initiae du hero 
			Coord target = null;
			
			var bg = new DrawBackGround();
			var backpack = new DrawBackPack();
			var miniMap = new DrawMiniMap(); 
			var miniMapController = new MiniMapController(miniMap);
			var heroInDungeon = new DrawHero();
			var treasure = new DrawTreasureRoom();
			
			
			
			//boucle de jeu 
			while(true) {
				var event = context.pollEvent();
				switch(event) {//soint pointerEvent(souris) ou keyboardEvent(clavier) ou null rien 
				case PointerEvent p ->{
					if(p.action() == PointerEvent.Action.POINTER_DOWN) {
						var mouseX = p.location().x();
						var mouseY = p.location().y();
						target = miniMapController.convertClick(mouseX, mouseY);
						this.posHero = miniMapController.tryMove(floor, posHero,target);
					}
					break;
					
				}
				case KeyboardEvent k ->{}
				case null ->{}
				}
				/*
				if(event != null) {
					context.dispose();
					System.exit(0);
				}*/
				context.renderFrame(g-> { bg.render(g, screenWidth, screenHeight);
								backpack.render(g, hero.backPack(), screenWidth, screenHeight) ;
								miniMap.render(g, floor, this.posHero, screenWidth, screenHeight);
								heroInDungeon.render(g, screenWidth, screenHeight);
								treasure.render(g,  screenWidth, screenHeight);
				});
				
			}
		});
	}

	
	
     /*
//####### POUR DESSINER LE FLOOR ###########
//     var drawFloor = new DrawFloor(screenWigth, screenHeight);
//     var floor = new Floor(1);
//     var row = floor.getRows();
//     var col = floor.getCols();
//     var floorRoom = floor.floor();
//     var coordHero = floor.postionHero();
//     
//     context.renderFrame(f -> drawFloor.drawAllCaseDungeon(f, floorRoom, coordHero, row, col));
     
//##########################################
     
//###### POUR DESSINER LA ROOM #############
     var drawRoom = new drawRoom(screenWigth, screenHeight);
     var hero = new Hero("Hero");
//     
     
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
         case PointerEvent e -> {context.dispose(); System.exit(0);}
         case KeyboardEvent e -> {context.dispose(); System.exit(0);}
         case null -> {
//           ####### POUR DESSINER LE FLOOR ###########
//           context.renderFrame(f -> drawFloor.drawAllCaseDungeon(f, floorRoom, coordHero, row, col));
           
           
         }
       }
     }
   }
   */
}
