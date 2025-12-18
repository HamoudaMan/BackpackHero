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
   * Represent the actual healt of Hero. 
   */
  private int health;
  /**
   * Represent the max healt Hero can get.
   */
  private int maxHealth;
  /**
   * Represent the mana of Hero.
   */
  private int mana;
  /**
   * Represent the gold of Hero
   */
  private int gold;
  
  public Hero(String name, int health, int maxHealth, int mana, int gold) {
    Objects.requireNonNull(name);
    if(health < 0 && health > maxHealth) {
      throw new IllegalArgumentException("health must be > 0 and < maxHealth");
    }
    if(maxHealth < 0) {
      throw new IllegalArgumentException("maxHealth must be > 0");
    }
    if(mana < 0) {
      throw new IllegalArgumentException("mana must be > 0");
    }
    if(gold < 0) {
      throw new IllegalArgumentException("gold must be > 0");
    }
    this.name = name;
    this.health = health;
    this.maxHealth = maxHealth;
    this.mana = mana;
    this.gold = gold;
  }
  
}