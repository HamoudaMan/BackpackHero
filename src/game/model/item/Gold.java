package game.model.item;

public record Gold(int amount) implements Item {
	public Gold {
		if(amount < 0) {
			throw new IllegalArgumentException("Gold can't be negative");
		}
	}
	
	@Override
	public boolean[][] shape() {
		return new boolean[][] {{true}};
	}
	
}
