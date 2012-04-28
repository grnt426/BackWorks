import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Mission {

	private ArrayList<ArrayList<Tile>> board;
	private PlayerMovable first_robot;
	private ArrayList<Movable> objects;
	private int mission_number;

	public Mission(int mc, ArrayList<ArrayList<Tile>> board){
		mission_number = mc;
		this.board = board;

		first_robot = null;

		// Extract movable objects
		for(ArrayList<Tile> row : board){
			for(Tile t : row){
				if(t.playerMovable()){
					if(t instanceof RobotTile){

						// build the linked-list of robots backwards
						first_robot = new PlayerMovable(t.getXCell(),
								t.getYCell(), first_robot, t, this);
					}
					else{
						System.err.println("NOT ROBOT TILE SDDSF!");
					}
				}
				else if(t.objectMovable()){
					objects.add(new ObjectMovable(t.getXCell(), t.getYCell()));
				}
			}
		}
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Mission: #").append(mission_number).append("\n");
		for(ArrayList<Tile> row : board){
			for(Tile t : row){
				sb.append(t);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public void moveRobots(Direction d){
		first_robot.setDirection(d);
		first_robot.move();
	}

	public void moveObjects(Movement d){

	}

	public Tile setTile(int x, int y, Tile newTile){
		Tile t = board.get(y).get(x);
		board.get(y).set(x, newTile);
		return t;
	}

	public ArrayList<ArrayList<Tile>> getBoard() {
		return board;
	}

	public int getMissionNumber() {
		return mission_number;
	}
}
