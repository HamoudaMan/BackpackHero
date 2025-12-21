package game.zen.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.dungeon.Coord;
import game.dungeon.Floor;
import game.dungeon.Room;
import game.dungeon.RoomType;
import game.zen.imgLoad.ImageLoader;

public class DrawMiniMap {
	private final BufferedImage tile;
	private final BufferedImage hero;
	public int zoneX, zoneY,zoneW, zoneH, cellWidth, cellHeight;//seront utilisé dans MiniMapController
	
	public DrawMiniMap() {
		this.tile = ImageLoader.getLoadedImage("tile");
		this.hero = ImageLoader.getLoadedImage("hero");
	}
	
	public void render(Graphics2D g, Floor floor, Coord positionHero, int screenWidth, int screenHeight) {
		//zone d'affichage de la minimap
		zoneW = screenWidth/3; //largeur de la map
		zoneH = screenHeight/3; //longuer de la map
		zoneX = screenWidth /3;//40 : valeur arbitraire pour la marge a gauche 
		zoneY = screenHeight/12;//marge en haut 
		
		/*		var zoneX = screenWidth/3;
		var zoneY = 
		var zoneW = screenWidth/3;
		var zoneH = screenHeight/3;*/
		
		Room[][] rooms = floor.rooms();
		var rows = rooms.length;
		var cols = rooms[0].length;
		 cellWidth = zoneW/cols;
		cellHeight = zoneH/rows;
		
		//dessin de la grille de la minimap
		for(var r = 0; r<rows; r++) {
			for(var c = 0; c<cols; c++) {
				var x = zoneX + c*cellWidth;
				var y = zoneY + r*cellHeight;
				
				g.drawImage(tile,x, y, cellWidth, cellHeight, null);
				//une couleur pour chaque type de room (par la suite on met une image a a la place de la couleur ?)
				
				if(floor.isBlocked(r, c)) {
					g.setColor(Color.BLACK);
					g.fillRect(x, y, cellWidth, cellHeight);
					g.setColor(Color.BLACK);
					g.drawRect(x, y, cellWidth, cellHeight);
					continue;//if blocked no room drawing 
				}
				RoomType type = rooms[r][c].type();
				switch(type) {
				case ENEMY -> g.setColor(new Color(255, 0, 0, 120));
				case TREASURE -> g.setColor(new Color(255, 255, 0, 120));
				case MERCHANT -> g.setColor(Color.LIGHT_GRAY);
				case HEALER ->g.setColor(new Color(0, 255,0, 100));
				case EXIT -> g.setColor(Color.MAGENTA);
				default ->g.setColor(new Color(255, 255, 255, 40));//obligé de mettre les valeur rgba (a pour l'opacité) pour plus de flexibilité sur les couleur 
				}
				g.fillRect(x, y, cellWidth, cellHeight);
				//pour le contour des salles sinn j'ai desbandes continue (comme a l'ancien commit )
				g.setColor(Color.BLACK);
				g.drawRect(x, y, cellWidth, cellHeight);
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
