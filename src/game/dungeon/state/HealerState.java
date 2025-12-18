package game.dungeon.state;

public class HealerState {
	private boolean used ;
	
	public HealerState() {
		this.used = false;//at the begining the healer room is not used yet 
	}
	public boolean isUsed() {
		return used;
	}
	
	public void use() {
		if(used) {
			return;
		}
		used = true;//dispaly a message like : "Healer is exausted "
	}
}
