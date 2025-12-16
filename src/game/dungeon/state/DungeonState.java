package game.dungeon.state;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import game.dungeon.Coord;

public class DungeonState {
	private Map<Coord, TreasureState> treasureStates;
	
	public DungeonState(){
		this.treasureStates = new HashMap<>();
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
}
