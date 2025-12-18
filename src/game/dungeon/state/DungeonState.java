package game.dungeon.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import game.dungeon.Coord;

public class DungeonState {
	private Map<Coord, TreasureState> treasureStates;
	private Map<Coord, HealerState> healerStates;
	
	public DungeonState(){
		this.treasureStates = new HashMap<>();
		this.healerStates = new HashMap<>();
	}
	
	/**
	 * each treasure room has a state , if it has no state yet a new one is created 
	 * @param coord the coord of the current room
	 * @return the treasure state associated with this room 
	 */
	public TreasureState treasureState(Coord coord) {
		Objects.requireNonNull(coord);
		if(!treasureStates.containsKey(coord)) {
			var t = new TreasureState();
			treasureStates.put(coord, t);
		}
		return treasureStates.get(coord);
		//return treasureStates.computeIfAbsent(coord, c->new TreasureState());//to test
	}
	
	public HealerState healerState(Coord coord) {
		Objects.requireNonNull(coord);
		if(!healerStates.containsKey(coord)) {
			var t = new HealerState();
			healerStates.put(coord, t);
		}
		return healerStates.get(coord);
		//return healerState.computeIfAbsent(coord, c->new healerState);//to test
	}
	
}
