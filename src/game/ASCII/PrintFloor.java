package game.ASCII;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.dungeon.Room;

public class PrintFloor {
	public static void printF(Floor floor, Coord heroPos) {
		IO.println("======== Floor " + floor.level() + " =========");
		Room[][] listeRoom = floor.floor();// recuperer toute les room de l'etage level
		for(var r = 0; r < listeRoom.length; r++) {//5
			IO.println("+---------------------------------+");
			for(var c = 0; c < listeRoom[r].length; c++) {//11
				if(r == heroPos.row()  && c == heroPos.col()) {
					IO.print("| * ");// on place le hero sur la map 
				}else {
					IO.println("| " +symbol(listeRoom[r][c]) +" ");//appel a symbol pour afficher la premiere lettre de la room 
				}
			IO.print("|");	
			}
			IO.println("+---------------------------------+\n");	
		}
	}
	
	private static char symbol(Room room) {
		return switch(room.type()) {
		case ENEMY ->'E';
		case TREASURE ->'T';
		case MERCHANT ->'M';
		case HEALER ->'H';
		case EXIT ->'X';
		case CORRIDOR ->'.';
		//default ->'.'; // tous les cas sont couvert dans pas besoin du default (merci enum)
		};
	}
}
