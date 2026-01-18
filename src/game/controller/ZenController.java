package game.controller;



import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.controller.state.ZenGameState;
import game.model.backpack.BackPack;
import game.model.dungeon.DCoord;
import game.model.dungeon.Dungeon;
import game.model.dungeon.Floor;
import game.model.dungeon.Room;
import game.model.dungeon.state.DungeonState;
import game.model.dungeon.state.HealerState;
import game.model.dungeon.state.TreasureState;
import game.model.hallOfFame.GameStats;
import game.model.hallOfFame.HallOfFame;
import game.model.hallOfFame.HallOfFameStorage;
import game.model.hallOfFame.ScoreCalculator;
import game.model.hero.Hero;
import game.model.item.Item;
import game.model.item.ItemOnScreen;
import game.model.item.Weapon;
import game.model.representation.Coord;
import game.model.representation.StateRotation;
import game.view.Window;
import game.view.draw.DrawBackGround;
import game.view.draw.DrawBackPack;
import game.view.draw.DrawCombatBoutons;
import game.view.draw.DrawEnemyRoom;
import game.view.draw.DrawExitDoor;
import game.view.draw.DrawFinishButton;
import game.view.draw.DrawGameOver;
import game.view.draw.DrawGroundItem;
import game.view.draw.DrawHealerRoom;
import game.view.draw.DrawHero;
import game.view.draw.DrawItemDescription;
import game.view.draw.DrawItemOnScreen;
import game.view.draw.DrawMenu;
import game.view.draw.DrawMerchantRoom;
import game.view.draw.DrawMiniMap;
import game.view.draw.DrawMiniMapButton;
import game.view.draw.DrawTreasureRoom;
import game.view.draw.GroundItemHitBox;
import game.view.loader.ImageLoader;
import game.view.stats.DrawItemInfo;


public class ZenController {
	private int screenWidth;
	private int screenHeight;
	private Dungeon dungeon;
	private Floor floor;
	private Hero hero ;
	private DCoord posHero;//obligé de passé posHero ici sinn j'ai des pobleme avec le swithc et le render
	private ZenGameState state = ZenGameState.MENU;// cas de base on commence dans le couloir 
	private boolean showMiniMap = false;
	
	//hero movment
	private final Queue<DCoord> movementQueue = new ArrayDeque<>();
	private int moveCoolDown = 0;
	private static final int MOVE_DELAY = 10;//frames between a movement between 2 cells 
	private DCoord lastActivatedRoom = null;
	
	private final DungeonState dungeonState = new DungeonState();
	private  Item hoveredItem = null;
	private GroundItemHitBox hoveredGroundItem;
	private ArrayList<ItemOnScreen> listItemOnScreen = new ArrayList<>();
	private int mouseX, mouseY;
	
	private ItemOnScreen draggedItem = null;//the item taht we drag 
  private int draggedItemIndex = -1;
  private int dragOffSetX = 0;// where we clcik on the item 
  private int dragOffSetY = 0;// where we clcik on the item 
	
	private final HallOfFameStorage hallOfFameStorage = new HallOfFameStorage();
	private HallOfFame  hallOfFame;
	
