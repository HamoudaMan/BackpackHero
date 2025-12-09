package game.zen.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.dungeon.Room;
import game.dungeon.RoomType;

public class DrawMiniMap {
	private final BufferedImage tile;
	private final BufferedImage hero;
	
	public DrawMiniMap() {
		this.tile = ImageLoader.load("/sprites/ui/dunjon/tile.png");
		this.hero = ImageLoader.load("/sprites/ui/dunjon/hero.png");
	}
	
	public void render(Graphics2D g, Floor floor, Coord positionHero, int screenWidth, int screenHeight) {
		//zone d'affichage de la minimap
		var zoneW = screenWidth/4; //largeur de la map
		var zoneH = screenHeight/4; //longuer de la map
		var zoneX = screenWidth - zoneW - 40;//valeur arbitraire pour la marge a gauche 
		var zoneY = 40;//marge en haut 
		
		Room[][] rooms = floor.floor();
		var rows = rooms.length;
		var cols = rooms[0].length;
		var cellWidth = zoneW/cols;
		var cellHeight = zoneH/rows;
		
		//dessin de la grille de la minimap
		for(var r = 0; r<rows; r++) {
			for(var c = 0; c<cols; c++) {
				var x = zoneX + c*cellWidth;
				var y = zoneY + r*cellHeight;
				
				g.drawImage(tile,x, y, cellWidth, cellHeight, null);
				//une couleur pour chaque type de room (par la suite on met une image a a la place de la couleur ?)
				RoomType type = rooms[r][c].type();
				switch(type) {
				case ENEMY -> g.setColor(Color.RED);
				case TREASURE -> g.setColor(Color.YELLOW);
				case MERCHANT -> g.setColor(Color.LIGHT_GRAY);
				case HEALER ->g.setColor(Color.GREEN);
				case EXIT -> g.setColor(Color.MAGENTA);
				default ->g.setColor(null);
				}
				/*
				//on applique la couleur 
				if(g.getColor() !=null) {
					g.fillRect(x, y, cellWidth, cellHeight);
				}
				*/
				
			}
			
		}
	// draw where the hero is:
    var colHero = zoneX +positionHero.col()*cellWidth; // 0 à COLS-1
    var rowHero = zoneY + positionHero.row()*cellHeight; // 0 à ROWS-1

    //g.setColor(Color.CYAN);
    //g.fillOval(rowHero + cellWidth/4, colHero + cellHeight/4, cellWidth/4, cellHeight/4);//pas sur des calcul a revoir 
    g.drawImage(hero,colHero, rowHero, cellWidth, cellHeight, null);
		
	
	}
	
}
