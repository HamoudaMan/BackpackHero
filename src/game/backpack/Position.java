package game.backpack;

public record Position(int row, int col) {
	public Position{
		if(row <0 || col<0) {
			throw new IllegalArgumentException("Position cant be neagtive");
		}
	}
	public Position add(Position other) {
		return new Position(this.row + other.row, this.col +other.col);
	}
}
