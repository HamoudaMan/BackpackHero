package game.zen.controller;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.ennemies.Enemy;
import game.hero.Hero;
import game.zen.state.CombatPhase;
import game.zen.state.ZenGameState;
import game.zen.view.DrawCombatBoutons;

public class CombatController {
	private final DrawCombatBoutons boutons;
	private CombatPhase phase = CombatPhase.HEROTURN;
	
	public CombatController(DrawCombatBoutons boutons) {
		this.boutons = boutons;
	}
	public void reset() {
		this.phase = CombatPhase.HEROTURN;
	}
	/*
	public ZenGameState manageClick(int mouseX, int mouseY, Floor floor, Coord posHero, Hero hero, ZenGameState currentState) {
		if(currentState != ZenGameState.ENEMYROOM) {
			return currentState;//on verifie si c'est bien une enemyroom
		}
		//si click sur attack
		if(boutons.clickAttack(mouseX, mouseY)) {
			var enemyList = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
			if(!enemyList.isEmpty()) {
				EnemyI e = enemyList.get(0);
				e.takeDamage(7);
				if(e.health() <=0) {
					enemyList.remove(0);//si l'ennemi est mort en le supprime 
					if(enemyList.isEmpty()) {
						return ZenGameState.FLOOR;//si il n'y a plus d'ennemi alors combat terminer et on peut bouger le hero 
					}
				}
			}
		}
		//si click block
		if(boutons.clickBLock(mouseX, mouseY)) {
			hero.addProtection(6);
		}
		return currentState;
	}
	*/
	public ZenGameState manageClick(int mouseX, int mouseY, Floor floor, Coord posHero, Hero hero, ZenGameState currentState) {
		if(currentState != ZenGameState.ENEMYROOM) {
			return currentState;//on verifie si c'est bien une enemyroom
		}
		var enemies = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
		if(enemies.isEmpty()) {
			return currentState;
		}
		
		Enemy enemy = enemies.get(0);
		//var enemy = enemies.get(0);
		switch(phase) {//tour du hero en premier 
			case HEROTURN->{if(boutons.clickAttack(mouseX, mouseY)) {
												enemy.takeDamage(7);
											
												if(enemy.currentHealth() <=0) {
													enemies.remove(0);//si l'ennemi est mort en le supprime 
													
													if(enemies.isEmpty()) {
														phase = CombatPhase.END; // le combat est terminé
														return ZenGameState.FLOOR;//si il n'y a plus d'ennemi alors combat terminer et on peut bouger le hero 
													}
												enemy = enemies.get(0);
												}
												enemy.nextAction();
												phase = CombatPhase.ENEMYTURN;
												
										 }//si on block : 
										if(boutons.clickBLock(mouseX, mouseY)) {
											hero.addProtection(6);
											enemy.nextAction();
											phase = CombatPhase.ENEMYTURN;
										}
										return currentState;
			}
											
			case ENEMYTURN ->{enemy.doNextAction(hero);
												hero.resetProtection();
												phase = CombatPhase.HEROTURN;
												return currentState;
			}
			case END -> {return ZenGameState.FLOOR;}//combat finir on se replace hors de la salle combat
			
		}
		return currentState;
		
	}
}
