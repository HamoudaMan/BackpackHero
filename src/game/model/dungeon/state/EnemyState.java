package game.model.dungeon.state;

public class EnemyState {
	private boolean cleared = false;
	
	public boolean isCleared() {
		return cleared;
	}
	public void clear() {
		this.cleared = true;
	}
}