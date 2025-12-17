package game.zen.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.items.Item;
import game.zen.imgLoad.ImageLoader;

public class DrawItemDescription {
	private final BufferedImage parchemin ;
	
	public DrawItemDescription() {
		this.parchemin =  ImageLoader.getLoadedImage("itemparchemin");
	}
	
	public void render(Graphics2D g, int itemX, int itemY, Item item, int screenWidth, int screenHeight ) {
		var height = 220;
		var width = 130;
		var x = itemX +20;
		var y = itemY+20;
		
		//to stay inside the window 
		if(x+width > screenWidth) {
			x = itemX-width-20;
		}
		if(y+height > screenHeight) {
			y = itemX-height-20;
		}
		g.drawImage(parchemin, x, y, width, height, null);
		g.setColor(new Color(60, 40, 18));
		g.setFont(new Font("Serif", Font.BOLD, 15));
		g.drawString(item.name(), x+15, y+15);
		g.setFont(new Font("Serif", Font.PLAIN, 15));
		var textY = y +45;
		
		for(String s: item.description().split("\n")) {
			g.drawString(s, x, textY);
			textY +=15;
		}
		
	}

}
