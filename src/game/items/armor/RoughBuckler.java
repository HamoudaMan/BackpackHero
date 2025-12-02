package game.items.armor;

import game.items.Item;

public class RoughBuckler implements Item, Armors{
	
	private final String name = "Rough Buckler";
	private final int block = 7;
	private final int cost = 1;
	@Override
	public String name() {
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
		// TODO Auto-generated method stub
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
