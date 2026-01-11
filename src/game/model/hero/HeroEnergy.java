package game.model.hero;

public class HeroEnergy {
	private int energy;
	private final int maxEnergy;
	
	public HeroEnergy(int maxEnergy) {
    this.energy = 3;
    this.maxEnergy = 3;
	}
	
  public boolean canConsumeEnergy(int energyToConsume) {
    if(energyToConsume < 0) {
      throw new IllegalArgumentException("energyToConsume must be > 0");
    }
    return energy >= energyToConsume;
  }
  
  public void consumeEnergy(int energyToConsume) {
    if(energyToConsume < 0) {
      throw new IllegalArgumentException("energyToConsume must be >= 0");
    }
    if (energy < energyToConsume) throw new IllegalStateException("not enough energy");
    energy -= energyToConsume;
  }
  
  public void resetEnergy() {
  	energy = maxEnergy;
  }
  
  public int energy() {
  	return energy;
  }
}
