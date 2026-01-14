package game.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawExitDoor {
	private final BufferedImage exitDoor;
	public DrawExitDoor() {
		this.exitDoor = ImageLoader.getLoadedImage("exitDoor");
	}
	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		var doorW = 300;
		var doorH = 500;
		var x = screenWidth - screenWidth*0.2 ;
		var y = screenHeight*0.5;
		
		g.drawImage(exitDoor, (int)x, (int)y, doorW, doorH, null);
		
	}
	public boolean isClicked(int mouseX, int mouseY, int screenWidth, int screenHeight ) {
		var doorW = 300;
		var doorH = 500;
		var x = screenWidth - screenWidth*0.2 ;
		var y = screenHeight*0.5;
		IO.println(mouseX + " /"+ mouseY);
		return mouseX >= x && mouseX <= doorW+x && mouseY>= y && mouseY <= y + doorH;
	}
}
