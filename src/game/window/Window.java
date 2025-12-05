package game.window;

import java.awt.Color;
import java.util.Objects;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;

public class Window {
  private ApplicationContext windowOfGame;
  
  public ApplicationContext GetContext() {
    return windowOfGame;
  }
  
  private void addContextToWindow(ApplicationContext context) {
    Objects.requireNonNull(context);
    windowOfGame = context;
  }
  
  public void OpenWindow() {
    Application.run(Color.WHITE, t -> addContextToWindow(t));
  }
  
  public void CloseAll() {
    windowOfGame.dispose();
    System.exit(0);
  }
}
