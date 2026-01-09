package game.view.stats;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class DrawEnergyBar {
	
	
	public static void renderEnergy(Graphics2D g,int  centerX, int centerY, int energy) {
	  final int rad = 10;
		final int diametre = rad*2;
		
		g.setColor(new Color(255,170,60));
		g.fillOval(centerX -rad , centerY-rad, diametre , diametre);
		//bordure
		g.setColor(Color.BLACK);
		g.drawOval(centerX -rad , centerY-rad, diametre , diametre);
		
		var text = String.valueOf(energy); //convert the int in string
		g.setColor(Color.BLACK);
		
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
