package game.view;

import java.awt.Color;
import java.util.Objects;
import java.util.function.Consumer;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;


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