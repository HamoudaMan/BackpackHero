package game.model.enemy;

public class EnemyFactory {
	private EnemyFactory() {}
	
	public static Enemy create(EnemyType type) {
		return new Enemy(type);
	}
}
