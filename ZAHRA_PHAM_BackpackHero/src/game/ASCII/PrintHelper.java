package game.ASCII;

public class PrintHelper {
	
	public static void PrintComandes() {
		IO.println("======= Commandes =======");
		IO.println("Movements : (z, q, s, d)");
		IO.println("z -> up");
		IO.println("q -> left");
		IO.println("s -> down");
	  IO.println("d -> right");
  	IO.println("e -> interact\n");
  	IO.println("---------------------------------");
  	
	}
	public static void legende() {
		IO.println("* = Hero");
		IO.println("E = Enemy Room");
		IO.println("T = Treasure Room");
		IO.println("M = Merchant Room");
		IO.println("H = Healer");
		IO.println("X = Exit Room");
		IO.println(". = Corridor");
		IO.println("----------------------");
	}
}
