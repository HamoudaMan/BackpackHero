package game.view.draw;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.model.hallOfFame.HallOfFame;
import game.view.loader.ImageLoader;

public class DrawMenu {
	private final BufferedImage menuBg ;
	private final BufferedImage playButton;
	private final BufferedImage title;
	
	private  int buttonWidth, buttonHeight, buttonX, buttonY;
	private int titleWidth, titleHeight, titleX, titleY;
	private final DrawHallOfFame drawHallOfFame;
	
	
	public DrawMenu() {
		this.menuBg = ImageLoader.getLoadedImage("bg_menu");
		this.playButton = ImageLoader.getLoadedImage("play_button");
		this.title = ImageLoader.getLoadedImage("title");
		this.drawHallOfFame = new DrawHallOfFame();
	}
	public void render(Graphics2D g, int width, int height, HallOfFame hallOfFame) {
		//IO.println("drawing menu ");
		buttonWidth = 720;
		buttonHeight = 520;
		buttonX = width/2 - buttonWidth/2;
		buttonY = height/2 +100 ;
		
		titleWidth = (int)(width*0.6);
		titleHeight = height/3;
		titleX = width/2 - titleWidth/2;
		titleY = 0;

		
		//bg
		g.drawImage(menuBg, 0,0,  width, height, null );
		//title
		g.drawImage(title, titleX, titleY, titleWidth, titleHeight, null);
		//draw HallOf fame
		drawHallOfFame.render(g, hallOfFame, width, titleY+(int)(titleHeight*1.3), buttonY);
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
