package game.model.item;

import java.util.Objects;

public record Curse(String name, boolean[][] shape) implements Item {
  public Curse {
    Objects.requireNonNull(name);
    Objects.requireNonNull(shape);
  }
}
