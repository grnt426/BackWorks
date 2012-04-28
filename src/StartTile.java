import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author:      Grant Kurtz
 */
public class StartTile extends Tile {

	static BufferedImage image;

	public StartTile(){
		try {
			image = ImageIO.read(new File("images/Robot.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString(){
		return "R";
	}

	@Override
	public Image getImage() {
		return image;
	}
}
