package game.zen.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.items.Item;
import game.items.armor.Armors;
import game.items.weapons.Weapon;
import game.zen.imgLoad.ImageLoader;

public class DrawItemDescription {
	private final BufferedImage parchemin ;
	//constant margins for the display of the description on an item
	
	 // Marges internes
  private static final int PADDING_X = 20;
  private static final int TITLE_Y = 35;
  private static final int TEXT_START_Y = 65;
  private static final int LINE_HEIGHT = 16;
	public DrawItemDescription() {
		this.parchemin =  ImageLoader.getLoadedImage("itemparchemin");
	}
	
	public void render(Graphics2D g, int itemX, int itemY, Item item, int screenWidth, int screenHeight ) {
		
		var height = 300;
		var width = 280;
		//SIZE OF THE PARCHEMIN : THE ZONE WHERE THE TEXT WILL BE DISPLAYED	
		var x = itemX-width /2 -55;
		var y = itemY -height-30;
		//to stay inside the window 
		
		if(x< 10) {
			x = 10;
		}
		//if too high
		if(y+height > screenHeight) {
			y = itemY+50;
		}
		if(x+width > screenWidth -10) {
			x = screenWidth -width-10;
		}
		g.drawImage(parchemin, x, y, width, height, null);
		
		g.setColor(new Color(60, 40, 18));
		//g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 15));
		g.drawString(item.name(), x+90, y+108);
		//description:
		g.setFont(new Font("Serif", Font.BOLD, 12));
		var textY = y+ 124;
		//stats:
		switch(item) {
			case Weapon w->{g.drawString("Damage: "+w.damage(), x+90, textY); textY+=15;}
			case Armors a->{g.drawString("Block: "+ a.block(), x+90, textY);textY+=15;}
		default ->{/*TODO : ADD ALL TYPES OF ITEMS*/ }
		}
		if(item.energyCost() >0) {
			g.drawString("Energy Cost: "+item.energyCost(), x+90, textY);
			textY+=15;
		}
		if(item.turnUsable()>0) {
			String uses="";
			if(item.turnUsable() > 99) {
				uses = "Uses per turn: no limit";
			}else {
				uses = "Uses per turn: "+item.turnUsable();
			}
			g.drawString(uses, x+90, textY);
		}

		
		/*//to draw in an exteranl box
		 * g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 15));
		g.drawString(item.name(), infoBoxX +10 , infoBoxY+15);
		g.setFont(new Font("Serif", Font.PLAIN, 15));
		var textY = infoBoxY +45;
		
		
		for(String s: item.description().split("\n")) {
			g.drawString(s, infoBoxX +10, textY);
			textY +=15;
		}
		 */
		
	}

}
