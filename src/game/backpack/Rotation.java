package game.backpack;

public enum Rotation {
	DEGREES_0,
	DEGREES_90,
	DEGREES_180,
	DEGREES_270;
	
	public Rotation rotate() {
		return switch(this) {
		case DEGREES_0 ->DEGREES_90 ;
		case DEGREES_90 -> DEGREES_180;
		case DEGREES_180-> DEGREES_270;
		case DEGREES_270-> DEGREES_0;
		};
	}
}
