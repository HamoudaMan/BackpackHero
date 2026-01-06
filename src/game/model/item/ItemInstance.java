package game.model.item;

import java.util.List;
import java.util.Objects;

import game.model.representation.Coord;

public record ItemInstance(Item item, List<Coord> coord) {
  public ItemInstance {
    Objects.requireNonNull(item);
    Objects.requireNonNull(coord);
    if(coord.isEmpty()) {
      throw new IllegalArgumentException("List<coord> coord can't be empty ");
    }
  }
}
