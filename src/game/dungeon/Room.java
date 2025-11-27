package game.dungeon;

import game.hero.Hero;

public interface Room {
	void enter(Hero hero);
	RoomType type();
	String description();
}
