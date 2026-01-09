package game.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawExitDoor {
	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		var doorW = 100;
		var doorH = 175;
		var x = screenWidth/2 -doorW/2 ;
		var y = screenHeight/2 -doorW/2;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, doorW, doorH);
		g.setColor(Color.WHITE);
		g.drawString("click here to go to the next floor", x+5, y+20);
		
	}
	public boolean isClicked(int mouseX, int mouseY, int screenWidth, int screenHeight ) {
		var doorW = 100;
		var doorH = 175;
		var x = screenWidth/2 -doorW/2;
		var y = screenHeight/2 -doorW/2;
		
		return mouseX >= x && mouseX <= doorW+x && mouseY>= y && mouseY <= y + doorH;
	}
}
