package game.dungeon;

public record Coord(int row, int col) {
	
	public Coord {
		/*
		if(row < 0 || col<0) {
			throw new IllegalArgumentException("col and row must be positive");
		}
		*/
	}
	
}
