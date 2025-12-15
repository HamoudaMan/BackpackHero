package game.zen.imgLoad;

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
		IO.println("Loading all the images : ");
		//the Dungeon : 
		load("bg","/sprites/ui/dunjon/bgHH.png");
		load("tile","/sprites/ui/dunjon/tile.png");
		load("hero","/sprites/ui/dunjon/heroUI/retro_jojo.png");
		//the BackPack:
		load("backpack","/sprites/ui/backpack1.png");
		load("parchemin", "/sprites/ui/parchemin.png");
		//the Items :
		load("woodensword","/sprites/items/weapon/woodenSword.png");
		//the Enemies:
		load("ratwolf","/sprites/ui/dunjon/enemies/ratwolf.png");
		load("smallratwolf","/sprites/ui/dunjon/enemies/smallRatWolf.png");
		//The merchantRoom:
		load("merchant","/sprites/ui/dunjon/merchant/dwarfMerchant.png");
		//the treasureRoom:
		load("treasure","/sprites/items/treasure1.png");
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
