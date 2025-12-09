package game.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.*;
import game.hero.Hero;
import game.interaction.Combat;
import game.interaction.CombatResult;
//import game.items.Item;

public class EnemyRoom implements Room{
	private final List<Enemy> enemiesList;
	
	public EnemyRoom(List<Enemy> enemiesList){
		Objects.requireNonNull(enemiesList);
		this.enemiesList = new ArrayList<>(enemiesList);
	}
	
	public List<Enemy> enemiesList() {
		return List.copyOf(enemiesList);
	}
	@Override
	public void enter(Hero hero) {
		Objects.requireNonNull(hero);
		IO.println(description());
		IO.println("enemies in the room :");
		IO.println(this);
		CombatResult result = Combat.startCombat(hero, enemiesList);
		
		if(result == CombatResult.LOSE) {
			IO.println("The hero is dead");
			return;
		}else if(result == CombatResult.WIN ) {
			IO.println("All the enemies have been defeated");
			enemiesList.clear();//on vide la liste des ennemies en cas de victoire 
		}
	}
	
	public void interact(Hero hero, Scanner input) {
		Objects.requireNonNull(hero);
		IO.println("FIGHT IS STARTING ... ");
		enter(hero);
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
			sb.append(enn.name()).append(" : ").append(enn.health()).append(" / ").append(enn.maxHealth()).append("\n");
		}
		return sb.toString();
	}

}
