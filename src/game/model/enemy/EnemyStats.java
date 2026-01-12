package game.model.enemy;


public record EnemyStats( int maxHealth, int damage, int block,int heal, int exp ) {
	public EnemyStats {
		
		if(maxHealth <=0) {
			throw new IllegalArgumentException("maxHealth must be positive");
		}
		if(damage <0 ) {
			throw new IllegalArgumentException("damage must be positive");
		}
		if(block <0 ) {
			throw new IllegalArgumentException("block must be positive");
		}
		if(heal <0 ) {
			throw new IllegalArgumentException("heal value must be positive");
		}
		if(exp <0 ) {
			throw new IllegalArgumentException("experience must be positive");
		}
		
	}

	
}