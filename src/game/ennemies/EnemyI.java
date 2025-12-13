package game.ennemies;



import game.hero.Hero;
//create an enemy constructor 
public interface EnemyI {
	String name();
	int health();
	int maxHealth();
	int protection();
	boolean isDead();
	Action nextAction();
	
	void resetProtection();
	void attack(Hero hero);//attaque le hero
	void takeDamage(int damage);
	void buffProtection();//augmente sa protection 
	void announceAction();//annonce de ses futurs actions du tour qui suit 
	void doAction(Hero hero);//choxi random de l'action de l'ennemi (buff protection / attaque)
	
}
