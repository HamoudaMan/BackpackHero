package game.zen.view;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import game.dungeon.state.TreasureState;

import game.zen.imgLoad.ImageLoader;

public class DrawTreasureRoom {
	private final BufferedImage treasureClose;
	private final BufferedImage treasureOpen;
	
	//private boolean isOpen = false;
	private  int  treasureWidth ,treasureHeight ,treasureX ,treasureY ;
	
	public DrawTreasureRoom() {
		this.treasureClose = ImageLoader.getLoadedImage("treasure_close");
		this.treasureOpen= ImageLoader.getLoadedImage("treasure_open");
	}
	

	
	public void render(Graphics2D g, int screenWidth, int screenHeight, TreasureState state) {
		//position du coffre: parite dubas , au centre 
		treasureWidth = 200;
		treasureHeight = 150;
		
		//coin en haut a gauche du treasure
		treasureX = screenWidth/2 + treasureWidth/2;
	  treasureY = (int)(screenHeight *0.75);
		if(!state.isOpened()) {
			//g.setFont(Font.DIALOG);
			g.drawString("Open me !", treasureX+ treasureWidth/3, treasureY);
			g.drawImage(treasureClose, treasureX, treasureY, treasureWidth, treasureHeight, null);
		}else  {
			g.drawImage(treasureOpen, treasureX, treasureY, treasureWidth, treasureHeight, null);
		}
		
		
		//afficher les items 
		
	}
	public boolean isClicked(int mouseX, int mouseY) {

		return mouseX >= treasureX && mouseX <= treasureX +treasureWidth && mouseY >= treasureY && mouseY <=treasureY+ treasureHeight;
		

	}
}
