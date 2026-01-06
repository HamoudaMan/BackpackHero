package game.zen.controller;



import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.dungeon.Coord;
import game.dungeon.Dungeon;
import game.dungeon.Floor;
import game.dungeon.Room;
import game.dungeon.RoomType;
import game.dungeon.state.DungeonState;
import game.dungeon.state.HealerState;
import game.dungeon.state.TreasureState;
import game.hero.Hero;
import game.items.Item;
import game.items.armor.RoughBuckler;
import game.items.magic.MagicWand;
import game.items.weapons.WoodenSword;

import game.zen.view.DrawHero;
import game.zen.view.DrawItemDescription;
import game.zen.view.DrawMerchantRoom;
import game.zen.view.DrawMiniMap;

import game.zen.view.DrawTreasureRoom;
import game.zen.view.GroundItemHitBox;
import game.zen.view.Window;
import game.zen.view.minimap.DrawMiniMapButton;
import game.zen.view.stats.DrawItemInfo;
import game.zen.imgLoad.ImageLoader;
import game.zen.state.ZenGameState;
import game.zen.view.DrawBackGround;
import game.zen.view.DrawBackPack;
import game.zen.view.DrawCombatBoutons;
import game.zen.view.DrawEnemyRoom;
import game.zen.view.DrawExitDoor;
import game.zen.view.DrawGroundItem;
import game.zen.view.DrawHealerRoom;

public class ZenController {
	private Dungeon dungeon;
	private Floor floor;
	private Coord posHero;//obligé de passé posHero ici sinn j'ai des pobleme avec le swithc et le render
	private ZenGameState state = ZenGameState.FLOOR;// cas de base on commence dans le couloir 
	private boolean showMiniMap = false;
	
	//hero movment
	private final Queue<Coord> movementQueue = new ArrayDeque<>();
	private int moveCoolDown = 0;
	private static final int MOVE_DELAY = 10;//frames between a movement between 2 cells 
	private Coord lastActivatedRoom = null;
	
	private final DungeonState dungeonState = new DungeonState();
	private  Item hoveredItem = null;
	private GroundItemHitBox hoveredGroundItem;
	private int mouseX, mouseY;
	
