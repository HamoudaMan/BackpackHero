package game.zen.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DrawCombatBoutons {
	public int attackX, attackY, attackW,attackH;
	public int blockX, blockY, blockW,blockH;
	
	public DrawCombatBoutons() {
		//pas la peine de utiliser le constructeur car apres les valeur des btn peuvent changais dans le cas ou on recadre la fentre (a tester)
	}
	
	public void render(Graphics2D g, int screenWidth, int screenHeight, int damage, int block) {
		var zoneY = screenHeight-50;
		var btnW = screenWidth/8;
		var btnH = 30;
	  attackX = screenWidth/4 - btnW/2;
		attackY = zoneY;
		attackW = btnW;
		attackH = btnH;
		blockX = 3*screenWidth/4 - btnW/2;
		blockY = zoneY;
		blockW = btnW;
		blockH=btnH;
		
		
		var blockX = 3* screenWidth/4 -btnW/2;
		
		//btn d'attack
		g.setColor(Color.RED);
		g.fillRect(attackX, zoneY, btnW, btnH);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Attack +"+damage, attackX+20, zoneY+20);
		//btn block
		g.setColor(Color.BLUE);
		g.fillRect(blockX, zoneY, btnW, btnH);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Blocking +"+block, blockX+20, zoneY+20);
	
	}
	
	public boolean clickAttack(int mouseX, int mouseY) {
		return mouseX >= attackX && mouseX <= attackX + attackW && mouseY>= attackY && mouseY<= attackY +attackH;
	}
	
	public boolean clickBLock(int mouseX, int mouseY) {
		return mouseX >=blockX && mouseX <= blockX + blockW && mouseY>= blockY && mouseY<= blockY +blockH;
	}
}
