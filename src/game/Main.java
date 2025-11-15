package game;
import java.util.Scanner;

import game.ennemies.RatWolf;
import game.hero.Hero;
public class Main {

	public static void main(String[] args) {
		var game = true;
		var tour = 0;
		Scanner sc = new Scanner(System.in);
		Hero hero = new Hero("mongi");
		RatWolf ratLoup = new RatWolf();
		while(game) {
			IO.println("debut du tour "+tour);
			
		    System.out.print("Voulez-vous attaquer ? (y/n) : ");
		    char choix = sc.nextLine().trim().toLowerCase().charAt(0);

		    if (choix == 'y') {
		        System.out.println("attaque de l'hero en cours  !");
		        hero.attack(ratLoup);
		        IO.println("attaque de l'ennemi ");
		        ratLoup.attack(hero);
		    } else if (choix == 'n') {
		        System.out.println("Fin du jeu.");
		        game = false;   // ⬅️ On sort de la boucle
		    } else {
		        System.out.println("Entrée invalide !");
		    }
			
		}

	}

}
