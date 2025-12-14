package game.zen.view.stats;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class DrawHealthBar {
	private static final int barHeight = 15;
	private static final Color bgColor = new Color(60,60,60);
	
	public static void render(Graphics2D g, int x, int y, int width, int currHP, int maxHP) {
		currHP = Math.max(0,Math.min(currHP, maxHP));
		//background of the bar 
		g.setColor(Color.darkGray);
		g.fillRect(x, y, width, barHeight);
		//hp  full
		double ratio = (double)currHP/maxHP;//double for more precision 
		var fillWidth = (int)(ratio*width);
		//if full hp color green, if half orange , if low red 
		Color hpColor;
			if (ratio>0.6) {
				 hpColor = Color.GREEN;
			}
			else if( ratio >0.3 ) {
				hpColor = Color.ORANGE;
			}else  {
				hpColor = Color.RED;
			}
		
		g.setColor(hpColor);
		g.fillRect(x, y, fillWidth, barHeight);
		//the text 
		var text = currHP + "/" +maxHP;
		g.setColor(Color.WHITE);
		
		// to center the text inside le health bar :
    FontMetrics fm = g.getFontMetrics();//to get the size 
    //to center the text 
    int textX = x + (width - fm.stringWidth(text)) / 2;//stringwitdh to get the width of the text
    int textY = y + (barHeight + fm.getAscent() - fm.getDescent()) / 2;

    g.drawString(text, textX, textY);
		//la bordure
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, barHeight);
	}
}
