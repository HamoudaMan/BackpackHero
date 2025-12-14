package game.zen.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.hero.Hero;
import game.zen.imgLoad.ImageLoader;
import game.zen.view.stats.DrawEnergyBar;
import game.zen.view.stats.DrawHealthBar;

public class DrawHero {
	private final BufferedImage hero;
	
	public DrawHero() {
		this.hero = ImageLoader.getLoadedImage("hero");
	}
	
	public void render(Graphics2D g, Hero h, int screenWidth, int screenHeight) {
		var heroW = screenWidth/8;
		var heroH = screenHeight/6;
		var heroX = screenWidth/4 - heroW;
		var heroY = screenHeight - heroH - (heroH/2);// pour bien le placer ou je veux 

		
		g.drawImage(hero, heroX, heroY, heroW, heroH, null);
		
		var healthBarX = heroX+50;
		var healthBarY = heroY+ heroH+3;
		var healthBarW = screenWidth/16;
		DrawHealthBar.render(g, healthBarX ,healthBarY , healthBarW, h.health(), h.maxHealth());
		
		DrawEnergyBar.renderEnergy(g, heroX+ heroX/3, heroY, h.energy());
		
	}
	/*
	public void renderHeroStats(Graphics2D g, Hero hero, int screenWidth, int screenHeight) {
		var healthBarX = heroX;
		var healthBarY = 40;
		var healthBarW = screenWidth/6;
		DrawHealthBar.render(g, healthBarX,healthBarY , healthBarW, hero.health(), hero.maxHealth());
	}
	*/
}

