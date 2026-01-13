package game.view.draw;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawMenu {
	private final BufferedImage menuBg ;
	private final BufferedImage playButton;
	
	private  int buttonWidth, buttonHeight, buttonX, buttonY;
	
	public DrawMenu() {
		this.menuBg = ImageLoader.getLoadedImage("bg_menu");
		this.playButton = ImageLoader.getLoadedImage("play_button");
	}
	public void render(Graphics2D g, int width, int height) {
		//IO.println("drawing menu ");
		buttonWidth = 620;
		buttonHeight = 420;
		buttonX = width/2 - buttonWidth/2;
		buttonY = height/2  ;
		//buttonX = width/2 - buttonWidth/2;
		//buttonY = height/2 - buttonHeight/2;
		//g.setColor(java.awt.Color.RED);
		//g.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);

		//bg
		g.drawImage(menuBg, 0,0,  width, height, null );
		//play button 
		g.drawImage(playButton, buttonX, buttonY, buttonWidth, buttonHeight, null);
	}	
	
	public boolean isClicked(int mouseX, int mouseY) {
		System.out.println("Checking click: mx=" + mouseX + " my=" + mouseY); // ← DEBUG
    System.out.println("Button bounds: x=" + buttonX + " y=" + buttonY + 
                      " w=" + buttonWidth + " h=" + buttonHeight); // ← DEBUG
		return mouseX >= buttonX && mouseX <= buttonX +buttonWidth && mouseY >= buttonY && mouseY <=buttonY+ buttonHeight;
		

	}
	
}
