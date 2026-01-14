package game.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import game.model.backpack.BackPack;
import game.view.loader.ImageLoader;



public class DrawBackPack {
	private final BufferedImage backPack;
	
	
		
	public DrawBackPack(){
	  this.backPack = ImageLoader.getLoadedImage("backpack");
	}
	
	private int calculateZoneX(int screenWidth) {
	  return screenWidth/4;
	}
	
	private int calculateZoneY(int screenHeight) {
    return screenHeight/12;
  }
	
	private int calculateZoneWidth(int screenWidth) {
	  return screenWidth * 2/4;
	}
	
	private int calculateZoneHeight(int screenHeight) {
	  return screenHeight/3;
	}
	
	private int calculateCellWidth(BackPack backpack, int screenWidth) {
	  Objects.requireNonNull(backpack);
	  return calculateZoneWidth(screenWidth)/backpack.getMaxX();
	}
	private int calculateCellHeight(BackPack backpack, int screenHeight) {
	  Objects.requireNonNull(backpack);
    return calculateZoneHeight(screenHeight)/backpack.getMaxY();
  }
	
	public int getXOffset(int screenWidth) {
	  return calculateZoneX(screenWidth);
	}
	
	public int getYOffset(BackPack backpack, int screenHeight) {
	  Objects.requireNonNull(backpack);
    return calculateZoneY(screenHeight);
  }
	
	public int getCellWidth(BackPack backpack, int screenWidth) {
	  Objects.requireNonNull(backpack);
	  return calculateCellWidth(backpack, screenWidth);
	}
	
	public int getCellHeight(BackPack backpack,int screenHeight) {
	  Objects.requireNonNull(backpack);
    return calculateCellHeight(backpack, screenHeight);
  }
	
	public void render(Graphics2D g, BackPack backpack, int screenWidth, int screenHeight) {
	  Objects.requireNonNull(backpack);
		//zone d'affichage du back pack
		
	  var zoneX = calculateZoneX(screenWidth);
		var zoneY = calculateZoneY(screenHeight);
		var zoneWidth = calculateZoneWidth(screenWidth);
		var zoneHeight = calculateZoneHeight(screenHeight);
		
		g.drawImage(backPack, zoneX, zoneY, zoneWidth, zoneHeight, null);//afficher l'image 
		
		//calcul des cases de la grille;
		var cellWidth =  calculateCellWidth(backpack, screenWidth);
		var cellHeight = calculateCellHeight(backpack, screenHeight);
		
		var unlocked = backpack.getUnlocked();
		
		//dessin de la grile row*col
		for(var row = 0; row < backpack.getMaxY(); row++) {
			for(var col = 0; col < backpack.getMaxX(); col++) {
				var x = zoneX + col * cellWidth;
				var y = zoneY + row * cellHeight;
				g.setColor(Color.BLACK);
				g.drawRect(x, y, cellWidth , cellHeight);
				if(!unlocked[row][col]) {
				  g.setColor(new Color(128,128,128, 120));
	        g.fillRect(x, y, cellWidth , cellHeight);
				}
			}
		}	
		//aficher les items 
	}
}
