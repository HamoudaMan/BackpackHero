package game.model.item;

public sealed interface Item permits Weapon, Curse, Gold, Consumables , Shield{
  boolean[][] shape();
}
