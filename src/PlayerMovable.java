/**
 * Author:      Grant Kurtz
 */
public class PlayerMovable extends Movable{

	private PlayerMovable next_robot;
	private Direction next_direction;
	private Tile ourTile;
	private Tile previousTile;
	private Mission mission;

	public PlayerMovable(int xCell, int yCell, PlayerMovable next_robot,
						 Tile tile, Mission m) {
		super(xCell, yCell);
		Model.printDebug("PlayerMovable()", "X: " + xCell + " Y: " + yCell);
		this.next_robot = next_robot;
		this.ourTile = tile;
		this.mission = m;

		// Don't allow ourselves to move at first
		setDirection(Direction.HALT);
	}

	@Override
	public void move() {

		// Put back the last tile that was here
		if(previousTile == null)
			mission.setTile(ourTile.getXCell(), ourTile.getYCell(),
					new EmptyTile(ourTile.getXCell(), ourTile.getYCell()));
		else
			mission.setTile(ourTile.getXCell(), ourTile.getYCell(),
					previousTile);

		// move ourselves
		ourTile.setXCell(ourTile.getXCell() +
				Movement.getXComponent(getDirection()));
		ourTile.setYCell(ourTile.getYCell() +
				Movement.getYComponent(getDirection()));
		previousTile = mission.setTile(ourTile.getXCell(), ourTile.getYCell(),
				ourTile);

		// Player DUN FUCKED UP!
		if(previousTile instanceof RobotTile){
			System.out.println("ROBOTS CRASHED!");
		}

		// give the robot after us the next direction to take
		if(next_robot != null){
			next_robot.setNextDirection(getDirection());
			next_robot.move();
		}

		// set our new direction to our next one
		setDirection(next_direction);
	}

	private void setNextDirection(Direction direction) {
		next_direction = direction;
	}
}
