package game.controller;

import game.controller.state.CombatPhase;
import game.controller.state.ZenGameState;
import game.model.dungeon.DCoord;
import game.model.dungeon.Floor;
import game.model.dungeon.state.DungeonState;
import game.model.enemy.Enemy;
import game.model.hero.Hero;

import game.view.draw.DrawCombatBoutons;
import game.view.draw.DrawEnemyRoom;

public class CombatController {
	private final DrawCombatBoutons boutons;
	private final DrawEnemyRoom drawEnemyRoom;
	private CombatPhase phase = CombatPhase.HEROTURN;
	private boolean enemyTurnExecuted = false;
	private Enemy selectedEnemy = null;
	
	public CombatController(DrawCombatBoutons boutons, DrawEnemyRoom drawEnemyRoom) {
		this.boutons = boutons;
		this.drawEnemyRoom = drawEnemyRoom;
	}
	public void reset() {
		this.phase = CombatPhase.HEROTURN;
		this.enemyTurnExecuted = false;
	}

	
	public ZenGameState manageCombat(int mouseX, int mouseY, Floor floor, DCoord posHero, Hero hero, ZenGameState currentState, DungeonState state) {
		if(currentState != ZenGameState.ENEMYROOM) {
			return currentState;//on verifie si c'est bien une enemyroom
		}
		
		var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
		if(enemies.isEmpty()) {
			return ZenGameState.FLOOR;
		}
		
		Enemy enemy = enemies.get(0);
	
		

		//hero always starts first 
		if(phase == CombatPhase.HEROTURN) {
			
			Enemy clickedEnemy = drawEnemyRoom.enemyIsClicked(mouseX, mouseY, enemies);
			if(clickedEnemy != null) {
				selectedEnemy = clickedEnemy ;
				drawEnemyRoom.setSelectedEnemy(selectedEnemy);
				IO.println("selected Enemy from controller" + selectedEnemy.type());
				return currentState;
				
			}
			if(boutons.clickAttack(mouseX, mouseY) && hero.energy().energy() >0 ) {
				Enemy target = selectedEnemy;
				if( selectedEnemy == null) {
					target = enemies.get(0);
					selectedEnemy = target;
					drawEnemyRoom.setSelectedEnemy(target);
					IO.println("no target selecting the first enn : "+ target.type());
				}
				if(!enemies.contains(target)) {
					target = enemies.get(0);
					selectedEnemy = target;
					drawEnemyRoom.setSelectedEnemy(target);
					IO.println("switcch target the last one dead  : "+ target.type());
				}
				hero.energy().consumeEnergy(1);//consume one enrgy
				target.takeDamage(7);
				IO.println("Hero attacks for " + 7 + " damage! Energy left: " + hero.energy().energy());
				
				if(target.isDead()) {
					IO.println("ENemy defeated");
					
					//increase xp of the hero 
					int xpGain = target.stats().exp();
					hero.addXp(xpGain);
					hero.incrementEnemiesDefeated();
					
					enemies.remove(target);//si l'ennemi est mort en le supprime 
					selectedEnemy = null;
					drawEnemyRoom.setSelectedEnemy(null);
						
					if(enemies.isEmpty()) {
						IO.println("all enemies defeated");
						state.enemyState(posHero).clear();
						hero.energy().resetEnergy();
						phase = CombatPhase.END; // le combat est terminé
						return ZenGameState.FLOOR;
					}
				}
					//IO.println("Not enough energy, Click End Turn to pass.");
				
				return currentState;
			}
			//block
			
			if(boutons.clickBLock(mouseX, mouseY)  &&  hero.energy().energy() >0) {
				System.out.println("Hero prtoection before buff  = " + hero.stats().protection());

				hero.stats().addProtection(6);
				System.out.println("Hero prtoection after buff  = " + hero.stats().protection());
				hero.energy().consumeEnergy(1);//consume 1 energy 
				IO.println("Hero blocks! Protection: " + hero.stats().protection() + 
            " Energy left: " + hero.energy().energy());
				//enemy.nextAction();
				return currentState;
			}
					

			/*
			if(!heroAct) {
				return currentState;
			}*/
			if(boutons.clickEndTurn(mouseX, mouseY)){
				IO.println("Hero ends turn. Enemy turn begins!");
        IO.println("Hero stats before enemy turn -> HP=" + hero.stats().health() + 
                  " Protection=" + hero.stats().protection() + 
                  " Energy=" + hero.energy().energy());
				phase = CombatPhase.ENEMYTURN;
				enemyTurnExecuted = false;
				return currentState;
			}
			return currentState;
		}
		
		//enemy turn wxill be in update() 

		if(phase == CombatPhase.ENEMYTURN) {
			enemy.playTurn(hero);
			hero.stats().resetProtection();
			hero.energy().resetEnergy();
			phase = CombatPhase.HEROTURN;
			return currentState;
		}
		
		if(phase == CombatPhase.END) {
			return ZenGameState.FLOOR;
		}
		return currentState;
	}
	
  public ZenGameState manageEnemyTurn(Floor floor, DCoord posHero, Hero hero) {
    //enemy plays 
    if(phase == CombatPhase.ENEMYTURN && !enemyTurnExecuted) {
        var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
        
        if(!enemies.isEmpty()) {
            IO.println("ENEMY TURN");
            //var enemyCount = enemies.size();
            // all enemies do their action 
            for(int i = 0; i < enemies.size(); i++) {
            		if( i>= enemies.size()) {
            			break;
            		}
                Enemy enemy = enemies.get(i);
                IO.println("Enemy " + (i+1) + " attacks");
                enemy.playTurn(hero);
            }
            
            IO.println("Hero stats after enemy turn -> HP=" + hero.stats().health() + 
                      " Protection=" + hero.stats().protection());
            
          
            if(hero.stats().isDead()) {
                IO.println("Hero defeated! Game Over!");
                // Ici vous pourriez gérer le game over
                phase = CombatPhase.END;
                enemyTurnExecuted = true;
                return ZenGameState.GAMEOVER;
            }
            
            // enemy playes his turn so true 
            enemyTurnExecuted = true;
            
            //prepare next turn 
            hero.stats().resetProtection(); // La protection est consommée
            hero.energy().resetEnergy();     // Le héros récupère ses 3 points d'énergie
            
            IO.println("NEW HERO TURN ");
            IO.println("Hero ready -> HP=" + hero.stats().health() + 
                      " Protection=" + hero.stats().protection() + 
                      " Energy=" + hero.energy().energy());
            
            //back to hero turn 
            phase = CombatPhase.HEROTURN;
        }
    }
    return ZenGameState.ENEMYROOM;
}

public CombatPhase getPhase() {
    return phase;
}
	
}
