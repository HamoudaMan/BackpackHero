package game.model.item;

import java.util.Objects;

import game.model.representation.Coord;

public record ItemOnScreen(Item item, Coord coord) {
  public ItemOnScreen {
    Objects.requireNonNull(item);
    Objects.requireNonNull(coord);
  }
  
  public ItemOnScreen(ItemOnScreen itemOnScreen, Coord coord) {
    this(itemOnScreen.item, coord);
  }
}