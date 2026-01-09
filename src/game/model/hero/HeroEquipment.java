package game.model.hero;

import game.model.item.Weapon;

public  class HeroEquipment {
	
	private Weapon weapon; 
		
		public void equip(Weapon weapon){
			this.weapon = weapon;
		}
		
		public Weapon weapon() {
			return weapon;
		}
		
		public boolean hasWeapon() {
			return weapon != null;
		}
	
}
