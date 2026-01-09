package game.controller;

import game.model.dungeon.DCoord;
import game.model.dungeon.Floor;
import game.model.dungeon.state.DungeonState;
import game.view.draw.DrawMiniMap;

/** savoir si le clic est dans la zone de la minimap
 * convertir le clic en Coor(x, y)
 * verifier si le deplacement ets autorisé 
 * retorurner la nouvelle pos du hero 
 */
public class MiniMapController {
	private final DrawMiniMap miniMap;
	
	public MiniMapController(DrawMiniMap miniMap) {
		this.miniMap = miniMap;
	}
	//convertir le click en postion valide stockée dans un Coord
	public DCoord convertClick(int mouseX, int mouseY) {
		var zoneX = miniMap.zoneX;//debut de la map 
		var zoneY = miniMap.zoneY;//debut de la map
		var zoneW = miniMap.zoneW;
		var zoneH = miniMap.zoneH;
		
		if(mouseX < zoneX || mouseX >= zoneX + zoneW) {
			return null;
		}
		if(mouseY < zoneY || mouseY >= zoneY + zoneH) {
			return null;
		}
		//transformer de pixel a coord de case 
		var col = (mouseX - zoneX) / miniMap.cellWidth;//position du click (par rapport a la  minimap) / taille de cellule = col de la cellule ou on clique 
		var row = (mouseY - zoneY) / miniMap.cellHeight;
		
		return new DCoord(row, col);
	}
	
	public boolean canMove(Floor floor, DCoord heroPos, DCoord target, DungeonState dungeonState) {
		if(target == null) {//click hors de la minimap
			return false;
		}
		if(!floor.validPosition(target) ) {//  verifie que la salle est valide 
			return false;
		}
		if(floor.isWall(target)) {
			return false;
		}
		if (!floor.canReach(heroPos, target, dungeonState)) {
	    IO.println("Pathway blocked+" +floor.getRoomInfo(target.row(), target.col()));
	    return false;
	}

		return floor.canReach(heroPos, target,dungeonState);
		//return true;
		/*
		//pour l'insant on peut se deplacer que dur une case voisine  (pas de diagonale)
		var deltaR = Math.abs(target.row() - heroPos.row());
		var deltaC = Math.abs(target.col() - heroPos.col());
		
		return deltaR + deltaC == 1;//autorise que deplacement sur une case voisine donc la difference doit etre ==  1 */
	}
	
	public DCoord tryMove(Floor floor,DCoord heroPos, DCoord target, DungeonState dungeonState) {
		
		if(canMove(floor, heroPos, target, dungeonState)) {
			return target;
		}
		return heroPos;
		// en utilisant moveHero de floor
		/*
		if (target == null) {
			return heroPos;
		}
		if(floor.moveHero(target, hero)) {
			return target;
		}
		return heroPos;
		*/
	}
	
}
