package game.model.item;

public sealed interface Item permits Weapon, Curse, Gold {
  boolean[][] shape();
}
