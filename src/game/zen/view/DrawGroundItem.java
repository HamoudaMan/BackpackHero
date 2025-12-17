package game.zen.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

import game.items.Item;
import game.zen.imgLoad.ImageLoader;

public class DrawGroundItem {
	

	
	public void render(Graphics2D g, int screenWidth ,int screenHeight, List<Item> items) {
		Objects.requireNonNull(items);
		var itemSize = 40;
		BufferedImage sprite;

		for( var i = 0;i<items.size() ; i++) {
			sprite = ImageLoader.getLoadedImage(items.get(i).spriteKey());
			//System.out.println(item.name() + " -> " + item.spriteKey());
			var x = screenWidth/2 +i*(itemSize +10);
			
			g.drawImage(sprite, x, screenHeight -100, itemSize,itemSize, null);

		}
	}
	
	public GroundItemHitBox findItemAt(int mouseX, int mouseY, int screenWidth, int screenHeight, List<Item>items) {
		var itemSize = 40;
		var gap = 10;
		var x = screenWidth/2;
		var y =  screenHeight-100;
		
		for(Item item: items) {
			if(mouseX >=x && mouseX <= x+itemSize && mouseY >= y && mouseY <= y+itemSize) {
				return new GroundItemHitBox(item, x, y);
			}
			x+=itemSize+gap;
		}
		return null;
	}
}
