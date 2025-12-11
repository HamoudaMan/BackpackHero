package game.zen.controller;

import java.util.List;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.dungeon.RoomType;
import game.ennemies.Enemy;
import game.ennemies.RatWolf;
import game.ennemies.SmallRatWolf;
import game.hero.Hero;
import game.items.MagicBackPack;
import game.items.weapons.WoodenSword;
import game.zen.view.DrawFloor;
import game.zen.view.DrawHero;
import game.zen.view.DrawMerchantRoom;
import game.zen.view.DrawMiniMap;
import game.zen.view.DrawRoom;
import game.zen.view.DrawTreasureRoom;
import game.zen.view.Window;
import game.zen.state.ZenGameState;
import game.zen.view.DrawBackGround;
import game.zen.view.DrawBackPack;
import game.zen.view.DrawEnemyRoom;

public class ZenController {
	private Coord posHero;//obligé de passé posHero ici sinn j'ai des pobleme avec le swithc et le render
	private ZenGameState state = ZenGameState.FLOOR;// cas de base on commence dans le couloir 
	
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
			var treasureRoom = new DrawTreasureRoom();
			var enemiesRoom = new DrawEnemyRoom();
			var merchanRoom = new DrawMerchantRoom();
		  List<Enemy> enn = List.of(new RatWolf(), new SmallRatWolf(), new RatWolf());//juste pour debug et test
			
			
			
			//boucle de jeu 
			while(true) {
				var event = context.pollEvent();
				switch(event) {//soint pointerEvent(souris) ou keyboardEvent(clavier) ou null rien 
				case PointerEvent p ->{
					if(p.action() == PointerEvent.Action.POINTER_DOWN) {//un click
						var mouseX = p.location().x();
						var mouseY = p.location().y();
						//si on est dans un combat 
						if( state == ZenGameState.ENEMYROOM) {
							break;//on sort on = one bouge pas le hero et on va au prochain renderFrame 
						}
						//sinnon on bouge le hero
						target = miniMapController.convertClick(mouseX, mouseY);
						this.posHero = miniMapController.tryMove(floor, posHero,target);//le hero bouge , il change de salle 
						
						//on check le type de la salle Pour savoir quoi render par la suite 
						RoomType type = floor.getRoomInfo(posHero.row(),	posHero.col() ).type();
						switch(type) {//un switch pour le render qui suit 
							case ENEMY -> state = ZenGameState.ENEMYROOM;
							case TREASURE -> state = ZenGameState.TREASUREROOM;
							case MERCHANT -> state = ZenGameState.MERCHANTROOM;
							case HEALER -> state = ZenGameState.HEALERROOM;
							case EXIT -> state = ZenGameState.EXITROOM;
							default -> state = ZenGameState.FLOOR;//on se balade dans la map
						}
					}
				}
				case KeyboardEvent e ->{context.dispose(); System.exit(0);}// si on clique sur une touche on quitte le jeu
				case null ->{}
				}
			
				//Ce qui sera render a chaque fois : 
				context.renderFrame(g-> { bg.render(g, screenWidth, screenHeight);
								backpack.render(g, hero.backPack(), screenWidth, screenHeight) ;
								miniMap.render(g, floor, this.posHero, screenWidth, screenHeight);
								heroInDungeon.render(g, screenWidth, screenHeight);
								switch(state) {
									case FLOOR ->{}
									case MERCHANTROOM -> {  merchanRoom.render(g, screenWidth, screenHeight);;
									}
									case TREASUREROOM -> {/*ajouter le render ici */}
									case ENEMYROOM -> {var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
									enemiesRoom.render(g, enemies, screenWidth, screenHeight);   }
									case HEALERROOM -> {/*ajouter le render ici */}
									case EXITROOM -> {/*ajouter le render ici */}
									}
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
