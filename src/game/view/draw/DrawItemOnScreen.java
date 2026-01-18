package game.view.draw;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import game.model.backpack.BackPack;
import game.model.item.ItemOnScreen;
import game.view.loader.ImageLoader;
import game.view.loader.SpriteKeyFinder;

public class DrawItemOnScreen {
  public void render(Graphics2D g, int screenWidth ,int screenHeight, List<ItemOnScreen> itemOnScreen, BackPack backpack, DrawBackPack draw) {
    Objects.requireNonNull(itemOnScreen);
    Objects.requireNonNull(backpack);
    Objects.requireNonNull(draw);
    var cellWidth = draw.getCellWidth(backpack, screenWidth);
    var cellHeight = draw.getCellHeight(backpack, screenHeight);
//    BufferedImage sprite;
    for(var i = 0; i < itemOnScreen.size(); i++) {
      var actualItem = itemOnScreen.get(i);
      var spriteKey = SpriteKeyFinder.spriteKey(actualItem.item());
      var sprite = ImageLoader.getLoadedImage(spriteKey);
      g.drawImage(sprite, actualItem.coord().x() , actualItem.coord().y(), cellWidth * actualItem.item().shape()[0].length, cellHeight * actualItem.item().shape().length, null);
    }
  }
  
  public void renderOneItem(Graphics2D g, int mouseX, int mouseY, int screenWidth ,int screenHeight, ItemOnScreen itemOnScreen, BackPack backpack, DrawBackPack draw) {
    Objects.requireNonNull(itemOnScreen);
    Objects.requireNonNull(backpack);
    Objects.requireNonNull(draw);
    var cellWidth = draw.getCellWidth(backpack, screenWidth);
    var cellHeight = draw.getCellHeight(backpack, screenHeight);
    var spriteKey = SpriteKeyFinder.spriteKey(itemOnScreen.item());
    var sprite = ImageLoader.getLoadedImage(spriteKey);
    g.drawImage(sprite, mouseX , mouseY, cellWidth * itemOnScreen.item().shape()[0].length, cellHeight * itemOnScreen.item().shape().length, null);
    g.drawRect(mouseX, mouseY, cellWidth * itemOnScreen.item().shape()[0].length, cellHeight * itemOnScreen.item().shape().length);
    //    g.drawImage(sprite, mouseX , mouseY, 100, 300, null);
    
  }
  
  public int findItemAt(int mouseX, int mouseY, int screenWidth, int screenHeight, ArrayList<ItemOnScreen> itemOnScreen, BackPack backpack, DrawBackPack draw) {
    Objects.requireNonNull(itemOnScreen);
    Objects.requireNonNull(backpack);
    Objects.requireNonNull(draw);
    var cellWidth = draw.getCellWidth(backpack, screenWidth);
    var cellHeight = draw.getCellHeight(backpack, screenHeight);
    for(var i = 0; i < itemOnScreen.size(); i++) {
      var actualItem = itemOnScreen.get(i);
      
      
      if(mouseX >= actualItem.coord().x() && mouseY >= actualItem.coord().y() && 
      		mouseX <= actualItem.coord().x() + cellWidth * actualItem.item().shape()[0].length &&
      		mouseY <= actualItem.coord().y() + cellHeight * actualItem.item().shape().length) {
        return i;
      }
    }
    return -1;
  }
}
