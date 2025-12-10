package game.zen.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import game.items.Item;

public class DrawTreasureRoom {
	private final BufferedImage treasure;
	
	public DrawTreasureRoom() {
		this.treasure = ImageLoader.load("/sprites/items/treasure1.png");
	}
	
	public void render(Graphics2D g, int screenWidth, int screenHeight ) {
		//position du coffre: parite dubas , au centre 
		var treasureWidth = 200;
		var treasureHeight = 200;
		var centerX = screenWidth/2;
		var centerY = (int)(screenWidth *0.75);//obligé de cast en int 
		//coin en haut a gauche du treasure
		var treasureX = centerX - treasureWidth/2;
		var treasureY = centerY - treasureHeight/2;
		
		g.drawImage(treasure, treasureX, treasureY, treasureWidth, treasureHeight, null);
		
		//afficher les items 
		
	}
}
