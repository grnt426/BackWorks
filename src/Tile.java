import java.awt.*;

/**
 * Author:      Grant Kurtz
 */
public abstract class Tile {

	static Tile TileFactory(char t){
		switch(t){
			case ' ':
				return new EmptyTile();
			case 'e':
			case 'E':
				return new EndTile();
			case 'r':
			case 'R':
				return new StartTile();
			case 'x':
			case 'X':
			default:
				return new WallTile();
		}
	}

	public abstract Image getImage();
}
