package game.controller;

import game.dungeon.Coord;
import game.dungeon.Dungeon;
import game.dungeon.Floor;
import game.dungeon.Room;
import game.hero.Hero;

public class MovmentController {
	private final Hero hero;
	private final Dungeon dungeon;
	
	public MovmentController(Hero hero, Dungeon dungeon) {
		this.hero = hero;
		this.dungeon = dungeon;
	}
	
	//methode pour gerer la touche que  clic du joeur 
	public void Move(String cmd) {
		var deltaRow = 0;
		var deltaCol = 0;
		switch(cmd) {
			case "z" -> deltaRow = -1;
			case "q" -> deltaCol = -1;
			case "s" -> deltaRow = 1;
			case "d" -> deltaCol = 1;
			default -> {IO.println("Invalid command");return ;}
		}
		Floor floor = dungeon.getCurrentFloor();
		var pos = floor.postionHero();
		var dest = new Coord( pos.row() +deltaRow, pos.col() + deltaCol );
		
		if (!floor.validPosition(dest)) {//hors champs 
			IO.println("You can't move there");
			return;
		}
		if(!floor.moveHero(dest, hero)) {
			IO.println("Movment fail");
			return;
		}
		Room room = floor.getRoomInfo(dest.row(), dest.col());
		switch(room.type()){
			case EXIT ->{ floor.setCompleted();dungeon.gotNextFloor();}
			default -> {} //ne rien faire
		}
		
		IO.println("HERO BEFORE MOVE : " + floor.postionHero());
		IO.println("DEST TRY : " + dest);
		IO.println("VALID ? " + floor.validPosition(dest));

	}
}
