package game.model.item;

public sealed interface Item permits Weapon {
  boolean[][] shape();
}
