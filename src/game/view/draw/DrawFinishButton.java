package game.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawFinishButton {
	private static  int width,height, x, y;
	private final  BufferedImage finishBtn;
	
	public DrawFinishButton() {
		this.finishBtn = ImageLoader.getLoadedImage("finishLooting");
	}
	
	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		 width = 300;
		 height = 250;
		 x = screenWidth-width ;
		 y = screenHeight -500;
		 
		 g.drawImage(finishBtn, x, y, width, height, null);
	
	}
	
	public boolean isClicked(int mouseX, int mouseY, int screenWidth, int screenHeight) {
		return mouseX >=x && mouseX <= x +width && mouseY >= y && mouseY <= y + height;
	}
}
