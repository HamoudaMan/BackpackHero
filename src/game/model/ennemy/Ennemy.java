package game.model.ennemy;

import java.util.Objects;

public class Ennemy {
  private final EnnemyType type;
  private int health;
  private int maxHealth;
  private int damage;
  private int block;
  private int heal;
  
  public Ennemy (EnnemyType type, int health, int maxHealth, int damage, int block, int heal) {
    Objects.requireNonNull(type);
    if(health < 0 && health > maxHealth) {
      throw new IllegalArgumentException("health must be > 0 and < maxHealth");
    }
    if(maxHealth < 0 && maxHealth < health) {
      throw new IllegalArgumentException("maxHealth must be > 0 and maxHealth >= health");
    }
    if(damage < 0) {
      throw new IllegalArgumentException("damage must be > 0");
    }
    if(block < 0) {
      throw new IllegalArgumentException("block must be > 0");
    }
    if(heal < 0) {
      throw new IllegalArgumentException("heal must be > 0");
    }
    this.type = type;
    this.health = health;
    this.maxHealth = maxHealth;
    this.damage = damage;
    this.block = block;
    this.heal = heal;
  }
  
  public EnnemyType getType() {
    return type;
  }
  
  public int getHealth() {
    return health;
  }
  
  public int getMaxHealth() {
    return maxHealth;
  }
  
  public int getDamage() {
    return damage;
  }
  
  public int getBlock() {
    return block;
  }
  
  public int getHeal() {
    return heal;
  }
}
