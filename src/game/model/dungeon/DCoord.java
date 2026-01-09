package game.model.dungeon;

public record DCoord(int row, int col) {
	
	public DCoord {
		/*
		if(row < 0 || col<0) {
			throw new IllegalArgumentException("col and row must be positive");
		}
		*/
	}
	public DCoord sum(DCoord other) {
		return new DCoord(this.row + other.row() , this.col +other.row());
	}
	
}
