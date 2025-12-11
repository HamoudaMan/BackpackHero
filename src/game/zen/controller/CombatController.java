package game.zen.controller;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.ennemies.Enemy;
import game.hero.Hero;
import game.zen.state.ZenGameState;
import game.zen.view.DrawCombatBoutons;

public class CombatController {
	private final DrawCombatBoutons boutons;
	
	public CombatController(DrawCombatBoutons boutons) {
		this.boutons = boutons;
	}
	public ZenGameState manageClick(int mouseX, int mouseY, Floor floor, Coord posHero, Hero hero, ZenGameState currentState) {
		if(currentState != ZenGameState.ENEMYROOM) {
			return currentState;//on verifie si c'est bien une enemyroom
		}
		//si click sur attack
		if(boutons.clickAttack(mouseX, mouseY)) {
			var enemyList = floor.getRoomInfo(posHero.row(), posHero.col()).enemiesList();
			if(!enemyList.isEmpty()) {
				Enemy e = enemyList.get(0);
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
}
