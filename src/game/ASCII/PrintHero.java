package game.ASCII;

import game.hero.Hero;

public class PrintHero {
	
	public static void PrintH(Hero hero) {
		IO.println("========= Hero =========");
		IO.println("Name : " +hero.name());
		IO.println("HP   : " + hero.health() +" / "+ hero.maxHealth());
		IO.println("Mana : " + hero.mana());
		IO.println("Gold : "+ hero.gold()+"\n");
	}
}
