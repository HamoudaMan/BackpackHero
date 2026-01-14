package game.model.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * generate random enemies with increasing difficulty 
 */
public class EnemyGenerator {
	private final Random random = new Random();
	
	
	private int setEnemyCount(int floorLevel) {
		var roll = random.nextInt(100);
		if(floorLevel == 1) {
			if(roll<50) {//1 enemy
				return 1; 
			}else if(roll <85) {//2 enmies
				return 2;
			}
			return 3; //3 enemies 
		}else if(floorLevel == 2) {
			if(roll<30) {//1 enemy
				return 1; 
			}else if(roll <75) {//2 enmies
				return 2;
			}
			return 3; //3 enemies 
		}else {
			if(roll<20) {//1 enemy
				return 1; 
			}else if(roll <50) {//2 enmies
				return 2;
			}
			return 3; //3 enemies 
		}
		
	}
	/**
	 * select an enemy type 
	 * @param floorLevel
	 * @return
	 */
	private EnemyType selectEnemyType(int floorLevel) {
		var roll = random.nextInt(100);
		switch(floorLevel) {
		case 1 ->{
			if(roll<40) { return EnemyType.SMALL_RATWOLF;}
			if(roll<70) { return EnemyType.SLIME;}
			if(roll<90) { return EnemyType.LILBEE;}
			return EnemyType.RATWOLF;
			
		}
		case 2 ->{
			if(roll<20) { return EnemyType.SMALL_RATWOLF;}
			if(roll<40) { return EnemyType.SLIME;}
			if(roll<60) { return EnemyType.LILBEE;}
			if(roll<70) { return EnemyType.MUSKRAT_BRIGAND;}
			if(roll<80) { return EnemyType.FROG_SORCERER;}
			
			return EnemyType.LIVING_SHADOW;
		}
		case 3 ->{
			if(roll<20) { return EnemyType.RATWOLF;}
			if(roll<40) { return EnemyType.SLIME;}
			if(roll<60) { return EnemyType.MUSKRAT_BRIGAND;}
			if(roll<70) { return EnemyType.LILBEE;}
			if(roll<75) { return EnemyType.LIVING_SHADOW;}
			if(roll<85) { return EnemyType.FROG_SORCERER;}
			
			return EnemyType.BEE_QUEEN;
		}
		default ->{
			 EnemyType[] possibleTypes = {
           EnemyType.RATWOLF, EnemyType.SLIME, EnemyType.MUSKRAT_BRIGAND,
           EnemyType.FROG_SORCERER, EnemyType.LIVING_SHADOW, EnemyType.LILBEE, EnemyType.BEE_QUEEN
       };
       return possibleTypes[random.nextInt(possibleTypes.length)];
		 }
		}
	}
	
	public List<Enemy> generateEnemies(int floorLevel){
		var enemyCount = setEnemyCount(floorLevel);
		var enemies = new ArrayList<Enemy>();
		
		for(var i = 0;i< enemyCount; i++) {
			var type = selectEnemyType(floorLevel);
			enemies.add(EnemyFactory.create(type));
		}
		return enemies;
	}

	
/**
 * determine the number of enmies between 1 and 3 
 * @param floorLevel
 * @return
 */

}
