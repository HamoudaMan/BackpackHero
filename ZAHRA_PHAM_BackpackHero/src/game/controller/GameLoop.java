package game.controller;

import java.util.Scanner;

import game.ASCII.PrintDungeon;
import game.dungeon.Dungeon;
import game.hero.Hero;

public class GameLoop {
		private final Scanner input = new Scanner(System.in);
		private final Hero hero;
		private Dungeon dungeon;
		private final MovmentController movment;
		private final InteractionController interaction;
		
		
		public GameLoop(Hero hero, Dungeon dungeon, MovmentController movment,InteractionController interaction) {
			this.hero = hero;
			this.dungeon = dungeon;
			this.movment  = movment;
			this.interaction = interaction;

		}
		
		public void start() {
			IO.println("Welcome to BackPack Hero !");
			while(!hero.heroDead() && !dungeon.isCompleted()) {
				PrintDungeon.printD(dungeon.getCurrentFloor(), hero);
				
				String cmd = input.nextLine().trim().toLowerCase();
				switch(cmd) {
				case "z","q","s","d" -> movment.Move(cmd);
				case "e" -> interaction.handleInteraction();
				default ->IO.println("Invalid commande");
				}
			}
		
			if(hero.heroDead()) {
				IO.println("GAME OVER");
			}else {
				IO.println("CONGRATS YOU WON!");
			}
		}
}
