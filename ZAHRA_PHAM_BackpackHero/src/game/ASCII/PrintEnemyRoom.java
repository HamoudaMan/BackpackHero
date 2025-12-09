package game.ASCII;

import java.util.List;

import game.ennemies.Enemy;
import game.hero.Hero;

public class PrintEnemyRoom {
	 public static void printE(Hero hero, List<Enemy> enemies) {
		 
		 IO.println("======== Enemy Room ========");
		 IO.println("Hero HP   : " + hero.health() +" / "+ hero.maxHealth());
		 IO.println("Hero Protection : " + hero.protection() +" \n\n");
		 
		 IO.println("Enemies");
		 var i = 0;
		 for( Enemy e: enemies) {
			 IO.println(" " + i + ") " + e.name() + "    " + e.health() + " / " + e.maxHealth());
			 i++;
			 
		 }
		 IO.println("---------------------------------");
		 IO.println("\n");
		 IO.println("Choose Acvtion");
		 IO.println("a -> Attack");
		 IO.println("b ->block");
		 IO.println("e -> Use \n\n");
	 }
}
