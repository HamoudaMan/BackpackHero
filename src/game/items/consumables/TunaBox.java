package game.items.consumables;

import java.util.Objects;

import game.ennemies.Enemy;
import game.hero.Hero;
import game.items.Item;

public class TunaBox implements Item, Consumables{
	private final String name = "Tuna Box";
	private boolean consumed = false;
	private int height = 1;
	private int width = 1;
	
	@Override
	public String name() {
		return name;
	}
	@Override
	public boolean canUse(Hero hero) {	
		Objects.requireNonNull(hero);
		return !consumed;
	}
	@Override
	public void use(Hero hero, Enemy enemy) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		if(!canUse(hero)) {
			IO.println("Tuna box empty");
			return;
		}
		hero.restoreEnergy(1);
		consumed = true;
		IO.println("You ate the tuna box (+1 energy)");
		
	}
	@Override
	public int energyCost() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int width() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void rotate() {
		var tmp = height;
		height = width;
		width = tmp;
		
	}
	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return consumed;
	}
	public void consume() {
		consumed = true;
	}
	
	
}
