package game.model.item;

import java.util.Objects;

public record Weapon(String name, int energyCost, int damage, boolean[][] shape) implements Item {
  
  public Weapon {
    Objects.requireNonNull(name);
    Objects.requireNonNull(shape);
    if(energyCost < 0) {
      throw new IllegalArgumentException("energyCost must be > 0");
    }
    if(damage < 0) {
      throw new IllegalArgumentException("damage must be > 0");
    }
  }
}
