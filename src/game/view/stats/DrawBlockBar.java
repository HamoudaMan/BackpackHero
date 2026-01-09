package game.view.stats;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawBlockBar {
	private final BufferedImage blockIcon;
	
	public DrawBlockBar() {
		this.blockIcon = ImageLoader.getLoadedImage("blockIcon");
	}

	
	public void renderBlockBar(Graphics2D g,int  centerX, int centerY, int block) {
	  final int rad = 14;
		final int diametre = rad*2;
		
		g.setColor(new Color(255,170,60));
		g.fillOval(centerX -rad , centerY-rad, diametre , diametre);
		//bordure
		g.setColor(Color.BLACK);
		//g.drawOval(centerX -rad , centerY-rad, diametre , diametre);
		g.drawImage(blockIcon, centerX -rad , centerY-rad, diametre , diametre, null);
		
		var text = String.valueOf(block); //convert the int in string
		g.setColor(Color.WHITE);
		
		/*
	// to center the text inside le health bar :
    FontMetrics fm = g.getFontMetrics();//to get the size 
    //to center the text 
    int textX = centerX + ( fm.stringWidth(text)) / 2;
    int textY = centerY + (fm.getAscent() - fm.getDescent()) / 2;
		*/
		g.drawString(text, centerX -4 , centerY +7);
	}
}
