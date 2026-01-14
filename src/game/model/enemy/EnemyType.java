package game.model.enemy;




public enum EnemyType {
	
	
  SMALL_RATWOLF(new Action[] {Action.BLOCK, Action.ATTACK, Action.BLOCK},
  							new EnemyStats(32,7,13,0,6)
  ), 
  
  RATWOLF(new Action[] {Action.BLOCK, Action.BLOCK, Action.ATTACK, Action.BLOCK},
  				new EnemyStats(45,8,15,0,6)
  		),
  
  SLIME(new Action[] {Action.ATTACK, Action.HEAL, Action.ATTACK, Action.HEAL},
  			new EnemyStats(27,5,4,4,5)
  		),
	
  MUSKRAT_BRIGAND(new Action[] {Action.ATTACK,  Action.BLOCK, Action.BLOCK},
			new EnemyStats(30,5,4,0,8)
		),
  LILBEE(new Action[] {Action.ATTACK, Action.HEAL, Action.ATTACK, Action.BLOCK},
			new EnemyStats(16,7,14,5,4)
		);
  
  private final Action[] pattern;
  private final EnemyStats stats;

  private EnemyType(Action[] pattern, EnemyStats stats) {
    this.pattern = pattern;
    this.stats = stats;
  }
  
  public Action[] getPattern() {
    return pattern;
  }
  
  public Action nextAction(int turn) {
  	return pattern[turn % pattern.length];
  }
  public EnemyStats stats() {
  	return stats;
  }
}