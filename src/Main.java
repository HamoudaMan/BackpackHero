import java.util.Scanner;
import game.ennemies.*;
import game.hero.Hero;
import game.state.GameState;

public class Main {
  public static void main(String[] args) {
    var game = new GameState();
    game.start();
  }
}
