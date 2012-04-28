import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author:      Grant Kurtz
 */
public class StickyTile extends Tile {

	static BufferedImage image;
	private int stickyCount;
	private int currentStickyCount;

	public StickyTile(int x, int y){
		super(false, false);
		setXCell(x);
		setYCell(y);
		try {
			image = ImageIO.read(new File("images/Sticky.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		stickyCount = 1;
		currentStickyCount = stickyCount;
	}

	public String toString(){
		return "S";
	}

	@Override
	public Image getImage() {
		return image;
	}

	public void decrementStuckCount() {
		currentStickyCount--;
	}

	public boolean stillStuck() {
		return currentStickyCount != 0;
	}

	public void resetStickyCount() {
		currentStickyCount = stickyCount;
	}
}
