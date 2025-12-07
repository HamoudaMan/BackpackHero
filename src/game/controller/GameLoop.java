package game.controller;

import java.util.Scanner;

import game.ASCII.PrintDungeon;
import game.dungeon.Dungeon;
import game.dungeon.Floor;
import game.hero.Hero;

public class GameLoop {
		private final Scanner input = new Scanner(System.in);
		private final Hero hero;
		private Dungeon dungeon;
		private final MovmentController movment;
		private final InteractionController interaction;
		
		
		public GameLoop(Hero hero, Dungeon dungeon) {
			this.hero = hero;
			this.dungeon = dungeon;
			this.movment  = new MovmentController(hero, dungeon);
			this.interaction = new InteractionController(hero, dungeon, input);

		}
		
		public void start() {
			IO.println("Welcome to BackPack Hero !");
			while(!hero.heroDead() && !dungeon.isCompleted()) {
				Floor floor = dungeon.getCurrentFloor();
				PrintDungeon.printD(floor, hero);
				
				String cmd = input.nextLine().trim().toLowerCase();
				switch(cmd) {
				case "z","q","s","d" -> movment.Move(cmd);
				case "e" -> {var pos = floor.postionHero(); var room = floor.getRoomInfo(pos.row(), pos.col()); room.interact(hero, input);}
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
