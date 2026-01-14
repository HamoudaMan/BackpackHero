package game.view.draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawMiniMapButton {
	private final BufferedImage mapBtn;
	private int buttonX, buttonY, buttonW, buttonH;
	
	public DrawMiniMapButton() {
		this.mapBtn = ImageLoader.getLoadedImage("mapBtn");
	}
	public void render(Graphics2D g, int screenWidth) {
		buttonW = 300;
		buttonH = 300;
		buttonX = screenWidth -buttonW;
		buttonY = 0;
		
		g.drawImage(mapBtn, buttonX, buttonY, buttonW, buttonH, null);
		
		
	}
	public boolean isClicked(int mouseX, int mouseY) {
		return mouseX >= buttonX && mouseX <= buttonX +buttonW && 
					mouseY >= buttonY && mouseY <=buttonY+ buttonH;
	}
}
