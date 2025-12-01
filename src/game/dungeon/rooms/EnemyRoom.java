package game.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.*;
import game.hero.Hero;

public class EnemyRoom implements Room{
	private final List<Enemy> enemiesList;
	
	public EnemyRoom(List<Enemy> enemiesList){
		this.enemiesList = new ArrayList<>(enemiesList);
	}
	
	public List<Enemy> enemiesList() {
		return enemiesList;
	}
	@Override
	public void enter(Hero hero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RoomType type() {
		return RoomType.ENEMY;
	}

	@Override
	public String description() {
		return "Be ready to fight fierce enemies.\n";
	}


	@Override
	public String toString() {
		var sb = new StringBuilder();
		for( var enn : enemiesList) {
			sb.append(enn.name()).append(" : ").append(enn.maxHealth()).append("/").append(enn.health());
		}
		return sb.toString();
	}

}
