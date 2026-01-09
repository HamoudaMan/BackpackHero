package game.controller;

import game.model.dungeon.state.HealerState;
import game.model.hero.Hero;

public class HealerController {
	private static final int FULL_HEAL_COST = 20;
	private static final int SMALL_HEAL_COST = 7;
	private static final int SMALL_HEAL_AMOUNT = 10;
	
	/**
	 * if isUsed is false then can use return true 
	 * @param state to get the state of healerRoom , weather the heal was used or not
	 * @return boolean
	 */
	public boolean canUse(HealerState state) {
		return !state.isUsed();
	}
	
	/**
	 * heal a small amount of HP of the hero
	 * @param hero
	 * @param state : state of the HealerRoom
	 * @return a boolean
	 */
	public boolean healSmall(Hero hero,HealerState state) {
		if(state.isUsed() || !hero.canPay(SMALL_HEAL_COST) ) {
			return false;
		}
			hero.pay(SMALL_HEAL_AMOUNT);
			hero.stats().heal(SMALL_HEAL_AMOUNT);
			state.use();//set the treasure room as used 
			return true;
	
	}
	/**
	 * heal all hp of the hero
	 * @param hero
	 * @param state : state of the HealerRoom
	 * @return a boolean
	 */
	public boolean healFull(Hero hero,HealerState state) {
		if(state.isUsed() || !hero.canPay(FULL_HEAL_COST) ) {
			return false;
		}
		hero.pay(FULL_HEAL_COST);
		hero.stats().heal(hero.stats().maxHealth());
		state.use();
		return true;
	}
}
