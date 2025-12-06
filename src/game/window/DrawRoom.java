package game.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import game.ennemies.Action;
import game.ennemies.Enemy;
import game.hero.Hero;

public class DrawRoom {
  private final int screenWidth;
  private final int screenHeight;
  
  public DrawRoom(int screenWidth, int screenHeight) {
    if(screenWidth <= 0 || screenHeight <= 0) {
      throw new IllegalArgumentException("screenWidth and screenHeight must be > 0");
    };
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }
  
  public void DrawRoomEnemy(Graphics2D graphics,  List<Enemy> enemy, Hero hero) {
    for(var i = 0; i<enemy.size(); i++) {
      DrawEnemy(graphics, enemy.get(i), i);
    }
    DrawHero(graphics, hero);
  }
  
  private void DrawEnemyNextActionAttack(Graphics2D graphics, int x, int y, int xWidth, int yHeight) {
    graphics.setColor(Color.RED);
    graphics.fillRect(x + xWidth* 2/4 - (xWidth/4)/2, y - screenHeight*5/50, xWidth/4, screenHeight*2/50);
  }
  
  private void DrawEnemyNextActionBlock(Graphics2D graphics, int x, int y, int xWidth, int yHeight) {
    graphics.setColor(Color.GRAY);
    graphics.fillRect(x + xWidth* 2/4 - (xWidth/4)/2, y - screenHeight*5/50, xWidth/4, screenHeight*2/50);
  }
  
  private void DrawEnemyEntityRectangle(Graphics2D graphics, int x, int y, int xWidth, int yHeight) {
    graphics.setColor(Color.RED);
    graphics.fillRect(x, y, xWidth, yHeight);
  }
  
  private void DrawHeroEntityRectangle(Graphics2D graphics, int x, int y, int xWidth, int yHeight) {
    graphics.setColor(Color.CYAN);
    graphics.fillRect(x, y, xWidth, yHeight);
  }
  private void DrawHeroEnergy(Graphics2D graphics, int energy, int x, int y, int xWidth, int yHeight) {
    // Energy rectangle
    var energyX = x - xWidth/4/2;
    var energyY = y - screenHeight * 2/50/2;
    var energyWidth = xWidth/4;
    var energyHeight = screenHeight * 2/50;
    graphics.setColor(Color.GREEN);
    graphics.fillRect(energyX , energyY, energyWidth, energyHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(energyX , energyY, energyWidth, energyHeight);
    
    // Energy numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.BLACK;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringEnergy = energy + "" ;
    var stringEnergyX = energyX + (energyWidth - fm.stringWidth(stringEnergy))/2;
    var stringEnergyY = energyY + (fm.getAscent() + energyHeight)/2 ;
    graphics.drawString(stringEnergy, stringEnergyX,  stringEnergyY); 
  }
  
  private void DrawHeroProtection(Graphics2D graphics, int protection, int x, int y, int xWidth, int yHeight) {
    // Protection rectangle
    var protectionX = x - xWidth/4/2;
    var protectionY = y + yHeight - screenHeight*2/50/2;
    var protectionWidth = xWidth/4;
    var protectionHeight = screenHeight*2/50;
    graphics.setColor(Color.GRAY);
    graphics.fillRect(protectionX , protectionY, protectionWidth, protectionHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(protectionX , protectionY, protectionWidth, protectionHeight);
    
    // Protection numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.BLACK;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringProtection = protection + "" ;
    var stringProtectionX = protectionX + (protectionWidth - fm.stringWidth(stringProtection))/2;
    var stringProtectionY = protectionY + (fm.getAscent() + protectionHeight)/2 ;
    graphics.drawString(stringProtection, stringProtectionX,  stringProtectionY); 
  }
  
  private void DrawEntityHpRectangle(Graphics2D graphics, int health, int maxHealtth, int x, int y, int xWidth, int yHeight) {
    // Health bar
    var entityHpX = x - screenWidth * 1/70;
    var entityHpY = y + yHeight + yHeight * 9/70;
    var entityHpWidth = xWidth + screenWidth * 2/70;
    var entityHpHeight = screenWidth * 1/70;
    graphics.setColor(Color.RED);
    graphics.fillRect(entityHpX, entityHpY, entityHpWidth * health/maxHealtth, entityHpHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(entityHpX, entityHpY, entityHpWidth, entityHpHeight);
    
    // Health numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.BLACK;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringHp = health +"/"+maxHealtth ;
    var stringHpX = entityHpX + (entityHpWidth - fm.stringWidth(stringHp))/2;
    var stringHpY = entityHpY + (fm.getAscent() + entityHpHeight)/2;
    graphics.drawString(stringHp, stringHpX,  stringHpY);    
  }
  
  private void DrawEntityName(Graphics2D graphics, String name, int x, int y, int xWidth) {
   var font = new Font("Arial", Font.BOLD, screenWidth * 3/200);
   var colorString = Color.BLACK;  
   graphics.setColor(colorString);
   graphics.setFont(font);
   var fm = graphics.getFontMetrics();
   var stringName = name;
   var stringNameX = x + (xWidth - fm.stringWidth(stringName))/2;
   var stringNameY = y - screenHeight/40;
   graphics.drawString(stringName, stringNameX,  stringNameY);
  }
  
  private void DrawEnemy(Graphics2D graphics, Enemy enemy, int position) {
    // Where to draw from the coordinate of the rectangle entity
    var enemyX = screenWidth * 4/7 + position* screenWidth *1/7;
    var enemyY = screenHeight * 3/7;
    var enemyWidth = screenWidth / 15;
    var enemyHeight = screenHeight * 2/5;
    
    // Enemy rectangle
    DrawEnemyEntityRectangle(graphics, enemyX, enemyY, enemyWidth, enemyHeight);
    // Enemy HP
    DrawEntityHpRectangle(graphics, enemy.health(), enemy.maxHealth(), enemyX, enemyY, enemyWidth, enemyHeight);
    // Enemy Name
    DrawEntityName(graphics, enemy.name(), enemyX, enemyY, enemyWidth);
    // Enemy Next Action
    switch (enemy.nextAction()) {
    case Action.ATTACK -> DrawEnemyNextActionAttack(graphics, enemyX, enemyY, enemyWidth, enemyHeight);
    case Action.BLOCK -> DrawEnemyNextActionBlock(graphics, enemyX, enemyY, enemyWidth, enemyHeight);
    };
  }
  
  private void DrawHero(Graphics2D graphics, Hero hero) {
    // Where to draw from the coordinate of the rectangle entity
    var heroX = screenWidth / 10;
    var heroY = screenHeight * 3/7;
    var heroWidth = screenWidth / 10;
    var heroHeight = screenHeight * 2/5;
    
    // Hero rectangle  
    DrawHeroEntityRectangle(graphics, heroX, heroY, heroWidth, heroHeight);
    // Hero name
    DrawEntityName(graphics, hero.name(), heroX, heroY, heroWidth);
    // Hero Health
    DrawEntityHpRectangle(graphics, hero.health(), hero.maxHealth(), heroX, heroY, heroWidth, heroHeight);
    // Hero energy
    DrawHeroEnergy(graphics, hero.energy(), heroX, heroY, heroWidth, heroHeight);
    //
    DrawHeroProtection(graphics, hero.protection(), heroX, heroY, heroWidth, heroHeight);
  }
}
