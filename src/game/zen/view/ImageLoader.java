package game.zen.view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;//lit les fichier png 
//objectif : retourner l'image qui sera utilisée par Graphics2d

public final class ImageLoader {//ne cree pas un objet 
	public static BufferedImage load(String path) {//buffered image pour representer l'image en memoire 
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));//lit l'image 
		}catch(IOException | IllegalArgumentException e) {//cas ou on arrive pas a lire 
			throw new RuntimeException("error loading img" + path, e);
		}
	}
}
