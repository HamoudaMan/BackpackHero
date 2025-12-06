package game;

import game.controller.GameLoop;
import game.controller.MovmentController;
import game.controller.InteractionController;
import game.dungeon.Dungeon;
import game.hero.Hero;

public class Main {
    public static void main(String[] args) {

        IO.println("=== Welcome to Backpack Hero ===");
        IO.print("Enter your hero's name: ");
        String name = IO.readln().trim();
        if (name.isEmpty()) name = "Hero";

        Hero hero = new Hero(name);
        Dungeon dungeon = new Dungeon();
        MovmentController movement = new MovmentController(hero, dungeon);
        InteractionController interaction = new InteractionController(hero, dungeon, new java.util.Scanner(System.in));
        
        GameLoop game = new GameLoop(hero, dungeon, movement, interaction);

        game.start();
    }
}
