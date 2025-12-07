package game.interaction;

import java.util.Objects;
import java.util.Scanner;

import game.ennemies.Enemy;
import game.hero.Hero;

public class UICombat {
	private final Scanner input;
	
	public UICombat(Scanner input) {
		this.input = input;
	}
	
	public void printTurn(Hero hero, Enemy enemy) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		IO.println("========= New Turn =========");
		IO.println(hero.name() +" HP: "+hero.health() +"/ "+hero.maxHealth());
		IO.println(enemy.name() +" HP: "+enemy.health() + "/ "+enemy.maxHealth());
		IO.println("----------------------------");
		
	}
	
	public void printEnemyDead(Enemy enemy) {
		Objects.requireNonNull(enemy);
		IO.println(enemy.name() + " is dead!");
	}
	
	public int askAction() {
		IO.println("\n----- Choose your action -----");
		IO.println("1 - use item from your back pack");
		IO.println("2 - End your turn");
		
		return Integer.parseInt(input.nextLine().trim());
	}
}
