package game.backpack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import game.items.Item;

public class MagicBackPack {
	//MAX dimension of the backpack
	private static final int MAX_ROWS = 5;
	private static final int MAX_COLS = 7;

	private final boolean[][] availbleCells;//unlcok new cells when lvl up 
	private Item[][] stuff;
	private final List<Item> items;//to store items in the backpack

	/**
	 * the constructor create a backpack with 3*3 cases in the center at the begining
	 * 
	 * stuff -> for the items int the backpack grid 
	 * availbleCells->to unlock cells as we advance in the game
	 * items->a list know which items are in the backpack
	 */
	public MagicBackPack() {
		this.stuff = new Item[MAX_ROWS][MAX_COLS];
		this.availbleCells = new boolean[MAX_ROWS][MAX_COLS];
		this.items = new ArrayList<>();
		//3*3 unlocked cells in the center (inital) 
		var startRow = 2;
		var startCol = 1;
		for(var r = startRow; r<startRow+3; r++) {
			for(var c = startCol; c<startCol+3; c++) {
				availbleCells[r][c] = true;
			}
		}
	}
	
/*
	public boolean canPlace(Item item, Position pos, Rotation rot) {
		Objects.requireNonNull(item);
		Objects.requireNonNull(pos);
		Objects.requireNonNull(rot);
		Set<Position>  cells = item.shape().getAbsPositions(pos, rot);
		for(var cell: cells) {
			var row = cell.row();
			var col = cell.col();
			
			if(row<0 || row>= MAX_ROWS|| col<0 || col>=MAX_COLS) {
				return false;
			}
			if(!availbleCells[row][col]) {
				return false;
			}
			if(stuff[row][col] != null && stuff[row][col] != item) {
				return false;
			}
		}
		return true;
		
	}
	*/
	//place(
	//remove(
	//add
	//isCellavailble
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
				//System.out.println("Testing cell " + r + "," + c + " => " + stuff[r][c]);

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
				//System.out.println("TEST fit " + item.name() + " at " + row + "," + col);

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
				if( (stuff[r][c]).equals(item) && stuff[r][c].equals(item)) {
					stuff[r][c] = null;
				}
			}
		}
	}
	
	public Item[][] stuff(){
		return stuff;
	}
	@Override
	public String toString() {
		var sb = new StringBuilder();
		var i = 1;
		for(var obj :stuff) {
			sb.append(i + " : ").append(obj);
			i++;
		}
		return sb.toString();
	}
}
