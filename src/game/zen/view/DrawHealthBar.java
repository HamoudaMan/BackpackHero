package game.zen.view;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawHealthBar {
	private static final int barHeight = 12;
	private static final Color bgColor = new Color(60,60,60);
	
	public static void render(Graphics2D g, int x, int y, int width, int currHP, int maxHP) {
		//le fond de la bar
		g.setColor(bgColor);
		g.fillRect(x, y, width, barHeight);
		//hp sont full
		double div = (double)currHP/maxHP;//pour plus de precision 
		var fill = (int)(div*width);
		g.setColor(Color.GREEN);
		g.fillRect(x, y, fill, barHeight);
		//la bordure
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, barHeight);
	}
}
