package game.zen.view;

import java.awt.Color;
import java.util.Objects;
import java.util.function.Consumer;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;
/*
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
}*/

public class Window{
	
	public void open(Color bgColor, Consumer<ApplicationContext> appCode) {// j'ai fait comme dans la doc exactement 
		Objects.requireNonNull(bgColor);
		Objects.requireNonNull(appCode);
		Application.run(bgColor, appCode);
		
	}
	
	public void open( Consumer<ApplicationContext> appCode) {
		open(Color.WHITE, appCode);
	}
}