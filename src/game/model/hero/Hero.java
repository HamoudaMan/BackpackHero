package game.model.hero;

import java.util.Objects;

/**
 * The SimpleGameData class stores all relevant pieces of information for the
 * Hero.
 * 
 * @author
 */
public class Hero {
  /**
   * Represent the name of Hero
   */
  private final String name;
  /**
   * Represent the actual health of Hero. 
   */
  private int health;
  /**
   * Represent the max health Hero can get.
   */
  private int maxHealth;
  private int protection;
  private int energy;
  private int maxEnergy;
  private int exp;
  private int level;
  
  
  
  public Hero(String name, int health, int maxHealth) {
    Objects.requireNonNull(name);
    if(health < 0 && health > maxHealth) {
      throw new IllegalArgumentException("health must be > 0 and < maxHealth");
    }
    if(maxHealth < 0 && maxHealth >= health) {
      throw new IllegalArgumentException("maxHealth must be > 0 and maxHealth >= health");
    }
    this.name = name;
    this.health = health;
    this.maxHealth = maxHealth;
    this.protection = 0;
    this.energy = 3;
    this.maxEnergy = 3;
    this.exp = 0;
  }
  
  public String getName() {
    return name;
  }
  
  public int getHealth() {
    return health;
  }
  
  public int getMaxHealth() {
    return maxHealth;
  }
  
  public int getProtection() {
    return protection;
  }
  
  public int getEnergy() {
    return energy;
  }
  
  public int getMaxEnergy() {
    return maxEnergy;
  }
  
  public void resetEnergy() {
    energy = maxEnergy;
  }
  
  public boolean canConsumeEnergy(int energyToConsume) {
    if(energyToConsume < 0) {
      throw new IllegalArgumentException("energyToConsume must be > 0");
    }
    return energy >= energyToConsume;
  }
  
  public void consumeEnergy(int energyToConsume) {
    if(energyToConsume < 0) {
      throw new IllegalArgumentException("energyToConsume must be > 0");
    }
    if(!canConsumeEnergy(energyToConsume)) {
      throw new IllegalArgumentException("energy can't be negative");
    }
    energy -= energyToConsume;
  }

  public void heal(int healAmount) {
    if(healAmount < 0) {
      throw new IllegalArgumentException("healAmount must be > 0");
    }
    health = Math.min(maxHealth, health +healAmount);
  }
  
  public void addProtection(int protection) {
    if(protection < 0) {
      throw new IllegalArgumentException("protection must be > 0");
    }
    this.protection += protection;
  }
  
  public void resetProtection() {
    protection = 0;
  }
  
  public void takeDamage(int damage ) {
    if(damage < 0) {
      throw new IllegalArgumentException("damage must be > 0");
    }
    var effectiveDamage = damage - protection; 
    if(effectiveDamage < 0) {
      effectiveDamage = 0;
    } 
    health -= effectiveDamage;
    if(health < 0) {
      health = 0;
    }
    protection = 0;
  }
}