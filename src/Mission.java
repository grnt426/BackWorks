import javax.xml.bind.Unmarshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Mission {

	private ArrayList<ArrayList<Tile>> board;
	private PlayerMovable first_robot;
	private ArrayList<Movable> objects;
	private int mission_number;
	private ActionListener listener;

	public Mission(int mc, ArrayList<ArrayList<Tile>> board){
		mission_number = mc;
		this.board = board;

		first_robot = null;
		listener = null;

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

	public Mission clone(){
		ArrayList<ArrayList<Tile>> newBoard = new ArrayList<ArrayList<Tile>>();
		for(ArrayList<Tile> rows : board){
			ArrayList<Tile> newBoardRow = new ArrayList<Tile>();
			for(Tile t : rows){
				newBoardRow.add(t.clone());
			}
			newBoard.add(newBoardRow);
		}
		return new Mission(this.mission_number, newBoard);
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
		first_robot.setNextDirection(d); // Need this for when flushing
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

	public Tile getTile(int newX, int newY) {
		return board.get(newY).get(newX);
	}

	public boolean commandsFlushed() {
		PlayerMovable pm = first_robot;
		while(pm != null){
//			Model.printDebug("commandsFlushed()", pm.getDirection().name());
			if(pm.getDirection() != Direction.HALT)
				return false;
			pm = pm.getNextRobot();
		}
		return true;
	}

	public boolean checkVictory() {
		PlayerMovable pm = first_robot;
		while(pm != null){
			if(!(pm.getPreviousTile() instanceof EndTile))
				return false;
			pm = pm.getNextRobot();
		}
		return true;
	}

	public void setListener(ActionListener listener) {
		this.listener = listener;
	}

	public void stateChange(PlayerMovable playerMovable) {
		listener.actionPerformed(new ActionEvent(playerMovable,
				ActionEvent.ACTION_PERFORMED, "stateChange"));
	}
}
