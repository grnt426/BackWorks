/**
 * Author:      Grant Kurtz
 */
public abstract class Movable {

	private int xCell, yCell;
	private Direction d;

	public Movable(int xCell, int yCell){
		this.xCell = xCell;
		this.yCell = yCell;
	}

	public int getXCell(){
		return xCell;
	}

	public int getYCell(){
		return yCell;
	}

	public void setDirection(Direction d){
		this.d = d;
	}

	public Direction getDirection(){
		return d;
	}

	public abstract void move();
}
