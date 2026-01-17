package game.view.loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * a class to load all images before opening zen WIndow 
 */
public final class ImageLoader {
	private static final Map<String, BufferedImage> IMAGES = new HashMap<>();
	private static boolean loaded = false; // to keep track if the image is loaded yet 
	
	/*
	 * method to load all the images 
	 * will be used before stating the game 
	 */
	public static void loadAll() {
		if(loaded) {
			throw new IllegalStateException("already loaded ");
		}
		IO.println("Loading all the images ... ");
		load("uknown", "/sprites/ui/uknown.png");
		//menu 
		load("bg_menu","/sprites/ui/menu_bg.png");
		load("play_button","/sprites/ui/play_btn.png");
		load("title","/sprites/ui/title.png");
		//map
		load("mapBtn","/sprites/ui/mapBtn.png");
		//loot
		load("finishLooting","/sprites/ui/finishLooting.png");
		//gameOver
		load("gameOver","/sprites/ui/gameOver.png");
		load("retryBtn","/sprites/ui/retryBtn.png");
		load("mainMenuBtn","/sprites/ui/mainMenuBtn.png");
		//the Dungeon : 
		load("bg","/sprites/ui/dunjon/bhHH.png");
		load("tile","/sprites/ui/dunjon/tile.png");
		load("hero","/sprites/ui/dunjon/heroUI/retro_jojo.png");
		load("exitDoor","/sprites/ui/dunjon/exitDoor.png");
		
		//icons
		load("blockIcon", "/sprites/items/blockIcon.png");
		load("attackicon", "/sprites/items/attackicon.png");
		load("healicon", "/sprites/items/healIcon.png");
		load("summonIcon", "/sprites/items/summonIcon.png");
		//the BackPack:
		load("backpack","/sprites/ui/backpack1.png");
		load("parchemin", "/sprites/ui/parchemin.png");
		
		//the Items :
		load("woodensword","/sprites/items/weapon/woodensword.png");
		load("woodenSword","/sprites/items/woodenSword.png");
		load("magicwand","/sprites/items/weapon/magicwand.png");
		load("roughbuckler","/sprites/items/sshield.png");
		load("paladinSword","/sprites/items/paladinSword.png");
		load("gold","/sprites/items/gold.png");
		load("booShield","/sprites/items/booShield.png");
		load("manaStone","/sprites/items/manaStone.png");
		load("itemparchemin","/sprites/items/itemparchemin.png");
		load("thunaBox","/sprites/items/thunaBox.png");
		
		//the Enemies:
		load("lowenergy","/sprites/ui/dunjon/enemies/low_energy.png");
		load("ratwolf","/sprites/ui/dunjon/enemies/ratwolf.png");
		load("smallratwolf","/sprites/ui/dunjon/enemies/smallRatWolf.png");
		load("muskratbrigand","/sprites/ui/dunjon/enemies/muskratBrigand.png");
		load("lilbee","/sprites/ui/dunjon/enemies/lilBee.png");
		load("slime","/sprites/ui/dunjon/enemies/slime.png");
		load("frogSorcerer","/sprites/ui/dunjon/enemies/frogSorcerer.png");
		load("livingShadow","/sprites/ui/dunjon/enemies/livingShadow.png");
		load("beeQueen","/sprites/ui/dunjon/enemies/beeQueen.png");
		
		//The merchantRoom:
		load("merchant","/sprites/ui/dunjon/merchant/dwarfMerchant.png");
		
		//the treasureRoom:
		load("treasure_close","/sprites/items/treasure_close.png");
		load("treasure_open","/sprites/items/treasure_open.png");
		
		//the healerRoom:
		//the ExitRoom:
		IO.println("All images are loaded ");
		loaded = true ;
		
	}
	
	private static void load(String key, String path) {
		Objects.requireNonNull(key, "key can't be null");
		Objects.requireNonNull(path, "path can't be null");
		
		try(InputStream input = ImageLoader.class.getResourceAsStream(path)){
			if(input == null) {
				throw new IOException("image not found , "+ path);
			}
			BufferedImage img = ImageIO.read(input);
			if(img == null) {//check if the key exists
				throw new IllegalArgumentException("invalid ");
			}
			IMAGES.put(key, img);
		}catch(IOException e) {
			throw new RuntimeException("can't load image at :  " + path,e);
		}
	}
	/**
	 * 
	 * @param key : to indentify the image 
	 * @return the buffered image 
	 */
	public static BufferedImage getLoadedImage(String key) {
		Objects.requireNonNull(key,"key can't be null");
		if(!loaded) {
			throw new IllegalStateException("not loaded ");
		}
		BufferedImage img = IMAGES.get(key);
		if(img == null) {//check if the key exists
			throw new IllegalArgumentException("invalid: image key does not exist'" +key+"' ");
		}
		return img;
	}
}