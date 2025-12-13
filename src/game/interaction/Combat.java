package game.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import game.ennemies.EnemyI;
import game.hero.Hero;

public class Combat {
	private final Hero hero;
	private final List<EnemyI> enemies;
	private final UICombat ui;
	private final ItemManager items;
	private final EnemyController enemyController;
	
	public Combat(Hero hero,List<EnemyI> enemies,UICombat ui, ItemManager items, EnemyController enemyController) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemies);
		Objects.requireNonNull(ui);
		Objects.requireNonNull(enemyController);
		
		this.hero = hero;
		this.enemies = enemies;
		this.ui = ui;
		this.items = items;
		this.enemyController = enemyController;//juste pour la modularite
	}
	public CombatResult startCombat() {
		while(!enemies.isEmpty()) {
			EnemyI enemy = enemies.get(0);
			ui.printTurn(hero, enemy);
			enemy.announceAction();
			//tour hero
			items.heroTurn(hero, enemy);
			
			if(enemy.isDead()) {
				ui.printEnemyDead(enemy);
				enemies.remove(0);//si l'ennemie est mort on le suprr de la liste
				continue;
			}
			//tour de l'enn
			enemyController.exe(enemy, hero);
			hero.resetProtection();//fin du tour donc block revien a 0;
			
			if(hero.heroDead()) {
				return CombatResult.LOSE;
			}
		}

	return CombatResult.WIN;
	}
	/*
	public CombatResult startCombat(Hero hero, List<EnemyI> enemiesList) {
		List<EnemyI> enemies = new ArrayList<>(enemiesList);
		Scanner scanner = new Scanner(System.in);
		
		while(!enemies.isEmpty()) {//tant qu'il ya des ennemies 
			hero.resetEnergy();//a chaque tour on reset l'energie a 3 
			var enemy = enemies.get(0);
			enemy.resetProtection();
			IO.println("===== NEW TURN =====");
			IO.println(hero.name() + " HP : " +hero.health() );
			IO.println(enemy.name() + " HP : " +enemy.health() );
			IO.println("------------");
			
			
			enemy.announceAction();
			String action = "";
			while(!action.equals("a") && !action.equals("b") ) {
				IO.println("Choose Action : a-> attack b->block");
				action = scanner.nextLine().trim().toLowerCase();
			}
			if(action.equals("a")) {
				hero.attack(enemy);//hero attack en premier 
			}else {
				hero.block();
			}
			
			if(enemy.isDead()) {
				
				IO.println("enemie "+ enemy.name() +"is dead");
				enemies.remove(0);//si l'ennemie est mort on le suprr de la liste
				continue;
			}
			enemy.doAction(hero);//tour de l'ennemie, il execute l'acotion qu'il a annoncé 
			
			if(hero.heroDead()) {
				return CombatResult.LOSE;
			}
		}
		return CombatResult.WIN;
	}
	*/
}
