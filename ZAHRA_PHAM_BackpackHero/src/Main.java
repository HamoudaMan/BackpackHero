import java.util.Scanner;
import game.ennemies.*;
import game.hero.Hero;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Hero hero = new Hero("Mongi");
        SmallRatWolf ennemi = new SmallRatWolf();   // ou RatWolf

        int tour = 1;
        boolean game = true;

        System.out.println("=== Début du combat contre " + ennemi.name() + " ! ===\n");

        while (game) {

            System.out.println("\n--- TOUR " + tour + " ---");

            //etat avant annonce
            System.out.println("Héros : " + hero.health() + " PV | Protection : " + hero.protection());
            System.out.println(ennemi.name() + " : " + ennemi.health() + " PV | Protection : " + ennemi.protection());
            
            //annonce de l’ennemi
            ennemi.announceAction();
            System.out.println("L'ennemi prépare : " + ennemi.nextAction());

            // action du hero
            System.out.print("\nVoulez-vous attaquer ou bloquer ? (a / b) : ");
            char choix = sc.nextLine().trim().toLowerCase().charAt(0);

            if (choix == 'a') {
                System.out.println(" Le héro attaque !");
                hero.attack(ennemi);

            } else if (choix == 'b') {
                System.out.println("  Le héros se protège !");
                hero.block();
            }

            if (ennemi.isDead()) {
                System.out.println("\n Victoire ! L’ennemi est vaincu !");
                break;
            }

            //action de l’ennemi
            System.out.println("\n->  L'ennemi agit...");
            ennemi.doAction(hero);

            if (hero.heroDead()) {
                System.out.println("\n Le héros est mort… Game Over.");
                break;
            }

            tour++;
        }

        sc.close();
    }
}
