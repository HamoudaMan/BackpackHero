package game.model.item;

public sealed interface Item permits Weapon, Curse {
  boolean[][] shape();
}