	public void handleExit() {
		if(!floor.allEnemiesCleared(dungeonState)) {
			IO.println("You must defat all enemies first ");
			return;
		}
		floor.setCompleted();
		if(dungeon.onLastFloor()) {
			IO.println("Game finish");
			return;
		}
		dungeon.gotNextFloor();
		floor = dungeon.getCurrentFloor();
		posHero = floor.postionHero();
		state = ZenGameState.FLOOR;
		showMiniMap = false;
	}
	
	
	public void start() {
		System.out.println("loading in progress .. ");
		ImageLoader.loadAll();
		System.out.println("loading in progress/////// .. ");
		
		var window = new Window();
		window.open(context -> {
			var screenWidth = context.getScreenInfo().width();
			var screenHeight = context.getScreenInfo().height();
			dungeon = new Dungeon(3);
			floor = dungeon.getCurrentFloor();


			Hero hero = new Hero("JOTARO KUJO");
			System.out.println("Dungeon OK");
			System.out.println("Floor = " + floor);
			System.out.println("Hero start = " + floor.postionHero());
			hero.addToBackPack(new WoodenSword());
			//var floor = new Floor(1);
			//var floor = FloorGenerator.generate();
			this.posHero = floor.postionHero();//pos initiae du hero 
			Coord target = null;
			
			var bg = new DrawBackGround();
			var backpack = new DrawBackPack();
			var itemInfoBox = new DrawItemInfo();
			
			var miniMap = new DrawMiniMap(); 
			var miniMapController = new MiniMapController(miniMap);
			var miniMapButton = new DrawMiniMapButton();
			
			var heroInDungeon = new DrawHero();
			
			var treasureRoom = new DrawTreasureRoom();
			var groundItems = new DrawGroundItem();
			var itemDescription = new DrawItemDescription();
			
			var merchanRoom = new DrawMerchantRoom();
			
			var enemiesRoom = new DrawEnemyRoom();
			
			var combatBoutons = new DrawCombatBoutons();
			var combatController = new CombatController(combatBoutons);
			//healer
			var healerController = new HealerController();
			var healerRoom = new DrawHealerRoom();
			
			var exitRoom = new DrawExitDoor();
			
		  //List<EnemyI> enn = List.of(new RatWolf(), new SmallRatWolf(), new RatWolf());//juste pour debug et test
			
			//var mouseX = -1;
			//var mouseY = -1;
			
			//boucle de jeu 
			hero.addGold(120);//juste pour test 
			while(true) {
				var event = context.pollEvent();
				switch(event) {//soint pointerEvent(souris) ou keyboardEvent(clavier) ou null rien 
				case PointerEvent p ->{
					 mouseX = p.location().x();
					 mouseY = p.location().y();
					 if(p.action() == PointerEvent.Action.POINTER_MOVE) {
						 TreasureState ts = dungeonState.treasureState(posHero);
						 if(state == ZenGameState.TREASUREROOM) {
							 if(ts.isOpened()) {
								 hoveredGroundItem = groundItems.findItemAt(mouseX, mouseY,screenWidth,screenHeight,ts.loot());
								 hoveredItem = (hoveredGroundItem != null)?hoveredGroundItem.item():null;
							 }else {
								 hoveredItem = null;
									hoveredGroundItem = null;
							 }
						 }
							
									
					 }
					if(p.action() == PointerEvent.Action.POINTER_DOWN) {//un click
						
						if(miniMapButton.isClicked(mouseX, mouseY)) {
							showMiniMap = !showMiniMap;
							break;
						}
						
						if(state == ZenGameState.TREASUREROOM) {
							TreasureState ts = dungeonState.treasureState(posHero);
							if(hoveredGroundItem !=null) {
								hoveredItem = hoveredGroundItem.item();
								break;
							}
							if(treasureRoom.isClicked(mouseX, mouseY)) {//tresure is clicked
								if(!ts.isOpened()) {
									//loot = generateTreasureLoot();
					        List<Item> loot = List.of(
					            new RoughBuckler(),
					            new MagicWand(),
					            new WoodenSword()
					        );
					        
									ts.open(loot);
									showMiniMap = false;
								}
								break;
							}
							hoveredItem = null;
							hoveredGroundItem = null;
							//break;
									
						}
						if(state == ZenGameState.HEALERROOM) {
							HealerState hs = dungeonState.healerState(posHero);
							if(!hs.isUsed()) {
								if(healerRoom.fullHealClicked(mouseX, mouseY)) {
									healerController.healFull(hero, hs);
					
								}
								else if(healerRoom.smallHealClicked(mouseX, mouseY)) {
									healerController.healSmall(hero, hs);
							
								}
								else if(healerRoom.exitClicked(mouseX, mouseY)) {
									state = ZenGameState.FLOOR;
								
								}
							}else {
								if(healerRoom.exitClicked(mouseX, mouseY)) {
									state = ZenGameState.FLOOR;
									
								}
							}
							break;
						}
						if( state == ZenGameState.EXITROOM  ) {
							if(exitRoom.isClicked(mouseX, mouseY, screenWidth, screenHeight)) {
								IO.println("Exit is clicked ");
								handleExit();
								state = ZenGameState.FLOOR;
							}
								
							break;
						}
						//si on est dans un combat 
						if( state == ZenGameState.ENEMYROOM) {
							//showMiniMap = false;
							state = combatController.manageClick(mouseX, mouseY, floor, posHero, hero, state, dungeonState);
							break;//on  bouge pas le hero et on va au prochain renderFrame 
						}
						
						//sinnon on bouge le hero sur la minimap
						target = miniMapController.convertClick(mouseX, mouseY);
						if(miniMapController.canMove(floor, posHero, target,dungeonState)) {
							var path = floor.findPath(posHero, target,dungeonState);
							if(path.size()>1) {
								path.remove(0);//remove the current postion
								movementQueue.clear();//clear the queue
								movementQueue.addAll(path);//prepare the next movements 
							}
						}
						//this.posHero = miniMapController.tryMove(floor, posHero,target);//le hero bouge , il change de salle 

						
						//on check le type de la salle Pour savoir quoi render par la suite 
						Room room = floor.getRoomInfo(posHero.row(),posHero.col());
						if(room == null) {
							break; //it's Wall so we do nothing 
						}
						/*
						RoomType type = floor.getRoomInfo(posHero.row(),	posHero.col() ).type();
						switch(type) {//un switch pour le render qui suit 
							case ENEMY -> {state = ZenGameState.ENEMYROOM;combatController.reset();}
							case TREASURE -> state = ZenGameState.TREASUREROOM;
							case MERCHANT -> state = ZenGameState.MERCHANTROOM;
							case HEALER -> state = ZenGameState.HEALERROOM;
							case EXIT -> {state = ZenGameState.EXITROOM;}
							default -> state = ZenGameState.FLOOR;//on se balade dans la map
						}*/
					}
				}
				case KeyboardEvent e ->{context.dispose(); System.exit(0);}// si on clique sur une touche on quitte le jeu
				case null ->{}
				}
		
				//animation moving hero from cell to cell
				boolean arrived  = false;
			  if(!movementQueue.isEmpty()) {
			  	moveCoolDown++;
			  	if(moveCoolDown >=MOVE_DELAY) {
			  		moveCoolDown = 0;
				  	var next = movementQueue.poll();
				  	floor.moveHero(next, dungeonState);
				  	posHero = floor.postionHero();
				  	arrived = movementQueue.isEmpty();
			  	}

			  }
			  
			  if(arrived || movementQueue.isEmpty() && state == ZenGameState.FLOOR && !posHero.equals(lastActivatedRoom)) {
			  	lastActivatedRoom = posHero;
			  	Room room = floor.getRoomInfo(posHero.row(),posHero.col());
			  	if(room!=null) {
			  		switch(room.type()) {
							case ENEMY -> {state = ZenGameState.ENEMYROOM;combatController.reset();}
							case TREASURE -> state = ZenGameState.TREASUREROOM;
							case MERCHANT -> state = ZenGameState.MERCHANTROOM;
							case HEALER -> state = ZenGameState.HEALERROOM;
							case EXIT -> {state = ZenGameState.EXITROOM;}
							default -> state = ZenGameState.FLOOR;//on se balade dans la map
			  		}
			  	}
			  }
				if(state == ZenGameState.TREASUREROOM) {
					TreasureState ts = dungeonState.treasureState(posHero);
					if(ts.isOpened()) {
						hoveredGroundItem = groundItems.findItemAt(mouseX, mouseY, screenWidth, screenHeight, ts.loot());
					}
				}
				//Ce qui sera render a chaque fois : 
				context.renderFrame(g-> { bg.render(g, screenWidth, screenHeight, floor.level());
																	
																//itemInfoBox.render(g, screenWidth, screenHeight);
													if(showMiniMap)
														miniMap.render(g, floor, this.posHero, screenWidth, screenHeight);
													else {
														backpack.render(g, hero.backPack(), screenWidth, screenHeight) ;
													}
													miniMapButton.render(g, screenWidth);
													heroInDungeon.render(g,hero, screenWidth, screenHeight);
													
													
													//heroInDungeon.renderHeroStats(g, hero, screenWidth, screenHeight);
													switch(state) {
														case FLOOR ->{}
														case MERCHANTROOM -> { /* merchanRoom.render(g, screenWidth, screenHeight);*/;}
														case TREASUREROOM -> {
																									TreasureState ts = dungeonState.treasureState(posHero);
																									treasureRoom.render(g, screenWidth, screenHeight, ts);
																									if(ts.isOpened()) {
																										groundItems.render(g, screenWidth, screenHeight, ts.loot());
																									}
																									if(hoveredItem !=null && hoveredGroundItem !=null) {
																										itemDescription.render(g, hoveredGroundItem.x(), hoveredGroundItem.y(), hoveredItem,screenWidth, screenHeight);
																										//hoveredGroundItem = null;
																									}
																									}
														case ENEMYROOM -> {showMiniMap = false;
																								var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
																								if(enemies.isEmpty()) {// pour ne pas reactiver le combat sur une salle ennemi deja traversée
																									state = ZenGameState.FLOOR;
																								}
																								enemiesRoom.render(g, enemies, screenWidth, screenHeight); 
																								combatBoutons.render(g, screenWidth, screenHeight, 7, 5);}
														case HEALERROOM -> {HealerState hs = dungeonState.healerState(posHero);
																								if(!hs.isUsed()) {
																									healerRoom.render(g, screenWidth, screenHeight);
																								}else if(hs.isUsed()){
																									healerRoom.renderUsed(g, screenWidth, screenHeight);
																								}
																								;}
														case EXITROOM -> {exitRoom.render(g, screenWidth, screenHeight);}
														
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
     List<EnemyI> ennemies = List.of(new SmallRatWolf(), new SmallRatWolf(), new SmallRatWolf());
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
