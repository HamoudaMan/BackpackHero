package game.model.hallOfFame;

import java.util.Objects;

/**
 * A class to calculate the score 
 */
public class ScoreCalculator {
	
	public GameResult scoreCalculator(GameStats stats) {
		Objects.requireNonNull(stats);
		var score = 0;
		score+= stats.enemiesDefeated()*100;
		score += stats.maxHp()*20;
		score+= stats.heroLevel()*150;
		score+=stats.floorsExplored()*120;//bonus
		score+= stats.itemsValue()*10;
		
		return new GameResult(score, stats);
	}
}
