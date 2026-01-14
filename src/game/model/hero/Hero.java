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
  private int experience;
  private int lvl;
  private int xpToNextLvl;
  private final HeroEquipment equipment;
  private final BackPack backPack;
  
  //for the score board 
  private int floorsCompleted;
  private int enemiesDefeated;
  
  public Hero(String name) {
    Objects.requireNonNull(name);
   
    this.name = name;
    this.stats = new HeroStats(40);
    this.energy = new HeroEnergy(3);
    this.equipment = new HeroEquipment();
    this.backPack = new BackPack(7, 5, 2, 1, 4, 3);
    this.experience = 0;
    this.lvl = 1;
    this.xpToNextLvl = calculateXpForNextLvl(1) ;
    
    this.floorsCompleted = 0;
    this.enemiesDefeated = 0;
    

  }
  
  private int calculateXpForNextLvl(int currentLvl) {
		return currentLvl *10;
	}
  public void addXp(int xp) {
  	if(xp < 0) {
  		throw new IllegalArgumentException("xp must be positive ");
  	}
  	experience += xp;
  	while(experience >= xpToNextLvl) {
  		levelUp();
  	}
  }

	private void levelUp() {
		experience -= xpToNextLvl;
		lvl++;
		xpToNextLvl = calculateXpForNextLvl(lvl);
		stats.heal(stats.maxHealth());//heal full when lvl up
		
	}
	//0 -> 100
  public int xpToPercentage() {
  	if(xpToNextLvl == 0) {
  		return 100;
  	}
  	return experience*100 /xpToNextLvl;
  }

	public void incrementEnemiesDefeated() {
		enemiesDefeated ++;
		
	}
	public void incrementFloorsCompleted() {
		floorsCompleted ++;
		
	}
	
	public boolean canPay(int amount) {
  	return backPack.hasGold(amount);
  }
  public void pay(int amount) {
  	backPack.spendGold(amount);
  }
  public int gold() {
  	return backPack.getGold();
  }
  public void earnGold(int amount) {
  	backPack.addGold(amount);
  }
  
  
  
  public void takeDamage(int dmg) {
  	stats().takeDamage(dmg);
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

  public int lvl() {
  	return lvl;
  	
  }
  public int experience() {
  	return experience;
  }
  
  public int xpToNextLvl() {
  	return xpToNextLvl;
  }

  public int floorsCompleted() {
  	return floorsCompleted;
  }
  
  public int enemiesDefeated() {
  	return enemiesDefeated;
  }
}