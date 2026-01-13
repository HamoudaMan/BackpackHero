package game.controller;



import java.util.ArrayDeque;
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
import game.model.item.Weapon;
import game.view.Window;

import game.view.draw.*;

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
			var combatController = new CombatController(combatBoutons,enemiesRoom );
			//healer
			var healerController = new HealerController();
			var healerRoom = new DrawHealerRoom();
			
			var exitRoom = new DrawExitDoor();
			
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
					 IO.println("Event: " + p.action() + " | State: " + state); 
					 if(state == ZenGameState.MENU && p.action() == PointerEvent.Action.POINTER_DOWN) {
						 IO.println("Menu check: isClicked = " + menu.isClicked(mouseX, mouseY));

						 if(menu.isClicked(mouseX, mouseY)) {
							 IO.println("CREATING DUNGEON...");
							 dungeon = new Dungeon(3);
							 IO.println("Dungeon created: " + dungeon);
							 floor = dungeon.getCurrentFloor();
							 IO.println("Floor retrieved: " + floor);
							 posHero = floor.postionHero();
							 IO.println("Hero position: " + posHero);
							 state = ZenGameState.FLOOR;
							 System.out.println("Dungeon OK");
				        System.out.println("Floor = " + floor);
				        System.out.println("Hero start = " + posHero);
				        //continue;
						 }
						 break;
						 
					 }
           if(state == ZenGameState.MENU) {
          	 IO.println("Still in menu, breaking");
             break;
           }
					 if(p.action() == PointerEvent.Action.POINTER_MOVE) {
						 TreasureState ts = dungeonState.treasureState(posHero);
						 if(state == ZenGameState.TREASUREROOM) {
							 System.out.println("STATE3 = " + state);

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
		    if(state == ZenGameState.MENU) {
	        context.renderFrame(g-> {
	            menu.render(g, screenWidth, screenHeight);
	        });
	        continue; // ← LA CLÉ : Sauter tout le reste de la boucle !
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
													/*if(state == ZenGameState.MENU) {
															menu.render(g, screenWidth, screenHeight);
															return;
													}*/
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
														case MENU -> {/*menu.render(g, screenWidth, screenHeight);return;*/} 
														
									}
													
				});
				
			}
			
		});
	
	}

	
	
   
}
