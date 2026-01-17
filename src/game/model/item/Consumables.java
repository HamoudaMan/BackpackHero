package game.model.item;

import java.util.Objects;

public record Consumables(String name, int energyCost, int manaCost, int heal, boolean[][] shape) implements Item  {


	  
	  public Consumables {
	    Objects.requireNonNull(name);
	    Objects.requireNonNull(shape);
	    if(energyCost < 0) {
	      throw new IllegalArgumentException("energyCost must be > 0");
	    }
	    if(manaCost < 0) {
	      throw new IllegalArgumentException("manaCost must be > 0");
	    }
	    if(heal < 0) {
	      throw new IllegalArgumentException("heal must be > 0");
	    }
	  }
}
