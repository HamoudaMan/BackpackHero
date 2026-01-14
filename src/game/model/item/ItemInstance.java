package game.model.item;

import java.util.List;
import java.util.Objects;

import game.model.representation.Coord;
import game.model.representation.StateRotation;

public record ItemInstance(Item item, List<Coord> coord, StateRotation rotation) {
  public ItemInstance {
    Objects.requireNonNull(item);
    Objects.requireNonNull(coord);
    Objects.requireNonNull(rotation);
    if(coord.isEmpty()) {
      throw new IllegalArgumentException("List<coord> coord can't be empty ");
    }
  }
}
