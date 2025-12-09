package game.items;


public class MagicBackPack {
	//private Weapon weaponsEquiped;
	private static final int ROWS = 3;
	private static final int COLS = 5;
	
	private Item[][] stuff;
	
	public MagicBackPack() {
		this.stuff = new Item[ROWS][COLS];
	}
	
	public boolean fitsInBackPack(Item item,int row, int col ) {
		var w = item.width();
		var h = item.height();
		//on check si ca depasse du sac 
		if(row + h > ROWS || col + w > COLS ) {
			return false;
		}
		//on check si il n'y pas un item au dessu de l'autre 
		for( var r = row; r< row+h; r++) {
			for(var c = col; c < col+w; c++ ) {
				if(stuff[r][c] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void placeInBackPack(Item item, int row, int col) {
		var w = item.width();
		var h = item.height();
		
		//pour l'affichage y'aura une boucle qui va check chaque case  
		for( var r = row; r< row+h; r++) {
			for(var c = col; c < col+w; c++ ) {
				stuff[r][c] = item;
			}
		}
	}
	
	public boolean add(Item item) {
		//cas ou y a pas besoin de faire une rota sur l'item 
		for(var row = 0; row < ROWS; row ++) {
			for(var col = 0; col <COLS; col++) {
				if(fitsInBackPack(item, row, col)) {
					placeInBackPack(item, row, col);
					return true;
				}
			}
		}
		//deuxieme cas de figure on essaye en faisant une rotation
		item.rotate();
		for(var row = 0; row < ROWS; row ++) {
			for(var col = 0; col <COLS; col++) {
				if(fitsInBackPack(item, row, col)) {
					placeInBackPack(item, row, col);
					return true;
				}
			}
		}
		item.rotate();// on le remet a sa postio nd'origine 
		return false;//impossible de le fit dans le sac 
	}
	
	public void remove(Item item) {//enleve l'item du sac a dos (peut etre refere une versio nqui renvoi un bool 
		for(var r = 0; r<ROWS; r++) {
			for(var c = 0; c< COLS; c++) {
				if( (stuff[r][c]).equals(item)) {
					stuff[r][c] = null;
				}
			}
		}
	}
	
	public Item[][] stuff(){
		return stuff;
	}
}
