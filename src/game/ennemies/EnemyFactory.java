package game.ennemies;

import java.util.Objects;

public final class EnemyFactory {

	public static Enemy create(EnemyType type) {
		Objects.requireNonNull(type);
		
		return switch(type) {
		case SMALLRATWOLF -> createSmallRatWolf();
		case RATWOLF -> createRatWolf();
		case SLIME -> createSlime();
		};
	}
	
	/**
	 * @return a new Small RatWolf  with full HP, (immutable stats) 
	 */
	private static Enemy createSmallRatWolf() {
		var stats = new EnemyStats(30, 5, 4, 5 );
		return new Enemy(EnemyType.SMALLRATWOLF, stats);
	}
	
	private static Enemy createRatWolf() {
		var stats = new EnemyStats(40, 7, 6, 8 );
		return new Enemy(EnemyType.RATWOLF,stats);
	}
	
	private static Enemy createSlime() {
		var stats = new EnemyStats(25, 4, 3, 4 );
		return new Enemy(EnemyType.SLIME,stats);
	}
}
