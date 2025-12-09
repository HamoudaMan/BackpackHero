package game.dungeon.rooms;

import java.util.Objects;
import java.util.Scanner;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.hero.Hero;

public class ExitRoom implements Room{

	@Override
	public void enter(Hero hero) {
		Objects.requireNonNull(hero);
		IO.println(description());
		IO.println("You found the exit of this floor ");
		
	}
	@Override
	public void interact(Hero hero, Scanner input) {
		//la logique de exit sera dans dungeon qui detectera le type 
		Objects.requireNonNull(hero);
		IO.println("Leaving the floor...");
		
	}

	@Override
	public RoomType type() {
		return RoomType.EXIT;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return " You reached the end of this floor GG";
	}



}
