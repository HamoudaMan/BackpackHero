package game;

import game.controller.GameLoop;
import game.controller.MovmentController;
import game.controller.InteractionController;
import game.dungeon.Dungeon;
import game.hero.Hero;
import game.items.weapons.WoodenSword;

public class Main {
    public static void main(String[] args) {

        IO.println("=== Welcome to Backpack Hero ===");
        IO.print("Enter your hero's name: ");
        String name = IO.readln().trim();
        if (name.isEmpty()) {
        	name = "Hero";
        }

        Hero hero = new Hero(name);
        hero.addGold(10);
        Dungeon dungeon = new Dungeon();
        hero.backPack().add(new WoodenSword());

       // hero.addToBackPack(new WoodenSword());
        hero.equipWeapon(new WoodenSword() );
        MovmentController movement = new MovmentController(hero, dungeon);
        InteractionController interaction = new InteractionController(hero, dungeon, new java.util.Scanner(System.in));
        
        GameLoop game = new GameLoop(hero, dungeon);

        game.start();
       
    }
}
