package game.zen.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.zen.imgLoad.ImageLoader;

public class DrawBackGround {
	private final BufferedImage bg;
	
	public DrawBackGround() {
		this.bg = ImageLoader.getLoadedImage("bg");
		//System.out.println(ImageLoader.class.getResource("/sprites/background.png"));

	}
	
	public void render(Graphics2D g, int width, int height, int level) {
		g.drawImage(bg, 0,0,  width, height, null );
		g.setColor(Color.WHITE);
		g.drawString("Floor "+ level, 50, 20);
		g.setColor(Color.BLACK);
		// drawImage(image, x, y, width, height, null);
	}
}
