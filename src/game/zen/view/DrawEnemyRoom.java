package game.zen.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import game.ennemies.Enemy;
import game.zen.imgLoad.ImageLoader;
/*classe pour draw les enemies 
 * objectif : si y en a 1 -> afficgage simple
 * 						si y'en a 2 -> l'un a cote de l'autre 
 * 						si y'en a 3  -> en forme de triangle 
 */
public class DrawEnemyRoom {
	private final BufferedImage ratWolf;
	private final BufferedImage smallRatWolf;
	
	
	public DrawEnemyRoom() {
		this.ratWolf = ImageLoader.getLoadedImage("ratwolf");
		this.smallRatWolf = ImageLoader.getLoadedImage("smallratwolf");
	}
	
	public void render(Graphics2D g, List<Enemy> enemies, int screenWidth, int screenHeight) {
		
		var enemyCount = enemies.size();
		var enemyW = screenWidth/12;
		var enemyH = screenHeight/6;//peut etre meme plus petit 
		
		//afichage sur la meme ligne pour l'instant 
		var zoneX = (int)(screenWidth*0.7) ;// debut de la ligne 
		var zoneY = (screenHeight *4) /6;
		var space = enemyW;

		
		//var totalWidth = enemyCount * enemyW;
		//var totalHeight = enemyCount *enemyH;
		for(var i = 0; i<enemyCount;i++) {
			var x = zoneX + i *space;
			//var y = zoneY;
			Enemy e = enemies.get(i);
			/*
			switch(e.name()) {
			case "RatWolf" -> g.drawImage(ratWolf, x, zoneY, enemyW, enemyH, null);
			case "SmallRatWolf" -> g.drawImage(smallRatWolf, x, zoneY, enemyW, enemyH, null);
			}*/
			g.drawImage(ratWolf, x, zoneY, enemyW, enemyH, null);
			
			DrawHealthBar.render(g,x, zoneY - 15, (int)(enemyW*0.80), e.currentHealth(), e.stats().maxHealth());
			g.setColor(Color.WHITE);
			g.drawString("next Action : " + enemies.get(i).nextAction().toString(), x, zoneY-30);

		}
		
		
	}
}
