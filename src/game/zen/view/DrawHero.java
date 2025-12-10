package game.zen.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.hero.Hero;

public class DrawHero {
	private final BufferedImage hero;
	
	public DrawHero() {
		this.hero = ImageLoader.load("/sprites/ui/dunjon/jojo.png");
	}
	
	public void render(Graphics2D g,  int screenWidth, int screenHeight) {
		var zoneW = screenWidth/6;
		var zoneH = screenHeight/4;
		var zoneX = screenWidth/4 - zoneW;
		var zoneY = screenHeight - zoneH - (zoneH/2);// pour bien le placer ou je veux 

		
		g.drawImage(hero, zoneX, zoneY, zoneW, zoneH, null);
	}
}

