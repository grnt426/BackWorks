import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author:      Grant Kurtz
 */
public class EmptyTile extends Tile {

	static BufferedImage image;

	public EmptyTile(){
		try {
			image = ImageIO.read(new File("images/Empty.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString(){
		return " ";
	}

	@Override
	public Image getImage() {
		return image;
	}
}
