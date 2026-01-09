package game.view.draw;

import java.util.Objects;

import game.model.item.Item;


public class GroundItemHitBox {
	private final Item item;
	private final int x;
	private final int y;
	
	public GroundItemHitBox(Item item, int x, int y) {
		Objects.requireNonNull(item);
		if(x <0 || y <0 ) {
			throw new IllegalArgumentException("x and y must be grater than 0");
		}
		this.item = item;
		this.x = x;
		this.y = y;
	}
	
	public Item item() {
		return item;
	}
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
}
