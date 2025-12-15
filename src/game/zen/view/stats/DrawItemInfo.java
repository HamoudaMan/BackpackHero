package game.zen.view.stats;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.zen.imgLoad.ImageLoader;

public class DrawItemInfo {
	private final BufferedImage parchemin;
	
	public DrawItemInfo() {
		this.parchemin = ImageLoader.getLoadedImage("parchemin");
	}
	
	public static void render(Graphics2D g, int screenWidth, int screenHeight) {
		var parcheminX = (int)(screenWidth/1.5);
		var parcheminY = screenHeight/12;
		var parcheminW = screenWidth/8;
		var parcheminH = screenHeight/4;
		
		g.setColor(Color.GRAY);
		g.drawRect( parcheminX, parcheminY, parcheminW, parcheminH);
		g.fillRect(parcheminX, parcheminY, parcheminW,  parcheminH);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SERIF, Font.ITALIC, 20));
		g.drawString("Item info ", parcheminX+50, parcheminY+30);
		g.setFont(null);
		//g.drawImage(parchemin, zoneX+zoneWidth, zoneY, zoneWidth/4, zoneHeight,null);
	}
}