	private void handleExit() {
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
	
	private void handlePointerMenu(PointerEvent p, DrawMenu menu) {
		 if( p.action() == PointerEvent.Action.POINTER_DOWN && menu.isClicked(mouseX, mouseY)) {
			
			 IO.println("Menu check: isClicked = " + menu.isClicked(mouseX, mouseY));
				hero = new Hero("JOTARO KUJO");
			 IO.println("CREATING DUNGEON...");
			 dungeon = new Dungeon(100);
			 IO.println("Dungeon created: " + dungeon);
			 floor = dungeon.getCurrentFloor();				 
			 IO.println("Floor retrieved: " + floor);
			 posHero = floor.postionHero();
			 IO.println("Hero position: " + posHero);
			 dungeonState.reset();
			 movementQueue.clear();
			 lastActivatedRoom = null;
			 showMiniMap = false;
			 
			 
				 //state = ZenGameState.CHOOSEITEM;
				 state = ZenGameState.FLOOR;
				 System.out.println("Dungeon OK");
	       System.out.println("Floor = " + floor);
	       System.out.println("Hero start = " + posHero);
		 }     //continue;
			 
	}
	/**
	 * a private methode to handle the pointer event when in EnmyLoot State
	 * @param p
	 * @param groundItems
	 * @param finishBtn
	 */
	private void handlePointerEnemyLoot(PointerEvent p, DrawGroundItem groundItems, DrawFinishButton finishBtn) {
		var eState = dungeonState.enemyState(posHero);
		switch(p.action()) {
			case POINTER_MOVE ->{
				hoveredGroundItem = groundItems.findItemAt(mouseX, mouseY, screenWidth, screenHeight, eState.items());
				//IO.println("hitbox = " + hoveredGroundItem);
				 hoveredItem = (hoveredGroundItem != null)?hoveredGroundItem.item():null;
			}
			case POINTER_DOWN -> {
				if(finishBtn.isClicked(mouseX, mouseY, screenWidth, screenHeight)) {
					IO.println("Fininsh items enemy selection");
					eState.finishItemsSelection()	;
					hoveredItem = null;
					hoveredGroundItem = null;
					state = ZenGameState.FLOOR;
			}
		}
			default ->{}
		}
	}
	/**
	 * a private method to handle the pointer on GameOver 
	 * @param p pointer
	 * @param gameOverScreen 
	 */
	private void handlePointerGameOver(PointerEvent p, DrawGameOver gameOverScreen) {
		if(p.action() == PointerEvent.Action.POINTER_DOWN) {
			 if(gameOverScreen.retryIsClicked(mouseX, mouseY)) {
				 IO.println("retry is clicked ...");
				 
				 hero = new Hero("Jotaro");
        dungeon = new Dungeon(3);
        floor = dungeon.getCurrentFloor();
        posHero = floor.postionHero();
        dungeonState.reset(); 
       
        showMiniMap = false;
        movementQueue.clear();
        lastActivatedRoom = null;
        
        state = ZenGameState.FLOOR;
        return;//return so we don't go in the next if 
     
			 }
			 else if (gameOverScreen.mainMenuBtnIsClicked(mouseX, mouseY)) {
					 IO.println("main menu clicked ...");
					 hero = null;
					 dungeon = null;
					 floor = null;
					 posHero = null;
					 
					 movementQueue.clear();
					 lastActivatedRoom = null;
					 showMiniMap = false;
          
					 state = ZenGameState.MENU;
				 }
			 }
	}
	/**
	 * handler pointerEvent inside the treasurRoom
	 * @param p PointerEvent
	 * @param treasureRoom 
	 */
	private void handlePointerTreasureRoom(PointerEvent p, DrawTreasureRoom treasureRoom) {
		if(p.action() == PointerEvent.Action.POINTER_DOWN) {
			TreasureState ts = dungeonState.treasureState(posHero);
			
			//click on an item on the ground 
				if(hoveredGroundItem !=null) {
					hoveredItem = hoveredGroundItem.item();
					return;
				}
				//click on the treasure box
				if(treasureRoom.isClicked(mouseX, mouseY)) {//tresure is clicked
					if(!ts.isOpened()) {
		        List<Item> loot = List.of(
		            new Weapon("Wooden Sword", 1, 0, 7, new boolean[][] {{true, true}})
		           // new MagicWand(),
		            //new WoodenSword()
		        );
		        
						ts.open(loot);
						showMiniMap = false;
						return;
					}
				
				}
	
			}
	}
	/**
	 * Handle pointer when the player is in FLOOR state 
	 * @param p pointer event
	 * @param miniMapBtn
	 * @param miniMapController
	 */
	private void handlePointerFloor(PointerEvent p, DrawMiniMapButton miniMapBtn, MiniMapController miniMapController) {
		if(p.action() == PointerEvent.Action.POINTER_DOWN ) {
			//show minimap
			if(miniMapBtn.isClicked(mouseX, mouseY)) {
				showMiniMap = !showMiniMap;
				return;
			}
			//move using miniMap
			DCoord target = miniMapController.convertClick(mouseX, mouseY);
			if(miniMapController.canMove(floor, posHero, target,dungeonState)) {
				var path = floor.findPath(posHero, target,dungeonState);//a list of DCoord
				if(path.size()>1) {
					path.remove(0);//remove the current postion
					movementQueue.clear();//clear the queue
					movementQueue.addAll(path);//prepare the next movements 
				}
			}
			
		}
	}
	/**
	 * Handle pointer when the player is in EnemyRoom state 
	 * @param p pointer event
	 * @param combatController
	 */
	private void handlePointerEnemyRoom(PointerEvent p, CombatController combatController  ) {
		if(p.action() == PointerEvent.Action.POINTER_DOWN ) {
			state = combatController.manageCombat(mouseX, mouseY, floor, posHero, hero, state, dungeonState);
			if(hero.stats().isDead() ) {
				IO.println("switch to gameover screen");
				saveGameScore();//saing score when hero is dead 
				state = ZenGameState.GAMEOVER;
			}
		}
	}
	
	/**
	 * Handle pointer when the player is in HealerRoom state 
	 * @param p pointer event
	 * @param combatController
	 */
	private void handlePointerHealerRoom(PointerEvent p,DrawHealerRoom healerRoom, HealerController healerController  ) {
		if(p.action() == PointerEvent.Action.POINTER_DOWN ) {
			HealerState hs = dungeonState.healerState(posHero);
		//if the healer is not used : 
			if(!hs.isUsed()) {
				if(healerRoom.fullHealClicked(mouseX, mouseY)) {
					healerController.healFull(hero, hs);
					return;// we return so there's  only one action possible 
					
	
				}
				if(healerRoom.smallHealClicked(mouseX, mouseY)) {
					healerController.healSmall(hero, hs);
					return;
			
				}

			}
			if(healerRoom.exitClicked(mouseX, mouseY)) {
					state = ZenGameState.FLOOR;
					
				}
		}
	}
	
	/**
	 * Handle pointer when the player is in ExitRoom state 
	 * @param p pointerEvent
	 * @param exitRoom
	 */
	private void handlePointerExitRoom(PointerEvent p, DrawExitDoor exitRoom) {
		if(p.action() == PointerEvent.Action.POINTER_DOWN) {
			if(exitRoom.isClicked(mouseX, mouseY, screenWidth, screenHeight)) {
				IO.println("Exit is clicked ");
				handleExit();//state == FLOOR in handleExit()
			
			}
		}
	}
	/**
	 * updates the current hovered item 
	 * @param groundItems
	 */
	private void updateHoverItem(DrawGroundItem groundItems) {
		
		hoveredGroundItem = null;
		hoveredItem = null;
		switch(state) {
			case TREASUREROOM ->{
						TreasureState ts = dungeonState.treasureState(posHero);
						if(ts.isOpened()) {
						 hoveredGroundItem = groundItems.findItemAt(mouseX, mouseY,screenWidth,screenHeight,ts.loot());
						 hoveredItem = (hoveredGroundItem != null)?hoveredGroundItem.item():null;
					 }
			}
			case ENEMYLOOT -> {
					var eState = dungeonState.enemyState(posHero);
					hoveredGroundItem = groundItems.findItemAt(mouseX, mouseY, screenWidth, screenHeight, eState.items());
					//IO.println("hitbox = " + hoveredGroundItem);
					hoveredItem = (hoveredGroundItem != null)?hoveredGroundItem.item():null;
			}
			default ->{}
		}
	}
	
	/**
	 * a private methode to handle all pointer Event
	 * @param p
	 */
	private void handlePointerEvent(PointerEvent p,DrawMenu menu, DrawGameOver gameOverScreen, CombatController combatController,
														DrawGroundItem groundItems, DrawFinishButton finishButton,DrawTreasureRoom treasureRoom,
														DrawHealerRoom healerRoom, HealerController healerController, DrawExitDoor exitRoom,
														MiniMapController miniMapController, DrawMiniMapButton miniMapButton) {
		mouseX = p.location().x();
		mouseY = p.location().y();
		
		if(state == ZenGameState.MENU ) {
			 handlePointerMenu(p, menu);
			 return;
			 
		 }
           
    if(state == ZenGameState.GAMEOVER ) {
    	handlePointerGameOver(p, gameOverScreen);
      return;
		}
   				
		if( state == ZenGameState.ENEMYROOM ) {
			handlePointerEnemyRoom(p, combatController );
			return;
		}
						
 		if(state == ZenGameState.ENEMYLOOT) {
			handlePointerEnemyLoot(p,groundItems, finishButton);
			return;
		}
 		//no return beacause here we can directrly click on minimap if we want to leave
 		if(state == ZenGameState.TREASUREROOM) {
			handlePointerTreasureRoom(p, treasureRoom);
			//return;
		}
 		if(state == ZenGameState.HEALERROOM) {
 			handlePointerHealerRoom(p, healerRoom, healerController);
 			return;
 		}
    if(state == ZenGameState.EXITROOM) {
    	handlePointerExitRoom(p, exitRoom);
      return;
    }
           
		//to be able to move in the floor 
		if(state == ZenGameState.FLOOR || state == ZenGameState.TREASUREROOM) {
			handlePointerFloor(p, miniMapButton, miniMapController);
	
		}
	}
	/**
	 * a methode to save the score of the game that has been played , 
	 * will be called inside the game loop after herod death
	 */
	private void saveGameScore() {
		var stats = new GameStats(hero.stats().maxHealth(), hero.lvl(), hero.enemiesDefeated(), 0,floor.level());
		var calculator = new ScoreCalculator();
		var result = calculator.scoreCalculator(stats);
		hallOfFame.add(result);
		if(hallOfFameStorage.save(hallOfFame)) {
			IO.println("score saved :"+ result.score()+"points" ); 
		}else {
			IO.println("couln not save ");
		}
	}
	
	
	/////////RENDER METHODS
	/**
	 * a metheod to render all UI 
	 * @param g
	 * @param bg
	 * @param miniMap
	 * @param backpack
	 * @param miniMapButton
	 * @param heroInDungeon
	 */
	private void renderUI(Graphics2D g, DrawBackGround bg, DrawMiniMap miniMap,DrawBackPack backpack,DrawMiniMapButton miniMapButton,DrawHero heroInDungeon) {
		bg.render(g, screenWidth, screenHeight, floor.level());
		if(showMiniMap)
			miniMap.render(g, floor, this.posHero, screenWidth, screenHeight);
		else {
			backpack.render(g, hero.backPack(), screenWidth, screenHeight) ;
		}
		miniMapButton.render(g, screenWidth);
		heroInDungeon.render(g,hero, screenWidth, screenHeight);
	}
	

	/**
	 * a metheod to render the treasure room 
	 * @param g
	 * @param treasureRoom
	 * @param groundItems
	 * @param itemDescription
	 */
	private void renderTreasurRoom(Graphics2D g, DrawTreasureRoom treasureRoom, DrawGroundItem groundItems, DrawItemDescription itemDescription) {
		TreasureState ts = dungeonState.treasureState(posHero);
		treasureRoom.render(g, screenWidth, screenHeight, ts);
		if(ts.isOpened()) {
			groundItems.render(g, screenWidth, screenHeight, ts.loot());
		}
		if(hoveredItem !=null && hoveredGroundItem !=null) {
			itemDescription.render(g, hoveredGroundItem.x(), hoveredGroundItem.y(), hoveredItem,screenWidth, screenHeight);
		
		}
	}
	/**
	 * a methode to render the enemyRoom
	 * @param g
	 * @param enemiesRoom
	 * @param combatButtons
	 */
	private void renderEnemyRoom(Graphics2D g, DrawEnemyRoom enemiesRoom, DrawCombatBoutons combatButtons) {
		showMiniMap = false;
		var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
		if(enemies.isEmpty()) {// pour ne pas reactiver le combat sur une salle ennemi deja traversée
			state = ZenGameState.FLOOR;
		}
		enemiesRoom.render(g, enemies, screenWidth, screenHeight); 
		combatButtons.render(g, screenWidth, screenHeight, 7, 5);
    if(hero.energy().energy() == 0) {
    	
      enemiesRoom.renderLowEnergy(g); 
    }
	}
	
	private void renderHealerRoom(Graphics2D g, DrawHealerRoom healerRoom) {
		HealerState hs = dungeonState.healerState(posHero);
		if(!hs.isUsed()) {
			healerRoom.render(g, screenWidth, screenHeight);
		}else if(hs.isUsed()){
			healerRoom.renderUsed(g, screenWidth, screenHeight);
		}
		
	}
	
	/**
	 * a methode to render the EnemyLoot
	 * @param g
	 * @param enemiesRoom
	 * @param groundItems
	 * @param itemDescription
	 * @param finishButton
	 */
	private void renderEnemyLoot(Graphics2D g, DrawEnemyRoom enemiesRoom,DrawGroundItem groundItems, DrawItemDescription itemDescription, DrawFinishButton finishButton) {
		var eState = dungeonState.enemyState(posHero);
		//render the enemy room empty 
		enemiesRoom.render(g, List.of(), screenWidth, screenHeight);
		//render ground items
		groundItems.render(g, screenWidth, screenHeight, eState.items());
		if(hoveredItem !=null && hoveredGroundItem !=null) {
		itemDescription.render(g, hoveredGroundItem.x(), hoveredGroundItem.y(), hoveredItem,screenWidth, screenHeight);
		hoveredItem = (hoveredGroundItem != null)?hoveredGroundItem.item():null;
		//hoveredGroundItem = null;
	}
		finishButton.render(g, screenWidth, screenHeight);
}
	
	public void start() {
		System.out.println("loading in progress .. ");
		ImageLoader.loadAll();
		System.out.println("loading in progress ..... ");
		this.hallOfFame = hallOfFameStorage.load();
		System.out.println("Hall of fame loaded.. "+ hallOfFame.results().size());
		var window = new Window();
		window.open(context -> {
			this.screenWidth = context.getScreenInfo().width();
			this.screenHeight = context.getScreenInfo().height();

			
			DCoord target = null;
			
			var menu = new DrawMenu();
			var bg = new DrawBackGround();
			var gameOverScreen = new DrawGameOver();
			var backpack = new DrawBackPack();
			var itemInfoBox = new DrawItemInfo();
			
			var miniMap = new DrawMiniMap(); 
			var miniMapController = new MiniMapController(miniMap);
			var miniMapButton = new DrawMiniMapButton();
			
			var heroInDungeon = new DrawHero();
			
			var treasureRoom = new DrawTreasureRoom();
			var groundItems = new DrawGroundItem();
			var itemDescription = new DrawItemDescription();
			var finishButton = new DrawFinishButton();
			
			var merchanRoom = new DrawMerchantRoom();
			
			var enemiesRoom = new DrawEnemyRoom();
			
			var combatBoutons = new DrawCombatBoutons();
			var combatController = new CombatController(combatBoutons,enemiesRoom );
			//healer
			var healerController = new HealerController();
			var healerRoom = new DrawHealerRoom();
			
			var exitRoom = new DrawExitDoor();
			
			var drawItemOnScreen = new DrawItemOnScreen();
			boolean[][] shape = {{true}, {true},{true}};
      var weapon = new Weapon("Wooden Sword", 1, 0, 5, shape); 
      //var OneitemOnSreen = new ItemOnScreen(weapon, new Coord(0, 0));
     // listItemOnScreen.add(OneitemOnSreen);
      var backpackData = new BackPack(7, 5, 2, 1, 4, 3);
			

			
			this.hero = new Hero("JOTARO KUJO");
			while(true) {
				var event = context.pollEvent();
				switch(event) {//soint pointerEvent(souris) ou keyboardEvent(clavier) ou null rien 
				case PointerEvent p ->{
				//mouseX and Y are already in handlePointerEvent methode 
					 //mouseX = p.location().x();
					 //mouseY = p.location().y();
					IO.println("Event: " + p.action() + " | State: " + state); 
					 //handling all pointer events : 
					handlePointerEvent(p,menu, gameOverScreen, combatController,groundItems, finishButton,treasureRoom, healerRoom, healerController,  exitRoom, miniMapController,  miniMapButton);
					if(state == ZenGameState.MENU|| state == ZenGameState.GAMEOVER) {
						continue;//do nothing and continue to the next frame 
				
					}

					/*
					 if(state == ZenGameState.ENEMYLOOT) {
             if(p.action() == PointerEvent.Action.POINTER_DOWN) {
               var index = drawItemOnScreen.findItemAt(mouseX, mouseY, screenWidth, screenHeight, listItemOnScreen, hero.backPack(), backpack);
               if(index != -1) {
                 //found an item
                 draggedItemIndex = index;
                 draggedItem = listItemOnScreen.get(index);
                 dragOffSetX = mouseX - draggedItem.coord().x();
                 dragOffSetY = mouseY - draggedItem.coord().y();
                 //remove the itme from the list when dragging 
                 listItemOnScreen.remove(index);
                 IO.println("Start du dragging of item at index " + index);
                 
               }
               break;
             }
             
             if(p.action() == PointerEvent.Action.POINTER_UP && draggedItem != null) {
               // Drop de l'item
               int newX = mouseX - dragOffSetX;
               int newY = mouseY - dragOffSetY;
               if(newX < 0) {
                 newX = 0;
               }
               if(newY < 0) {
                 newY = 0;
               }
               //create new item with the new postion
               ItemOnScreen droppedItem = new ItemOnScreen(draggedItem.item(), new Coord(newX, newY));
               
               listItemOnScreen.add(droppedItem);
               
               var res = backpackData.CheckAndAddInBackpack(
                   draggedItem.item(),
                   StateRotation.Base,
                   droppedItem.coord(),
                   backpack.getXOffset(screenWidth),
                   backpack.getYOffset(screenHeight),
                   backpack.getZoneWidth(screenWidth),
                   backpack.getZoneHeight(screenHeight),
                   backpack.getCellWidth(backpackData, screenWidth),
                   backpack.getCellHeight(backpackData, screenHeight)
               );
               System.out.println("RES = " + res);
               System.out.println("Dropped item at (" + newX + ", " + newY + ")");
               
               // Reset  drag state
               draggedItem = null;
               draggedItemIndex = -1;
               dragOffSetX = 0;
               dragOffSetY = 0;
             }
            
           }
					 */

					

				}
				case KeyboardEvent e ->{context.dispose(); System.exit(0);}// si on clique sur une touche on quitte le jeu
				case null ->{}
				
				
				}
	
				//update game : for the EnemyTurn, the detection of changing Rooms and  the hero movment and animation 
				if( state == ZenGameState.ENEMYROOM ) {
					
					combatController.manageEnemyTurn(floor, posHero,	 hero);
					if(hero.stats().isDead()) {
						IO.println("Hero dead during ennemy turnn");
						saveGameScore();
						state = ZenGameState.GAMEOVER;
					}
					
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
							case EXIT -> state = ZenGameState.EXITROOM;
							default -> state = ZenGameState.FLOOR;//on se balade dans la map
			  		}
			  	}
			  }
			  
			  //for ENEMYLOOT and TREASUREROOM
			  updateHoverItem(groundItems);
			  
				context.renderFrame(g-> { 
													   if(state == ZenGameState.MENU) {
												        menu.render(g, screenWidth, screenHeight, hallOfFame);
												        return;
													   }
													   if(state == ZenGameState.GAMEOVER) {
												        gameOverScreen.render(g, screenWidth, screenHeight);
												        return;
												     
													    }
												         
													   //render background , backback, hero 
														renderUI(g, bg, miniMap, backpack,miniMapButton, heroInDungeon);
														switch(state) {
														case FLOOR ->{}
														/*
														case CHOOSEITEM -> {
	                            // draw all item except the on beeing dragged 
	                              drawItemOnScreen.render(g, screenWidth, screenHeight, listItemOnScreen, hero.backPack(), backpack);
	                              if(draggedItem != null) {
                                  int drawX = mouseX - dragOffSetX;
                                  int drawY = mouseY - dragOffSetY;
                                  //to not have the exception when the coord is negative 
                                  drawX = Math.max(0, drawX);
                                  drawY = Math.max(0,drawY);
                                  
                                  ItemOnScreen tempItem = new ItemOnScreen(draggedItem.item(), new Coord(drawX, drawY));
                                  drawItemOnScreen.renderOneItem(g, drawX, drawY, screenWidth, screenHeight, tempItem, hero.backPack(), backpack);
                                }
														}*/
														case MERCHANTROOM -> {  /*merchanRoom.render(g, screenWidth, screenHeight)*/;}
														case TREASUREROOM -> renderTreasurRoom(g,treasureRoom, groundItems, itemDescription) ;
														case ENEMYROOM -> renderEnemyRoom( g,enemiesRoom,  combatBoutons);
													   case ENEMYLOOT -> renderEnemyLoot( g,  enemiesRoom, groundItems,  itemDescription, finishButton) ;
														case HEALERROOM -> renderHealerRoom(g,  healerRoom);
														case EXITROOM -> {exitRoom.render(g, screenWidth, screenHeight);}
														case MENU -> {} 
														case GAMEOVER ->{}
														
									}
													
				});
				
			}
			
		});
	
	}

	
	
   
}
