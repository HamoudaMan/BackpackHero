package game.zen.view;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import game.items.Item;
import game.zen.imgLoad.ImageLoader;

public class DrawTreasureRoom {
	private final BufferedImage treasureClose;
	private final BufferedImage treasureOpen;
	
	private boolean isOpen = false;
	private  int  treasureWidth ,treasureHeight ,treasureX ,treasureY ;
	
	public DrawTreasureRoom() {
		this.treasureClose = ImageLoader.getLoadedImage("treasure_close");
		this.treasureOpen= ImageLoader.getLoadedImage("treasure_open");
	}
	

	
	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		//position du coffre: parite dubas , au centre 
		treasureWidth = 200;
		treasureHeight = 150;
		
		//coin en haut a gauche du treasure
		treasureX = screenWidth/2 + treasureWidth/2;
	  treasureY = (int)(screenHeight *0.75);
		if(isOpen ==false) {
			//g.setFont(Font.DIALOG);
			g.drawString("Open me !", treasureX+ treasureWidth/3, treasureY);
			g.drawImage(treasureClose, treasureX, treasureY, treasureWidth, treasureHeight, null);
		}else if(isOpen== true) {
			g.drawImage(treasureOpen, treasureX, treasureY, treasureWidth, treasureHeight, null);
		}
		
		
		//afficher les items 
		
	}
	public void onClick(int mouseX, int mouseY) {
		if(isOpen) {
			return;
		}
		boolean inside =  mouseX >= treasureX && mouseX <= treasureX +treasureWidth && mouseY >= treasureY && mouseY <=treasureY+ treasureHeight;
		
		if(inside) {
			isOpen = true;
		}
	}
}
