import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Author:      Grant Kurtz
 */
public class WallTile extends Tile {

	static BufferedImage image;

	public WallTile(int x, int y){
		super(false, false);
		setXCell(x);
		setYCell(y);
		try {
			image = ImageIO.read(new File("images/Wall.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString(){
		return "X";
	}

	@Override
	public Image getImage() {
		return image;
	}
}
