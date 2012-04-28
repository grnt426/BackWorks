import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author:      Grant Kurtz
 */
public class TrapTile extends Tile {

	static BufferedImage image;

	public TrapTile(int x, int y){
		super(false, false);
		setXCell(x);
		setYCell(y);
		try {
			image = ImageIO.read(new File("images/Trap.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString(){
		return "T";
	}

	@Override
	public Image getImage() {
		return image;
	}
}
