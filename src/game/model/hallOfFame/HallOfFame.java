package game.model.hallOfFame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * add the score and  sort the 3 best scores 
 */
public final class HallOfFame {
	private static final int MAX = 3;
	private final List<GameResult> results = new ArrayList<>();
	
	/**
	 * this method adds a score to the list of scores of the hall of fame 
	 * @param result
	 */
	public void add(GameResult result) {
		Objects.requireNonNull(result);
		results.add(result);
		
		results.sort(Comparator.comparingInt(GameResult::score).reversed());//merci poo8
		
		if(results.size() > MAX) {
			results.remove(results.size()-1);
		}
		
	
	}
	
	public boolean isEmpty() {
		return results.isEmpty();
	}
	
	//defensive copy 
	public List<GameResult> results(){
		return List.copyOf(results);
	}
}
