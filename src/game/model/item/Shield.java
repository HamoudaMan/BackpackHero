package game.model.item;

import java.util.Objects;

public record Shield(String name, int energyCost, int manaCost, int protection, boolean[][] shape) implements Item  {


	  
	  public Shield {
	    Objects.requireNonNull(name);
	    Objects.requireNonNull(shape);
	    if(energyCost < 0) {
	      throw new IllegalArgumentException("energyCost must be > 0");
	    }
	    if(manaCost < 0) {
	      throw new IllegalArgumentException("manaCost must be > 0");
	    }
	    if(protection < 0) {
	      throw new IllegalArgumentException("protection must be > 0");
	    }
	  }
}
