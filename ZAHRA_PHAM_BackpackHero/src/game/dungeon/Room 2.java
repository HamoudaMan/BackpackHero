package game.dungeon;

import java.util.Scanner;

import game.hero.Hero;

public interface Room {
	void enter(Hero hero);
	void interact(Hero hero, Scanner input);
	RoomType type();
	String description();
	
}
