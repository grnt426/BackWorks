import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author:      Grant Kurtz
 */
public class EndTile extends Tile {

	static BufferedImage image;

	public EndTile(){
		try {
			image = ImageIO.read(new File("images/End.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString(){
		return "E";
	}

	@Override
	public Image getImage() {
		return image;
	}
}
