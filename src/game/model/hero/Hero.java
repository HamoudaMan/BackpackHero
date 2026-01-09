package game.model.hero;

import java.util.Objects;

import game.model.backpack.BackPack;

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
  private final HeroStats stats;
  private final HeroEnergy energy;
  private final HeroEquipment equipment;
  private final BackPack backPack;
  
  
  
  public Hero(String name) {
    Objects.requireNonNull(name);
   
    this.name = name;
    this.stats = new HeroStats(40);
    this.energy = new HeroEnergy(3);
    this.equipment = new HeroEquipment();
    this.backPack = new BackPack(7, 5, 2, 1, 4, 3);
  }
  
  public void takeDamage(int dmg) {
  	Math.max(0, stats.health()-dmg);
  }
  public String name() {
    return name;
  }
  
  public HeroStats stats() {
  	return stats;
  }
  public HeroEnergy energy() {
  	return energy;
  }
  public HeroEquipment equipment() {
  	return equipment;
  }
  public BackPack backPack(){
  	return backPack;
  }

  



}