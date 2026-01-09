package game.controller;

import game.controller.state.CombatPhase;
import game.controller.state.ZenGameState;
import game.model.dungeon.DCoord;
import game.model.dungeon.Floor;
import game.model.dungeon.state.DungeonState;
import game.model.enemy.Enemy;
import game.model.hero.Hero;
import game.model.representation.Coord;
import game.view.draw.DrawCombatBoutons;

public class CombatController {
	private final DrawCombatBoutons boutons;
	private CombatPhase phase = CombatPhase.HEROTURN;
	
	public CombatController(DrawCombatBoutons boutons) {
		this.boutons = boutons;
	}
	public void reset() {
		this.phase = CombatPhase.HEROTURN;
	}

	public ZenGameState manageClick(int mouseX, int mouseY, Floor floor, DCoord posHero, Hero hero, ZenGameState currentState, DungeonState state) {
		if(currentState != ZenGameState.ENEMYROOM) {
			return currentState;//on verifie si c'est bien une enemyroom
		}
		
		var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
		if(enemies.isEmpty()) {
			return currentState;
		}
		
		Enemy enemy = enemies.get(0);
		switch(phase) {
		//hero always starts first 
			case HEROTURN->{if(boutons.clickAttack(mouseX, mouseY)) {
												hero.energy().consumeEnergy(1);//consume one enrgy
												enemy.takeDamage(7);
												//hero.energy().resetEnergy();
												if(enemy.isDead()) {
													enemies.remove(0);//si l'ennemi est mort en le supprime 
													
													if(enemies.isEmpty()) {
														phase = CombatPhase.END; // le combat est terminé
														state.enemyState(posHero).clear();
														return ZenGameState.FLOOR;//si il n'y a plus d'ennemi alors combat terminer et on peut bouger le hero
														
													}
												enemy = enemies.get(0);
												}
												//enemy.nextAction();
												phase = CombatPhase.ENEMYTURN;
												
										 }//si on block : 
										if(boutons.clickBLock(mouseX, mouseY)) {
											hero.stats().addProtection(6);
											hero.energy().consumeEnergy(1);//consume 1 energy 
											//enemy.nextAction();
											phase = CombatPhase.ENEMYTURN;
										}
										
										return currentState;
			}
											
			case ENEMYTURN ->{//enemy.doNextAction(hero);
												enemy.playTurn(hero);
												hero.energy().resetEnergy();
												
												phase = CombatPhase.HEROTURN;
												return currentState;
			}
			case END -> {return ZenGameState.FLOOR;}//combat is done 
			
		}
		return currentState;
		
	}
}
