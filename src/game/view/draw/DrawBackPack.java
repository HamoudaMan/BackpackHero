package game.view.draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.model.backpack.BackPack;
import game.view.loader.ImageLoader;



public class DrawBackPack {
	private final BufferedImage backPack;

	private static final int rows = 3;
	private static final int cols = 5;
		
	public DrawBackPack(){
		this.backPack = ImageLoader.getLoadedImage("backpack");
		
		//this.rows = 3;
		//this.cols = 5;
	}
	
	public void render(Graphics2D g, BackPack backpack, int screenWidth, int screenHeight) {
		//zone d'affichage du bakc pack
		var zoneX = screenWidth/3;
		var zoneY = screenHeight/12;
		var zoneWidth = screenWidth/3;
		var zoneHeight = screenHeight/3;
		
		g.drawImage(backPack, zoneX, zoneY, zoneWidth, zoneHeight, null);//afficher l'image 
		
		//calcul des cases de la grille;
		var cellWidth = zoneWidth/cols;
		var cellHeight = zoneHeight/rows;
		//dessin de la grile row*col
		for(var r = 0; r<rows; r++) {
			for(var c=0; c<cols; c++) {
				//calcul des postions x et y de chaque cell(a voir pour cree une separation 
				var x = zoneX + c*cellWidth;
				var y = zoneY + r*cellHeight;
				
			//	if(backPack.stuff().)
				
				g.drawRect(x, y, cellWidth , cellHeight);
			}
		}
		
		
		
		//aficher les items 
		
		
	}

}
