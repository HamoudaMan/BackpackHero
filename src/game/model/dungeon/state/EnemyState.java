package game.model.dungeon.state;
/**
 * A class to help track the state of an enemyroom 
 * and the after combat items selection
 */
public class EnemyState {
	private boolean cleared = false;
	
	public boolean isCleared() {
		return cleared;
	}
	public void clear() {
		this.cleared = true;
	}
}