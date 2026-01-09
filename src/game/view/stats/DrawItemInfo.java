package game.view.stats;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawItemInfo {
	private final BufferedImage parchemin;
	
	public DrawItemInfo() {
		this.parchemin = ImageLoader.getLoadedImage("parchemin");
	}
	
	public static void render(Graphics2D g, int screenWidth, int screenHeight ) {
		var infoBoxX = 300;
		var infoBoxY = 120;
		var infoBoxW = screenWidth/8;
		var infoBoxH = screenHeight/4;
		
		g.setColor(Color.BLACK);
		g.drawRect( infoBoxX, infoBoxY, infoBoxW, infoBoxH);
		g.fillRect( infoBoxX, infoBoxY, infoBoxW, infoBoxH);
		g.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
		g.drawString("Item info ", infoBoxX+50, infoBoxY+30);
		g.setFont(null);
		//g.drawImage(parchemin, zoneX+zoneWidth, zoneY, zoneWidth/4, zoneHeight,null);
		
	}
}

