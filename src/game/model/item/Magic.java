package game.model.item;

import java.awt.Shape;
import java.util.Objects;

public record Magic(String name, boolean[][] shape  ) implements Item {
	public Magic{
		Objects.requireNonNull(name);
	}
}
