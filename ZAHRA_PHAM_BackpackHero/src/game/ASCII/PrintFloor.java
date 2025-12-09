package game.ASCII;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.dungeon.Room;

public class PrintFloor {
	public static void printF(Floor floor, Coord heroPos) {
		IO.println("\t============ Floor " + floor.level() + " =============");
		
		Room[][] listeRoom = floor.floor();// recuperer toute les room de l'etage level
		IO.println("+------------------------------------------------------+");
		
		for(var r = 0; r < listeRoom.length; r++) {//5
			StringBuilder sbRow = new StringBuilder();
			
			for(var c = 0; c < listeRoom[r].length; c++) {//11
				if(r == heroPos.row()  && c == heroPos.col()) {
					sbRow.append("| * ");// on place le hero sur la map 
				}else {
					sbRow.append("| " +symbol(listeRoom[r][c]) +" ");//appel a symbol pour afficher la premiere lettre de la room 
				}
				sbRow.append("|");	
			}
			IO.println(sbRow.toString());
			
		}
		IO.println("+------------------------------------------------------+\n");	
		IO.println();
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
