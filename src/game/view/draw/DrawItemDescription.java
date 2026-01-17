package game.view.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.model.item.Consumables;
import game.model.item.Curse;
import game.model.item.Gold;
import game.model.item.Item;
import game.model.item.Magic;
import game.model.item.Shield;
import game.model.item.Weapon;
import game.view.loader.ImageLoader;


public class DrawItemDescription {
	private final BufferedImage parchemin ;
	//constant margins for the display of the description on an item
	

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
		
		var name = "";
		var energyCost = 0;
		var numberOfUse = "";
		switch(item) {
			case Weapon w -> {name = w.name() ; energyCost = w.energyCost();}
			case Shield s ->{name = s.name() ; energyCost = s.energyCost();}
			case Gold go -> name = "Gold ( " + go.amount()+" )";
			case Consumables c -> {name = c.name() ; energyCost = c.energyCost();}
			case Magic m -> name = m.name();
			
			case Curse c -> name = c.name();
		default -> name = " unknown";
		}
		
		g.drawString(name, x+90, y+108);
		//description:
		g.setFont(new Font("Serif", Font.BOLD, 12));
		
		var textY = y+ 124;
		//stats:
		switch(item) {
			case Weapon w->{g.drawString("Damage: "+w.damage(), x+90, textY); textY+=15; energyCost = w.energyCost();}
			case Consumables c ->{ g.drawString("Heal: "+c.heal(), x+90, textY); textY+=15; energyCost = c.energyCost();
															textY += 15; numberOfUse = "1";
															g.drawString("Number of Use: "+numberOfUse, x+90, textY);}
			case Shield s->{g.drawString("Block: "+ s.protection(), x+90, textY);textY+=15;energyCost = s.energyCost();}
			case Magic m -> g.drawString("Stores mana  ", x+90, textY);
		default ->{/*TODO : ADD ALL TYPES OF ITEMS*/ }
		}
		if(energyCost >0) {
			g.drawString("Energy Cost: "+energyCost, x+90, textY);
			textY+=15;
		}
		/*
		if(item.turnUsable()>0) {
			String uses="";
			if(item.turnUsable() > 99) {
				uses = "Uses per turn: no limit";
			}else {
				uses = "Uses per turn: "+item.turnUsable();
			}*/
			//g.drawString("x uses", x+90, textY);
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


