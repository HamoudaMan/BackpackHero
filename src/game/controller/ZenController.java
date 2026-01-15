package game.controller;



import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.github.forax.zen.KeyboardEvent;
import com.github.forax.zen.PointerEvent;

import game.controller.state.ZenGameState;
import game.model.dungeon.DCoord;
import game.model.dungeon.Dungeon;
import game.model.dungeon.Floor;
import game.model.dungeon.Room;
import game.model.dungeon.state.DungeonState;
import game.model.dungeon.state.HealerState;
import game.model.dungeon.state.TreasureState;
import game.model.hero.Hero;
import game.model.item.Item;
import game.model.item.ItemOnScreen;
import game.model.item.Weapon;
import game.model.representation.Coord;
import game.view.Window;
import game.view.draw.DrawBackGround;
import game.view.draw.DrawBackPack;
import game.view.draw.DrawCombatBoutons;
import game.view.draw.DrawEnemyRoom;
import game.view.draw.DrawExitDoor;
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
	private Dungeon dungeon;
	private Floor floor;
	private DCoord posHero;//obligé de passé posHero ici sinn j'ai des pobleme avec le swithc et le render
	private ZenGameState state = ZenGameState.MENU;// cas de base on commence dans le couloir 
	private boolean showMiniMap = false;
		
	//hero movment
	private final Queue<DCoord> movementQueue = new ArrayDeque<>();
	private int moveCoolDown = 0;
	private static final int MOVE_DELAY = 10;//frames between a movement between 2 cells 
	private DCoord lastActivatedRoom = null;
	
	private final DungeonState dungeonState = new DungeonState();
	
	private Item hoveredItem = null;
	private GroundItemHitBox hoveredGroundItem;
	private ArrayList<ItemOnScreen> listItemOnScreen = new ArrayList<>();
	private int mouseX, mouseY;
	
	//DRAG AND DROP STATE //////////////////////////
	private ItemOnScreen draggedItem = null;//the item taht we drag 
	private int draggedItemIndex = -1;
	private int dragOffSetX = 0;// where we clcik on the item 
	private int dragOffSetY = 0;// where we clcik on the item 
	///////////////////
	///
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
			
			//floor = dungeon.getCurrentFloor();


			Hero hero = new Hero("JOTARO KUJO");
			//System.out.println("Dungeon OK");
			//System.out.println("Floor = " + floor);
			//System.out.println("Hero start = " + floor.postionHero());
			//hero.addToBackPack(new WoodenSword());
			//var floor = new Floor(1);
			//var floor = FloorGenerator.generate();
			//this.posHero = floor.postionHero();//pos initiae du hero
			DCoord target = null;
			
			var menu = new DrawMenu();
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
			// ------------------------------------------
			var drawItemOnScreen = new DrawItemOnScreen();
			boolean[][] shape = {{true}, {true},{true}};
      var weapon = new Weapon("Wooden Sword", 1, 0, 5, shape); 
      var OneitemOnSreen = new ItemOnScreen(weapon, new Coord(0, 0));
      listItemOnScreen.add(OneitemOnSreen);
     // ItemOnScreen dragItem = null;
      //var index = -1;
      // ------------------------------------------
			
		  //List<EnemyI> enn = List.of(new RatWolf(), new SmallRatWolf(), new RatWolf());//juste pour debug et test
			
			//var mouseX = -1;
			//var mouseY = -1;
			
			//boucle de jeu 
			//hero.addGold(120);//juste pour test 
			while(true) {
				var event = context.pollEvent();
				switch(event) {//soint pointerEvent(souris) ou keyboardEvent(clavier) ou null rien 
				case PointerEvent p ->{
					 mouseX = p.location().x();
					 mouseY = p.location().y();
					 
					 if(state == ZenGameState.MENU) {
						 if(menu.isClicked(mouseX, mouseY)) {
							 dungeon = new Dungeon(3);
							 floor = dungeon.getCurrentFloor();
							 posHero = floor.postionHero();
							 state = ZenGameState.CHOOSEITEM;
							 System.out.println("Dungeon OK");
							 System.out.println("Floor = " + floor);
							 System.out.println("Hero start = " + posHero);
						 }
						 break;
					 }
					 //////////////////////////
					 if(state == ZenGameState.CHOOSEITEM) {
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
						
							 //create new item with the new postion 
							 ItemOnScreen droppedItem = new ItemOnScreen(draggedItem.item(), new Coord(newX, newY));
							 
							 listItemOnScreen.add(droppedItem);
							 
							 System.out.println("Dropped item at (" + newX + ", " + newY + ")");
							 
							 // Reset  drag state
							 draggedItem = null;
							 draggedItemIndex = -1;
							 dragOffSetX = 0;
							 dragOffSetY = 0;
							 break;
						 }
						 
						 break;
					 	
					 }
					 ////////////////////////
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
					            new Weapon("Wooden Sword", 1, 0, 7, new boolean[][] {{true, true}})
					           // new MagicWand(),
					            //new WoodenSword()
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
						if( state == ZenGameState.ENEMYROOM ) {
							//showMiniMap = false;
							state = combatController.manageCombat(mouseX, mouseY, floor, posHero, hero, state, dungeonState);
							//combatController.manageEnemyTurn(floor, posHero,	 hero);
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

					}
				}
				case KeyboardEvent e ->{context.dispose(); System.exit(0);}// si on clique sur une touche on quitte le jeu
				case null ->{}
				}
				if( state == ZenGameState.ENEMYROOM ) {
					//showMiniMap = false;
					//state = combatController.manageCombat(mouseX, mouseY, floor, posHero, hero, state, dungeonState);
					combatController.manageEnemyTurn(floor, posHero,	 hero);
					//break;//on  bouge pas le hero et on va au prochain renderFrame 
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
				context.renderFrame(g-> { 
													if(state == ZenGameState.MENU) {
															menu.render(g, screenWidth, screenHeight);
															return;
													}
													bg.render(g, screenWidth, screenHeight, floor.level());
																	
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
														case CHOOSEITEM -> {
														// draw all item except the on beeing dragged 
															drawItemOnScreen.render(g, screenWidth, screenHeight, listItemOnScreen, hero.backPack(), backpack);
															
																							//if we dragging drawx the item beeing dragged 
																								if(draggedItem != null) {
																									int drawX = mouseX - dragOffSetX;
																									int drawY = mouseY - dragOffSetY;
																									 //to not have the exception when the coord is negative 
																									 drawX = Math.max(0, drawX);
																									 drawY = Math.max(0,drawY);
																									ItemOnScreen tempItem = new ItemOnScreen(draggedItem.item(), new Coord(drawX, drawY));
																									drawItemOnScreen.renderOneItem(g, drawX, drawY, screenWidth, screenHeight, tempItem, hero.backPack(), backpack);
																								}
														
														                    }
														case TREASUREROOM -> {  TreasureState ts = dungeonState.treasureState(posHero);
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
														case MENU -> {menu.render(g, screenWidth, screenHeight);return;} 
														
									}
													
				});
				/*
				if(state == ZenGameState.CHOOSEITEM) {
				  while(true) {
				    System.out.println(listItemOnScreen);
				    var eventChooseItem = context.pollEvent();
				    switch(eventChooseItem) {//soint pointerEvent(souris) ou keyboardEvent(clavier) ou null rien 
              case PointerEvent p ->{
                mouseX = p.location().x();
                mouseY = p.location().y();
                if(p.action() == PointerEvent.Action.POINTER_DOWN) {
                  index = drawItemOnScreen.findItemAt(mouseX, mouseY, screenWidth, screenHeight, listItemOnScreen, hero.backPack(), backpack);
                  if(index != -1) {
                    dragItem = listItemOnScreen.get(index);
                  }
                }
                if(p.action() == PointerEvent.Action.POINTER_MOVE && dragItem != null && index != -1) {
                  var test =  listItemOnScreen.get(index);
                  context.renderFrame(g-> drawItemOnScreen.renderOneItem(g, mouseX, mouseY, screenWidth, screenHeight, test , hero.backPack(), backpack));
    //             System.out.println(mouseX + " " + mouseY);
    //             continue;
                 
                 }
                if(p.action() == PointerEvent.Action.POINTER_UP && index != -1 && dragItem != null) {
                  listItemOnScreen.set(0, new ItemOnScreen(listItemOnScreen.get(0), new Coord(mouseX, mouseY)));
                  index = -1;
                  dragItem = null;
                }
              }
              case null -> {}
              case KeyboardEvent e ->{context.dispose(); System.exit(0);}
//            default -> throw new IllegalArgumentException("Unexpected value: " + eventChooseItem);
				    }
				  }
				}
				*/
			}
		});
	}
}
