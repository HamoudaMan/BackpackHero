package game.model.representation;

public record Coord(int x, int y) {
  public Coord {
    if(x < 0) {
      throw new IllegalArgumentException("Coord x must be >= 0");
    }
    if(y < 0) {
      throw new IllegalArgumentException("Coord y must be >= 0");
    }
  }
}
