package game.dungeon.rooms;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.hero.Hero;

public class TreasureRoom implements Room{


	public void interactWithRoom(Hero hero) {
		
	}
	
	@Override
	public void enter(Hero hero) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public RoomType type() {
		// TODO Auto-generated method stub
		return RoomType.TREASURE;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Welcome to the treasure room, don't be shy stuff yourself !";
	}

}
