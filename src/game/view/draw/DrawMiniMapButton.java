package game.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawMiniMapButton {
	private int buttonX, buttonY, buttonW, buttonH;
	
	public void render(Graphics2D g, int screenWidth) {
		buttonW = 40;
		buttonH = 40;
		buttonX = screenWidth -buttonW;
		buttonY = 20;
		g.setColor(Color.BLUE);
		g.fillRect(buttonX, buttonY, buttonW, buttonY);
		
		
	}
	public boolean isClicked(int mouseX, int mouseY) {
		return mouseX >= buttonX && mouseX <= buttonX +buttonW && 
					mouseY >= buttonY && mouseY <=buttonY+ buttonH;
	}
}
