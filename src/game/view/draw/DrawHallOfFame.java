package game.view.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

import game.model.hallOfFame.GameResult;
import game.model.hallOfFame.GameStats;
import game.model.hallOfFame.HallOfFame;

/**
 * Draws the Hall of Fame in the main menu 
 */
public class DrawHallOfFame {

	
	public void render(Graphics2D g, HallOfFame hallOfFame, int screenWidth, int startY, int endY) {
		Objects.requireNonNull(hallOfFame);
		var startX = screenWidth / 2 - 200; 
    var y = startY + 80;
    g.setColor(Color.WHITE);
    g.setFont(new Font("Serif", Font.BOLD, 40));
    g.drawString("Hall of Fame", startX, y);
    //g.setColor(Color.MAGENTA);
    g.setFont(new Font("Serif", Font.PLAIN, 25));
    y += 50;
    int place = 1;
    for (GameResult res : hallOfFame.results()) {
        GameStats stats = res.stats();
        g.drawString(place + ".", startX, y);
        g.drawString(res.score() + " pts", startX + 40, y);
        g.drawString("HP " + stats.maxHp(), startX + 140, y);
        g.drawString("Lvl " + stats.heroLevel(), startX + 220, y);
        g.drawString("E " + stats.enemiesDefeated(), startX + 300, y);
        g.drawString("F " + stats.floorsExplored(), startX + 360, y);

        y += 45;
        place++;
    }
		
	}
}
