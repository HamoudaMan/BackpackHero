package game.model.hero;

public class HeroStats {
	
	private int health;
  private int maxHealth;
  private int protection;

  public HeroStats(int maxHealth) {
  	if(maxHealth <= 0) {
  		throw new IllegalArgumentException("hero maxhealth must be positive");
  	}
  	this.maxHealth = maxHealth;
		this.health = maxHealth;
  }
  
  public void takeDamage(int damage ) {
    if(damage < 0) {
      throw new IllegalArgumentException("damage must be >= 0");
    }
    var effectiveDamage = damage - protection; 
    health = Math.max(0, health-effectiveDamage);
    protection = 0;
  }
  
  public void heal(int healAmount) {
    if(healAmount < 0) {
      throw new IllegalArgumentException("healAmount must be >= 0");
    }
    health = Math.min(maxHealth, health +healAmount);
  }
  
  public void addProtection(int protection) {
    if(protection < 0) {
      throw new IllegalArgumentException("protection must be >= 0");
    }
    this.protection += protection;
  }
  
  public boolean isDead() {
  	return health <= 0;
  }
  public int health() {
  	return health;
  }
  public int protection() {
  	return protection;
  }
  public int maxHealth() {
  	return maxHealth;
  }
}
