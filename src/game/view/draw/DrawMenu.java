package game.view.draw;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawMenu {
	private final BufferedImage menuBg ;
//	private final BufferedImage playButton;
	
	private  int buttonWidth, buttonHeight, buttonX, buttonY;
	
	public DrawMenu() {
		this.menuBg = ImageLoader.getLoadedImage("bg_menu");
		//this.playButton = ImageLoader.getLoadedImage("play_button");
	}
	public void render(Graphics2D g, int width, int height) {
		
		buttonWidth = 400;
		buttonHeight = 120;
		buttonX = 570;
		buttonY =700;
		//bg
		g.drawImage(menuBg, 0,0,  width, height, null );
		//play button 
		//g.drawImage(playButton, buttonX, buttonY, buttonWidth, buttonHeight, null);
	}	
	
	public boolean isClicked(int mouseX, int mouseY) {

		return mouseX >= buttonX && mouseX <= buttonX +buttonWidth && mouseY >= buttonY && mouseY <=buttonY+ buttonHeight;
		

	}
	
}
