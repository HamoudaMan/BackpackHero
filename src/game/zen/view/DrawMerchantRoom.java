package game.zen.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DrawMerchantRoom {
  private final BufferedImage merchant;
  
  public DrawMerchantRoom() {
    this.merchant = ImageLoader.load("/sprites/ui/dunjon/merchant/dwarfMerchant.png");
  }
  
  private void merchantSpeech(Graphics2D g, int screenWidth, int screenHeight , int x, int y, int xWidth, int yHeight) {
    var rectX = x - screenWidth * 1/10;
    var rectY = y - yHeight * 14/70;
    var rectWidth = xWidth + screenWidth * 3/20;
    var rectHeight = screenWidth * 2/90;
    g.setColor(Color.WHITE);
    g.fillRect(rectX, rectY, rectWidth , rectHeight);
    g.setColor(Color.BLACK);
    g.drawRect(rectX, rectY, rectWidth, rectHeight);
    
    textSpeech(g, screenWidth, screenHeight, rectX, rectY, rectWidth, rectHeight);
  }
  
  
  private void textSpeech(Graphics2D g, int screenWidth, int screenHeight , int x, int y, int xWidth, int yHeight) {
    var font = new Font("Arial", Font.BOLD, screenWidth * 3/200);
    var colorString = Color.BLACK;  
    g.setColor(colorString);
    g.setFont(font);
    var fm = g.getFontMetrics();
    var stringSpeech = "I'm the best merchant you can find here !!";
    var stringSpeechX = x + (xWidth - fm.stringWidth(stringSpeech))/2;
    var stringSpeechY = y + (fm.getAscent() + yHeight)/2; ;
    g.drawString(stringSpeech, stringSpeechX,  stringSpeechY);
  }
  
  // 
  private void loadItemImage(Graphics2D g, int screenWidth, int screenHeight, String path, int position) {
    var itemX = screenWidth * 14/20 + position* screenWidth *2/20;
    var itemY = screenHeight * 4/8;
    var itemWidth = screenWidth * 1/15;
    var itemHeight = screenHeight * 2/5;
    
    var itemImage = ImageLoader.load(path);
    var price = 2;
    DrawItemPrice(g, screenWidth, screenHeight,itemX, itemY, itemWidth, price);
    g.drawImage(itemImage, itemX, itemY, itemWidth, itemHeight, null);
  }
  
  private void DrawItemPrice(Graphics2D graphics, int screenWidth, int screenHeight, int x, int y, int xWidth, int price) {
    var font = new Font("Arial", Font.BOLD, screenWidth * 3/200);
    var colorString = Color.YELLOW;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringPrice = price + "g";
    var stringPriceX = x + (xWidth - fm.stringWidth(stringPrice))/2;
    var stringPriceY = y - screenHeight/40;
    graphics.drawString(stringPrice, stringPriceX,  stringPriceY);
   }
  
  // il faudra que la fonction prenne en argument une liste d'item
  public void render(Graphics2D g,  int screenWidth, int screenHeight) {
        
    
    var x = screenWidth * 4/9;
    var y = screenHeight * 5/8;
    var xWidth = screenWidth / 6;
    var yHeight = screenHeight / 4;
    
    for(int i = 0; i < 3; i++) {
      loadItemImage(g, screenWidth, screenHeight, "/sprites/items/weapon/woodenSword.png" , i);
    }
    // draw speech of the merchant
    merchantSpeech(g, screenWidth, screenHeight, x, y, xWidth, yHeight);
    // draw image of merchant
    g.drawImage(merchant, x, y, xWidth, yHeight, null);
  }
}

