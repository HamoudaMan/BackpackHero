package game.model.hallOfFame;
/**
 * A record to store the stats of the game that has been played  
 */
public record GameStats( int maxHp, int heroLevel, int enemiesDefeated,int itemsValue, int floorsExplored) {
	public GameStats{
		if(maxHp <=0 ) {
			throw new IllegalArgumentException("maxHp must be > 0");
		}
		if(heroLevel <=0 ) {
			throw new IllegalArgumentException("heroLevel must be > 0");
		}
		if(enemiesDefeated <0 ) {
			throw new IllegalArgumentException("enemiesDefeated must be > 0");
		}
		if(itemsValue <0 ) {
			throw new IllegalArgumentException("itemsValue must be > 0");
		}
		if(floorsExplored <0 ) {
			throw new IllegalArgumentException("floorsExplored must be > 0");
		}
	}
}
