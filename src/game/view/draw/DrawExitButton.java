package game.view.draw;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.view.loader.ImageLoader;

public class DrawExitButton {
	private   int width,height, x, y;
	private final  BufferedImage exitBtn;
	
	public DrawExitButton() {
		this.exitBtn = ImageLoader.getLoadedImage("exit_button");
	}
	
	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		 width = 350;
		 height = 200;
		 x = 0 ;
		 y = screenHeight/6 - height/2;
		 
		 g.drawImage(exitBtn, x, y, width, height, null);
	
	}
	
	public boolean isClicked(int mouseX, int mouseY) {
		return mouseX >=x && mouseX <= x +width && mouseY >= y && mouseY <= y + height;
	}
}
