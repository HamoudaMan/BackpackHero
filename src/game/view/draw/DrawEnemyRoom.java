package game.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import game.model.enemy.Action;
import game.model.enemy.Enemy;
import game.view.loader.ImageLoader;
import game.view.stats.DrawBlockBar;
import game.view.stats.DrawHealthBar;
/*classe pour draw les enemies 
 * objectif : si y en a 1 -> afficgage simple
 * 						si y'en a 2 -> l'un a cote de l'autre 
 * 						si y'en a 3  -> en forme de triangle 
 */
public class DrawEnemyRoom {
	private final BufferedImage ratWolf;
	private final BufferedImage smallRatWolf;
	private final BufferedImage slime;
	private final BufferedImage lilBee;
	private final BufferedImage muskratBrigand;
	//obliged enemies
	private final BufferedImage frogSorcerer;
	private final BufferedImage livingShadow;
	private final BufferedImage beeQueen;
	private final BufferedImage attackIcon;
	private final BufferedImage blockIcon;
	private final BufferedImage healIcon;
	private final BufferedImage summonIcon;
	private final BufferedImage lowEnergy;
	private final DrawBlockBar blockBar = new DrawBlockBar();
	
	private int enemyW;
	private int enemyH;
	private int zoneX;
	private int zoneY;
	private int space;
	private int screenH, screenW;
	
	private Enemy selectedEnemy = null;
	
	//private final BufferedImage smallRatWolf;
	
	
	public DrawEnemyRoom() {
		this.ratWolf = ImageLoader.getLoadedImage("ratwolf");
		this.smallRatWolf = ImageLoader.getLoadedImage("smallratwolf");
		this.slime = ImageLoader.getLoadedImage("slime");
		this.lilBee = ImageLoader.getLoadedImage("lilbee");
		this.muskratBrigand = ImageLoader.getLoadedImage("muskratbrigand");
		this.attackIcon = ImageLoader.getLoadedImage("attackicon");
		this.blockIcon = ImageLoader.getLoadedImage("blockIcon");
		this.healIcon = ImageLoader.getLoadedImage("healicon");
		this.lowEnergy = ImageLoader.getLoadedImage("lowenergy");
		this.summonIcon = ImageLoader.getLoadedImage("summonIcon");
	
		this.frogSorcerer = ImageLoader.getLoadedImage("frogSorcerer");
		this.livingShadow = ImageLoader.getLoadedImage("livingShadow");
		this.beeQueen = ImageLoader.getLoadedImage("beeQueen");
		
		
	}
	
	public void setSelectedEnemy(Enemy enemy) {
		this.selectedEnemy = enemy;
	}
	private void calculateDimensions(int screenWidth, int screenHeight) {
		screenW = screenWidth;
		screenH = screenHeight;
		enemyW = screenWidth/12;
		enemyH = screenHeight/6;
		zoneX = (int)(screenWidth*0.7) ;
		zoneY = (screenHeight *4) /6;
	  space = enemyW;
	}
	
	public void renderLowEnergy(Graphics2D g) {
		g.drawImage(lowEnergy, screenW/2 -200, screenH-150, 400, 250 , null);
	}
	
	public void render(Graphics2D g, List<Enemy> enemies, int screenWidth, int screenHeight) {
		calculateDimensions(screenWidth, screenHeight);
		var enemyCount = enemies.size();
		


		
		//var totalWidth = enemyCount * enemyW;
		//var totalHeight = enemyCount *enemyH;
		for(var i = 0; i<enemyCount;i++) {
			var x = zoneX + i *space;
			//to center the healthbar under the enemy
			var barW = (int)(enemyW*0.6);
			var barX = x+ (enemyW-barW)/2;
			var barY = zoneY+enemyH+ 6;

			
			
			//var y = zoneY;
			Enemy e = enemies.get(i);
			
			//draw around the enmy a box 
			if (e == selectedEnemy) {
		    g.setColor(new Color(255, 255, 0, 120));
		    g.drawRect(x, zoneY, enemyW, enemyH);
		}

			//to draw the nextAction Icon
			var iconW = enemyW/3;
			var iconX = x +(enemyW-iconW)/2;
			var iconY = zoneY - iconW -8;
			Action a = e.nextAction();
			var actionVal = e.nextActionValue();
		  BufferedImage icon = switch(a) {
		  	case ATTACK -> attackIcon;
		  	case BLOCK -> blockIcon;
		  	case HEAL -> healIcon;
		  	default ->{ throw new IllegalArgumentException("icon or type not found ");}
		  };
		  
			g.drawImage(icon, iconX, iconY, iconW,iconW,  null);
			g.setColor(Color.WHITE);
			g.drawString(String.valueOf(actionVal), iconX+iconW/2,iconY+iconW +11 );
					
			
			//draw the sprite 
			switch(e.type()) {
			case RATWOLF -> g.drawImage(ratWolf, x, zoneY, enemyW, enemyH, null);
			case SMALL_RATWOLF -> g.drawImage(smallRatWolf, x, zoneY, enemyW, enemyH, null);
			case SLIME -> g.drawImage(slime, x, zoneY, enemyW, enemyH, null);
			case MUSKRAT_BRIGAND -> g.drawImage(muskratBrigand, x, zoneY, enemyW, enemyH, null);
			case LILBEE -> g.drawImage(lilBee, x, zoneY, enemyW, enemyH, null);
			case FROG_SORCERER ->g.drawImage(frogSorcerer, x, zoneY, enemyW, enemyH, null);
			case LIVING_SHADOW ->g.drawImage(livingShadow, x, zoneY, enemyW, enemyH, null);
			case BEE_QUEEN ->g.drawImage(beeQueen, x, zoneY, enemyW, enemyH, null);
			}
		
			
			DrawHealthBar.render(g,barX, barY, barW, e.currentHealth(), e.stats().maxHealth());
			if(e.stats().block() >0) {
				blockBar.renderBlockBar(g, barX, barY +8, e.protection());
			}
			

		}
		
		
	}
	

	public Enemy enemyIsClicked(int mouseX, int mouseY, List<Enemy> enemies) {
		
		var enemyCount = enemies.size();
		
		
		for(var i = 0; i<enemyCount;i++) {
			var x = zoneX + i *space;
			var y = zoneY;
			if(mouseX >= x && mouseX <= x +enemyW && mouseY >= y && mouseY <=y + enemyH  ) {
				IO.println("enemy : " + enemies.get(i).type());
				return enemies.get(i);
			}
		}

		return null;
	}
	
}
