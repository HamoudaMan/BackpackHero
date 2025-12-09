package game.ASCII;

import java.util.Objects;

import game.items.Item;
//import game.items.MagicBackPack;

public class PrintBackPack {
	
	public static void PrintMagicBackPack(Item[][] listeItem) {
		
		IO.println("==========Magic Back Pack=========");
		IO.println();
		for( var r = 0; r< listeItem.length ; r++) {
			StringBuilder sbRow = new StringBuilder();
			
			for(var c = 0; c< listeItem[r].length; c++) {
				Item it = listeItem[r][c];
				if(it == null) {
					sbRow.append("[  ]");//cas ou case vide 
				}else {
					sbRow.append("[" +abbreviation(it.name()) + "]");
				}
			}
			IO.println(sbRow.toString());
		}
		IO.println();
	}
	
	private static String abbreviation(String name) {
		Objects.requireNonNull(name);
		return switch(name) {
		case "Wooden Sword" -> "WS";
		case "Rough Buckler" -> "RB";
		case "Magic Wand" -> "MW";
		default -> defaultAbbreviation(name);
		
		};
	}
	private static String defaultAbbreviation(String name) {
		if(name.length() <= 2){
			return name.toUpperCase();//cas ou le name de l'item est juste sur 1 ou 2 lettres 
			
		}
		return name.substring(0, 2).toUpperCase();
	}
}
