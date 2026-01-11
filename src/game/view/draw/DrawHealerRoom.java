package game.view.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DrawHealerRoom {
	private  int smallX, smallY, fullX,fullY, leaveX, leaveY;
	private final int buttonWidth = 220;
	private final int buttonHeight = 50;
	
	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		var centerX = screenWidth/2  -buttonWidth /2;
		var centerY = screenHeight/2;
		
		fullX = centerX;
		fullY = centerY;
		
		smallX = centerX;
		smallY = centerY+ buttonHeight +30;
		
		leaveX = centerX;
		leaveY = smallY+ buttonHeight+30;
		
		drawButton(g, fullX,fullY, "Heal Full HP for 20 gold");
		drawButton(g, smallX,smallY, "Heal small HP for 7 gold");
		drawButton(g, leaveX,leaveY, "Leave");
		
		
	}
	
	public void renderUsed(Graphics2D g, int screenWidth, int screenHeight ) {
		var centerX = screenWidth/2  -buttonWidth /2;
		var centerY = screenHeight/2;
		fullX = centerX;
		fullY = centerY;
		leaveX = centerX;
		leaveY = smallY+ buttonHeight+30;
		drawButton(g, fullX,fullY, "The healer is resting...");
		drawButton(g, leaveX,leaveY, "Leave");
		
	
		
	}
	
	private void drawButton(Graphics2D g, int x, int y, String text) {
		g.setColor(new Color(40, 40, 40,200));
		g.fillRoundRect(x, y, buttonWidth, buttonHeight,12,12);
		g.setColor(Color.WHITE);
		g.drawRoundRect( x,y , buttonWidth, buttonHeight,12,12);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.drawString(text, x+15, y+25);
		
	}
	
	
	private boolean inside(int mx, int my, int x, int y) {
		return mx>=x && mx <=x+buttonWidth && my >=y && my<=y +buttonHeight; 
	}	
	
	public boolean smallHealClicked(int mx, int my) {
		return inside(mx, my, smallX, smallY);
	}
	public boolean fullHealClicked(int mx, int my) {
		return inside(mx, my, fullX, fullY);
	}
	public boolean exitClicked(int mx, int my) {
		return inside(mx, my, leaveX, leaveY);
	}
}
