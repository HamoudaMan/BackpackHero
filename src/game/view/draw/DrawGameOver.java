package game.view.draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawGameOver {
	private final BufferedImage gameOver ;
	private final BufferedImage retryBtn ;
	private final BufferedImage mainMenuBtn ;
	
	private int retryWidth, retryHeight, retryX, retryY;
	private int mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight;
	
	public DrawGameOver() {
		this.gameOver = ImageLoader.getLoadedImage("gameOver");
		this.retryBtn = ImageLoader.getLoadedImage("retryBtn");
		this.mainMenuBtn = ImageLoader.getLoadedImage("mainMenuBtn");
		
	}
	
	private void calculateDimensions(int screenWidth, int screenHeight) {
    int buttonWidth = screenWidth / 5;  
    int buttonHeight = screenHeight / 4; 
    int centerX = screenWidth / 2;
    int buttonSpace = 20;
    retryWidth = buttonWidth;
    retryHeight = buttonHeight;
    retryX = centerX - buttonWidth - buttonSpace / 2 ;
    retryY = screenHeight - 150; 
    
    mainMenuWidth = buttonWidth;
    mainMenuHeight = buttonHeight;
    mainMenuX = centerX + buttonSpace / 2;
    mainMenuY = screenHeight - 150;
	}
	
	public void render(Graphics2D g, int screenWidth, int screenHeight ) {
		calculateDimensions(screenWidth,screenHeight);
		g.drawImage(gameOver, 0, 0, screenWidth, screenHeight, null);
		g.drawImage(retryBtn, retryX, retryY, retryWidth, retryHeight, null);
		g.drawImage(mainMenuBtn, mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight, null);
		
		
	}
	
	public boolean retryIsClicked(int mouseX, int mouseY) {
		System.out.println("Checking click: mx=" + mouseX + " my=" + mouseY); // ← DEBUG
    System.out.println("Button bounds: x=" + retryX + " y=" + retryY + 
                      " w=" + retryWidth + " h=" + retryHeight); // ← DEBUG
		return mouseX >= retryX && mouseX <= retryX +retryWidth && mouseY >= retryY && mouseY <=retryY+ retryHeight;

	}
	
	public boolean mainMenuBtnIsClicked(int mouseX, int mouseY) {
		System.out.println("Checking click: mx=" + mouseX + " my=" + mouseY); // ← DEBUG
    System.out.println("Button bounds: x=" + mainMenuX + " y=" + mainMenuY + 
                      " w=" + mainMenuWidth + " h=" + mainMenuHeight); // ← DEBUG
		return mouseX >= mainMenuX && mouseX <= mainMenuX +mainMenuWidth && mouseY >= mainMenuY && mouseY <=mainMenuY+ mainMenuHeight;
		

	}
}
