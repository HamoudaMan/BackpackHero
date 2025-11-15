package game.ennemies;



import game.hero.Hero;

public interface Enemy {
	String name();
	int health();
	int maxHealth();
	int protection();
	boolean isAlive();
	
	
	void attack(Hero hero);//attaque le hero
	void takeDamage(int damage);
	void buffProtection();//augmente sa protection 
	void announceAction();//annonce de ses futurs actions du tour qui suit 
	void chooseAction();//choxi random de l'action de l'ennemi (buff protection / attaque)
	
}
