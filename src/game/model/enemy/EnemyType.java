package game.model.enemy;




public enum EnemyType {
	
	
  SMALL_RATWOLF(new Action[] {Action.ATTACK, Action.ATTACK, Action.BLOCK},
  							new EnemyStats(30,5,4,5)
  ), 
  
  RATWOLF(new Action[] {Action.ATTACK, Action.BLOCK, Action.ATTACK, Action.BLOCK},
  				new EnemyStats(30,5,4,5)
  		),
  
  SLIME(new Action[] {Action.ATTACK, Action.HEAL, Action.ATTACK, Action.HEAL},
  			new EnemyStats(30,5,4,5)
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