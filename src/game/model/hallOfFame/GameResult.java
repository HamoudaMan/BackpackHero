package game.model.hallOfFame;

public record GameResult(int score ,GameStats stats) {
	public GameResult{
		if(score <0) {
			throw new IllegalArgumentException("Score must be >=0");
		}
		if(stats == null) {
			throw new IllegalArgumentException("stats can't be null");
		}
	}
}
