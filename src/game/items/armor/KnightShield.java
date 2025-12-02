package game.items.armor;

import game.items.Item;

public class KnightShield implements Item, Armors{
	private final String name = "Knight's Shield";
	private final int block = 8;
	private final int cost = 1;
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int block() {
		return block;
	}

	@Override
	public Boolean isUsable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int cost() {
		// TODO Auto-generated method stub
		return cost;
	}
	
}
