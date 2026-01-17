package game.model.hallOfFame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Stores and loads the score in file halloffame.txt  
 */
public final class HallOfFameStorage {
	private static final Path PATH_FILE = Path.of("halloffame.txt");
	private static final String SEPARATOR = ";";
	
	
	
	/**
	 * converts a GameResult to string and puts in format 
	 * @param res
	 * @return
	 */
	private static String putInFormat(GameResult res) {
		GameStats stats = res.stats();
		return res.score()+ SEPARATOR + stats.maxHp() +SEPARATOR + stats.heroLevel() +SEPARATOR +
				stats.enemiesDefeated()+ SEPARATOR + stats.itemsValue() +SEPARATOR + stats.floorsExplored();
				
	}
	/**
	 * Saves the Hall of fame on the file 
	 * @param hallOfFame the hallOfFame to save
	 */
	public boolean save(HallOfFame hallOfFame) {
		Objects.requireNonNull(hallOfFame);
		//convert each GameResult into a line (again merci poo 8)
		List<String> lines = hallOfFame.results().stream()
												.map(HallOfFameStorage:: putInFormat)
												.toList();
		try {
			Files.write(PATH_FILE, lines);
			return true;
		}catch(IOException exception) {
			//throw new IllegalStateException("can not save Hall of fame", exception);
			return false;
		}
				
	}
	
	
	/**
	 * parse the line of a text into a GameResult
	 * @param line
	 * @return the GameResult that has been parsed 
	 */
	private static GameResult parse(String line) {
		String[] p = line.split(SEPARATOR);
		if(p.length != 6) {
			throw new IllegalArgumentException("Invalid hall of fame line "+line);
		}
		var score = Integer.parseInt(p[0]);
		var maxHp = Integer.parseInt(p[1]);
		var heroLevel = Integer.parseInt(p[2]);
		var enemiesDefeated = Integer.parseInt(p[3]);
		var itemsValue = Integer.parseInt(p[4]);
		var floorsExplored = Integer.parseInt(p[5]);
		
		return new GameResult(score, new GameStats(maxHp, heroLevel, enemiesDefeated, itemsValue, floorsExplored));
		
		
	}

	
	/**
	 * Loads the hall of fame from the file .
	 * if the file does not exist ,an empty HallOfFame is returned
	 * @return a HallOfFame with the resuls 
	 */
	public HallOfFame load() {
		HallOfFame hallOfFame = new HallOfFame();
		
		//if no file, we return an empty file (first start ) 
		if(!Files.exists(PATH_FILE)) {
			return hallOfFame;
			
		}
		try {
			for(String line: Files.readAllLines(PATH_FILE)) {//read all lines and parses 
				if(line.isBlank()) {
					continue;//ignore empty line
				}
				hallOfFame.add(parse(line));
			}
			return hallOfFame;
		}catch(IOException excepetion) {
			//throw new IllegalStateException("can't load hall of fame", excepetion);
			return hallOfFame;
		}
	}
}
