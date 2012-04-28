import java.awt.*;

/**
 * Author:      Grant Kurtz
 */
public abstract class Tile {

	private boolean playerMovable;
	private boolean objectMovable;
	private int xCell;
	private int yCell;

	public Tile(boolean playerMovable, boolean objectMovable){
		this.playerMovable = playerMovable;
		this.objectMovable = objectMovable;
	}

	public Tile clone(){
		return Tile.TileFactory(this.toString().charAt(0), getXCell(),
				getYCell());
	}

	public void setXCell(int xCell){
		this.xCell = xCell;
	}

	public void setYCell(int yCell) {
		this.yCell = yCell;
	}

	public int getXCell() {
		return xCell;
	}

	public int getYCell() {
		return yCell;
	}

	public boolean playerMovable(){
		return playerMovable;
	}

	public boolean objectMovable(){
		return objectMovable;
	}

	public static Tile TileFactory(char t, int c, int r){
		switch(t){
			case ' ':
				return new EmptyTile(c, r);
			case 'e':
			case 'E':
				return new EndTile(c, r);
			case 'r':
			case 'R':
				return new RobotTile(c, r);
			case 's':
			case 'S':
				return new StickyTile(c, r);
			case 't':
			case 'T':
				return new TrapTile(c, r);
			case 'x':
			case 'X':
			default:
				return new WallTile(c, r);
		}
	}

	public abstract Image getImage();
}
