package game.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.*;


//import game.items.Item;

public class EnemyRoom implements Room{
	private final List<Enemy> enemiesList;
	
	public EnemyRoom(List<Enemy> enemies){
		Objects.requireNonNull(enemies);
		this.enemiesList = new ArrayList<>(enemies);
	}
	
	public List<Enemy> enemiesList() {
		return enemiesList;
	}/*
	@Override
	public void enter(Hero hero) {
		Objects.requireNonNull(hero);
		IO.println(description());
		IO.println("enemies in the room :");
		IO.println(this);
		if(enemiesList.isEmpty()){
			IO.print("you already did this room, all ennemies have been defeated");
			return;
		}
		CombatResult result = Combat.startCombat(hero, enemiesList);
		
		if(result == CombatResult.LOSE) {
			IO.println("The hero is dead");
			return;
		}else if(result == CombatResult.WIN ) {
			IO.println("All the enemies have been defeated");
			enemiesList.clear();//on vide la liste des ennemies en cas de victoire 
		}
	}*/
	


	@Override
	public RoomType type() {
		return RoomType.ENEMY;
	}

	@Override
	public String description() {
		return "Be ready to fight fierce enemies.\n";
	}





}
