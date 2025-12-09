package game.ASCII;

import game.dungeon.Floor;
import game.hero.Hero;

public class PrintDungeon {
	public static void printD(Floor floor, Hero hero) {
		IO.println("===== Explore the Dungeon =====");
		PrintHero.PrintH(hero);//affichage du hero 
		PrintBackPack.PrintMagicBackPack(hero.backPack().stuff());//affichage du sac a dos 
		PrintHelper.PrintComandes();//affichage des commandes 
		PrintFloor.printF(floor, floor.postionHero());//affichage de l'etage en cours 
		PrintHelper.legende();
		//IO.println("---------------------------------");
	}
}
