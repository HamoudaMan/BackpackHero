package game.ASCII;

import game.hero.Hero;

public class PrintHealer {
	public static void printH(Hero hero, int healAmount, int cost) {
		IO.println("========= Healer Room =========");
		IO.println("You can heal for : " + healAmount + " HP");
		IO.println("Cost             : " + cost + " gold\n");
		IO.println("---------------------------------");
		IO.println("Hero HP   : " + hero.health() +" / "+ hero.maxHealth());
		IO.println("Hero Gold : " + hero.gold());
		IO.println("---------------------------------");
		IO.println("Do you want to heal? (y/n)\n\n");
	}
}
