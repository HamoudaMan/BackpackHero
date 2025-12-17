package game.items.armor;

import java.util.Objects;

import game.ennemies.Enemy;
import game.hero.Hero;
import game.items.Item;

public class RoughBuckler implements Item, Armors{
	
	private final String name = "Rough Buckler";
	private final int block = 7;
	private final int energyCost = 1;
	private int width = 1;
	private int height = 3;
	@Override
	public boolean canUse(Hero hero) {
		Objects.requireNonNull(hero);
		return hero.energy()>= energyCost;
	}
	
	@Override
	public void use(Hero hero, Enemy enemy) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		
		if(!canUse(hero)) {
			IO.println("Not enough enerfy to use "+ name());
			return;
		}
		hero.consumeEnergy(energyCost);
		hero.addProtection(block);
		IO.println(name() + " : + "+block + " protection");
		
	}
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
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
	public int block() {
		return block;
	}

	@Override
	public int energyCost() {
		// TODO Auto-generated method stub
		return energyCost;
	}

	public String spriteKey() {
		return "roughbuckler";
	}
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return" roughBuckler test ";
	}

	@Override
	public int turnUsable() {
		// TODO Auto-generated method stub
		return 1000;
	}


}
