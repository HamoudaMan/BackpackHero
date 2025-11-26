package game.items.weapons;

import game.items.Item;

public class WoodenSword implements Item, Weapon{
	
	private final String name = "Wooden Sword";
	private final int damage = 7;
	private int turnUsable = 100;//une grande valeur pour dire illimié
	private boolean usable = true;
	private int width = 1;
	private int height = 3;
	private final int manaPrice = 1;	
	
	@Override
	public int damage() {
		return damage;
	}
	public int manaPrice() {
		return manaPrice;
	}

	@Override
	public int turnUsable() {
		return turnUsable;
	}

	@Override
	public Boolean usable() {
		return usable;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public int width() {
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
	public Weapon use() {
		return null;
		
	}

}
