package game.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

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
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(enemy);
    Objects.requireNonNull(hero);
    for(var i = 0; i<enemy.size(); i++) {
      DrawEnemy(graphics, enemy.get(i), i);
    }
    DrawHero(graphics, hero);
  }
  
  public void DrawRoomCorridor(Graphics2D graphics, Hero hero) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(hero);
    DrawHero(graphics, hero);
    DrawEndCorridor(graphics);
  }
  
  public void DrawRoomTreasure(Graphics2D graphics, Hero hero) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(hero);
    DrawHero(graphics, hero);
    DrawChest(graphics);
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
  private void DrawHeroLevel(Graphics2D graphics, int level, int x, int y, int xWidth, int yHeight) {
    // Energy rectangle
    var levelX = x - xWidth/4/2;
    var levelY = y - screenHeight * 2/50/2;
    var levelWidth = xWidth/4;
    var levelHeight = screenHeight * 2/50;
    graphics.setColor(Color.GREEN);
    graphics.fillRect(levelX , levelY, levelWidth, levelHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(levelX , levelY, levelWidth, levelHeight);
    
    // Energy numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.BLACK;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringLevel = level + "" ;
    var stringLevelX = levelX + (levelWidth - fm.stringWidth(stringLevel))/2;
    var stringLevelY = levelY + (fm.getAscent() + levelHeight)/2 ;
    graphics.drawString(stringLevel, stringLevelX,  stringLevelY); 
  }
  
  private void DrawHeroEnergy(Graphics2D graphics, int energy, int x, int y, int xWidth, int yHeight) {
    // Energy rectangle
    var energyX = x + xWidth - xWidth/4/2;
    var energyY = y - screenHeight * 2/50/2;
    var energyWidth = xWidth/4;
    var energyHeight = screenHeight * 2/50;
    graphics.setColor(Color.BLUE);
    graphics.fillRect(energyX , energyY, energyWidth, energyHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(energyX , energyY, energyWidth, energyHeight);
    
    // Energy numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.WHITE;  
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
  
  private void DrawEntityHpRectangle(Graphics2D graphics, int health, int maxHealth, int x, int y, int xWidth, int yHeight) {
    // Health bar
    var entityHpX = x - screenWidth * 1/70;
    var entityHpY = y + yHeight + yHeight * 9/70;
    var entityHpWidth = xWidth + screenWidth * 2/70;
    var entityHpHeight = screenWidth * 1/70;
    graphics.setColor(Color.RED);
    graphics.fillRect(entityHpX, entityHpY, entityHpWidth * health/maxHealth, entityHpHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(entityHpX, entityHpY, entityHpWidth, entityHpHeight);
    
    // Health numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.BLACK;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringHp = health +"/"+maxHealth ;
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
  
  private void DrawHeroExpRectangle(Graphics2D graphics, int exp, int maxExp, int x, int y, int xWidth, int yHeight) {
    // Health bar
    var expX = x - screenWidth * 1/70;
    var expY = y + yHeight + yHeight * 15/70;
    var expWidth = xWidth + screenWidth * 2/70;
    var expHeight = screenWidth * 1/70;
    graphics.setColor(Color.GREEN);
    graphics.fillRect(expX, expY, expWidth * exp/maxExp, expHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(expX, expY, expWidth, expHeight);
    
    // Health numeric
    var font = new Font("Arial", Font.BOLD, screenWidth * 2/200);
    var colorString = Color.BLACK;  
    graphics.setColor(colorString);
    graphics.setFont(font);
    var fm = graphics.getFontMetrics();
    var stringExp = exp +"/"+maxExp ;
    var stringExpX = expX + (expWidth - fm.stringWidth(stringExp))/2;
    var stringExpY = expY + (fm.getAscent() + expHeight)/2;
    graphics.drawString(stringExp, stringExpX,  stringExpY);    
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
    // Hero experience
    DrawHeroExpRectangle(graphics, hero.exp()+5, hero.exp()+10, heroX, heroY, heroWidth, heroHeight);
    // Hero level
    DrawHeroLevel(graphics, hero.energy(), heroX, heroY, heroWidth, heroHeight);
    // Hero energy
    DrawHeroEnergy(graphics, hero.energy(), heroX, heroY, heroWidth, heroHeight);
    // Hero protection
    DrawHeroProtection(graphics, hero.protection(), heroX, heroY, heroWidth, heroHeight);
  }
  
  private void DrawEntityEndCorridor(Graphics2D graphics,  int x, int y, int xWidth, int yHeight) {
    graphics.setColor(Color.GRAY);
    graphics.fillRect(x-(x/100), y-(y/20), xWidth+(x/100)*2, yHeight+(y/20));
    graphics.setColor(Color.BLACK);
    graphics.fillRect(x, y, xWidth, yHeight);
  }
  
  private void DrawEndCorridor(Graphics2D graphics) {
    var endX = screenWidth * 15/20;
    var endY = screenHeight * 2/7;
    var endWidth = screenWidth * 2/10;
    var endHeight = screenHeight * 3/5;
    // End of corridor rectangle
    DrawEntityEndCorridor(graphics, endX, endY, endWidth, endHeight);
    // Draw next room at the top 
    DrawEntityName(graphics, "Next Room", endX, endY, endWidth);
  }
  
  private void DrawEntityChest(Graphics2D graphics,  int x, int y, int xWidth, int yHeight) {
    graphics.setColor(Color.ORANGE);
    graphics.fillRect(x-(x/100), y-(y/50), xWidth+(x/100)*2, yHeight+(y/50));
    graphics.setColor(Color.YELLOW);
    graphics.fillRect(x, y, xWidth, yHeight);
  }
  
  private void DrawChest(Graphics2D graphics) {
    var chestX = screenWidth * 13/20;
    var chestY = screenHeight * 5/7;
    var chestWidth = screenWidth * 2/10;
    var chestHeight = screenHeight * 1/10;
    DrawEntityChest(graphics, chestX, chestY, chestWidth, chestHeight);
    DrawEntityName(graphics, "Click me !!", chestX, chestY, chestWidth);
  }
}
