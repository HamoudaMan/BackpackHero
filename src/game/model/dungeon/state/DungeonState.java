package game.model.dungeon.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import game.model.dungeon.DCoord;

public class DungeonState {
	private Map<DCoord, TreasureState> treasureStates;
	private Map<DCoord, HealerState> healerStates;
	private Map<DCoord, EnemyState> enemyStates;
	
	public DungeonState(){
		this.treasureStates = new HashMap<>();
		this.healerStates = new HashMap<>();
		this.enemyStates = new HashMap<>();
	}
	
	/**
	 * each treasure room has a state , if it has no state yet a new one is created 
	 * @param coord the coord of the current room
	 * @return the treasure state associated with this room 
	 */
	public TreasureState treasureState(DCoord coord) {
		Objects.requireNonNull(coord);
		if(!treasureStates.containsKey(coord)) {
			var t = new TreasureState();
			treasureStates.put(coord, t);
		}
		return treasureStates.get(coord);
		//return treasureStates.computeIfAbsent(coord, c->new TreasureState());//to test
	}
	
	public HealerState healerState(DCoord coord) {
		Objects.requireNonNull(coord);
		if(!healerStates.containsKey(coord)) {
			var t = new HealerState();
			healerStates.put(coord, t);
		}
		return healerStates.get(coord);
		//return healerStates.computeIfAbsent(coord, c->new HealerState();//to test
	}
	
	public EnemyState enemyState(DCoord coord) {
		
		Objects.requireNonNull(coord);
		if(!enemyStates.containsKey(coord)) {
			var t = new EnemyState();
			enemyStates.put(coord, t);
		}
		return enemyStates.get(coord);
		//return enemyStates.computeIfAbsent(coord, c->new EnemyState());//to test
	}
}