import com.sun.xml.internal.ws.message.EmptyMessageImpl;

/**
 * Author:      Grant Kurtz
 */
public class PlayerMovable extends Movable{

	private PlayerMovable next_robot;
	private Direction next_direction;
	private Tile ourTile;
	private Tile previousTile;
	private Mission mission;
	private RobotState state;

	public PlayerMovable(int xCell, int yCell, PlayerMovable next_robot,
						 Tile tile, Mission m) {
		super(xCell, yCell);
		Model.printDebug("PlayerMovable()", "X: " + xCell + " Y: " + yCell);
		this.next_robot = next_robot;
		this.ourTile = tile;
		this.mission = m;
		state = RobotState.FINE;

		// Don't allow ourselves to move at first
		setDirection(Direction.HALT);
	}

	@Override
	public void move() {

		// compute new direction
		int newX = ourTile.getXCell() + Movement.getXComponent(getDirection());
		int newY = ourTile.getYCell() + Movement.getYComponent(getDirection());

		// check the new location for special conditions
		Tile newLocation = mission.getTile(newX, newY);
		if(newLocation instanceof RobotTile &&
				getDirection() != Direction.HALT){
			System.out.println("ROBOTS CRASHED! X: " + newX + " Y: " + newY);
			System.out.println("TILE: " + mission.getTile(newX, newY));
			state = RobotState.CRASHED;
			stateChange();
		}
		else if(!(newLocation instanceof WallTile)){

			// Put back the last tile that was here
			if(previousTile == null)
				mission.setTile(ourTile.getXCell(), ourTile.getYCell(),
						new EmptyTile(ourTile.getXCell(), ourTile.getYCell()));
			else
				mission.setTile(ourTile.getXCell(), ourTile.getYCell(),
						previousTile);

			// move ourselves
			ourTile.setXCell(newX);
			ourTile.setYCell(newY);
			previousTile = mission.setTile(ourTile.getXCell(), ourTile.getYCell(),
					ourTile);

		}

		// give the robot after us the next direction to take
		if(next_robot != null){
			next_robot.setNextDirection(getDirection());
			next_robot.move();
		}

		// set our new direction to our next one
		setDirection(next_direction);
	}

	private void stateChange() {
		mission.stateChange(this);
	}

	public void setNextDirection(Direction direction) {
		next_direction = direction;
	}

	public Direction getNextDirection() {
		return next_direction;
	}

	public PlayerMovable getNextRobot() {
		return next_robot;
	}

	public Tile getPreviousTile() {
		return previousTile;
	}

	public RobotState getState() {
		return state;
	}
}
