/**
 * Author:      Grant Kurtz
 */
public class Movement {

	public static int getXComponent(Direction d){
		switch(d){
			case LEFT:
				return -1;
			case RIGHT:
				return 1;
			case DOWN:
			case UP:
			case HALT:
			default:
				return 0;
		}
	}

	public static int getYComponent(Direction d){
		switch(d){
			case UP:
				return -1;
			case DOWN:
				return 1;
			case LEFT:
			case RIGHT:
			case HALT:
			default:
				return 0;
		}
	}
}
