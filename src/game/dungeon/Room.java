package game.dungeon;

import java.util.Scanner;

import game.hero.Hero;

public interface Room {
	void enter(Hero hero);
	RoomType type();
	String description();
	void interact(Hero hero, Scanner input);
}
