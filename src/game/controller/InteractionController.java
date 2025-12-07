









//classe a supprimer

package game.controller;

import java.util.Scanner;

import game.dungeon.Dungeon;
import game.dungeon.Floor;
import game.dungeon.Room;
import game.hero.Hero;

public class InteractionController {
	private final Hero hero;
	private Dungeon dungeon;
	private final Scanner input;
	
	public InteractionController(Hero hero, Dungeon dungeon, Scanner input) {
		this.hero = hero;
		this.dungeon = dungeon;
		this.input = input;
	}
	
	public void handleInteraction() {
		Floor floor = dungeon.getCurrentFloor();
		Room room = floor.getRoomInfo(floor.postionHero().row(), floor.postionHero().col());
		room.interact(hero, input);
	}
}
