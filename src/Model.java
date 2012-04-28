import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.plugin2.message.GetAppletMessage;

import javax.swing.*;
import javax.xml.bind.Unmarshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Model {

	// Mission Data
	private ArrayList<Mission> missions;
	private int rows, cols;
	private int mission_count;
	private Mission current_mission;


	private ArrayList<Direction> moveList;

	// State values
	private boolean paused;
	private boolean running;
	private boolean ready;
	private boolean end;
	private boolean victory;

	private GameSimulation game;
	private ArrayList<ActionListener> listeners;

	public Model() throws IOException {
		missions = new ArrayList<Mission>();
		createMissions();
		moveList = new ArrayList<Direction>();
		listeners = new ArrayList<ActionListener>();


		// Set state info
		running = false;
		paused = false;
		ready = false;
		end = false;

		game = new GameSimulation();
		Thread t = new Thread(game);
		t.start();
	}

	private void createMissions() throws IOException {
		BufferedReader br = null;
		br = new BufferedReader(
				new FileReader("config/missionconfig.txt"));
		assert br != null;

		// Get mission sizes
		mission_count = Integer.parseInt(br.readLine());
		rows = Integer.parseInt(br.readLine());
		cols = Integer.parseInt(br.readLine());
		printDebug("createMissions()", "Missions: " + mission_count + " Rows: "
				+ rows + " Cols: " + cols);

		// Get Missions
		for(int mc = 0; mc < mission_count; mc++){
			br = new BufferedReader(
					new FileReader("config/mission" + (mc + 1) + ".txt"));

			// Create the board
			ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
			for(int r = 0; r < rows; r++){
				ArrayList<Tile> row = new ArrayList<Tile>();
				String line = br.readLine();
				//printDebug("createMissions()", "INPUT LINE: " + line);
				for(int c = 0; c < cols; c++){
					row.add(Tile.TileFactory(line.charAt(c), c, r));
				}
				board.add(row);
			}

			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null){
				sb.append(line);
			}

			Mission m = new Mission((mc + 1), board);
			m.setStartMissionText(sb.toString());
			System.out.println(m);
			System.out.println(m.getStartMissionText());
			missions.add(m);
		}
	}

	/**
	 * Starts the game
	 */
	public void start() {
		current_mission = missions.get(0).clone();
		game.updateListener();
	}

	public void nextMission(){
		if(current_mission.getMissionNumber() == mission_count)
			return;
		current_mission = missions.get(current_mission.getMissionNumber());
		reset();
	}

	public Mission getCurrentMission(){
		return current_mission;
	}

	public static void printDebug(String func, String msg){
		System.out.println("DEBUG:\t" + func + "\n\t" + msg);
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void addDirection(Direction d){
		moveList.add(d);
	}

	public void removeDirection(){
		if(moveList.size() != 0)
			moveList.remove(moveList.size()-1);
	}

	public void removeNthDirection(int index){
		if(moveList.size() > index)
			moveList.remove(index);
	}

	public void clearMoveList(){
		moveList.clear();
	}

	public ArrayList<Direction> getMoveList() {
		return moveList;
	}

	public void pause() {
		while(!ready){

		}
		paused = true;
	}

	public boolean isPausable() {
		while(!ready){

		}
		return (running && !paused && !end);
	}

	public boolean isPaused() {
		while(!ready){

		}
		return paused;
	}

	public void resume() {
		while(!ready){

		}
		paused = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void run() {
		while(!ready){

		}
		printDebug("run()", "Running Simulation!");
		running = true;

		Thread t = new Thread();
	}

	public void addListener(ActionListener listener){
		listeners.add(listener);
	}

	public void reset() {
		while(!ready){

		}
		running = false;
		paused = false;
		end = false;
		victory = false;
		game.reset();
		printDebug("run()", "Resetting Simulation!");

		// get next mission, and make sure the GameSim is updated
		current_mission = missions.get(
				current_mission.getMissionNumber() - 1).clone();
		game.updateListener();

		// let the painter know its mission object is out of date
		informListeners(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"reset"));

	}

	public void informListeners(ActionEvent ae){
		for(ActionListener al : listeners){
			al.actionPerformed(ae);
		}
	}

	public class GameSimulation implements Runnable, ActionListener {

		private int step;

		public GameSimulation() {
			step = 0;
			ready = true; // can handle simulation state changes
		}

		public void run() {

			// YOU SHALL NEVER PASS! Unless of course we get some kind of
			// horrible ConcurrentModificationException, which should only
			// happen if I am a shit programmer, or Java's scheduler throws
			// me a hardball.  Of course, you could always kill the thread, but
			// that isn't very nice, and is actually deprecated so you probably
			// shouldn't because rules exist for reasons or something or so I
			// was told. Also paragraphs.
			while(true){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// simulate the game while we can
				while(running && !paused && !end){
					if(step < moveList.size()){
						current_mission.moveRobots(moveList.get(step));
						step++;

						// tell listeners of the game state change
						informListeners(new ActionEvent(this,
								ActionEvent.ACTION_PERFORMED,
								"changed"));

						try {
							Thread.sleep(750);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else{

						// Make sure all Robots consume remaining commands
						current_mission.moveRobots(Direction.HALT);
						if(!current_mission.commandsFlushed()){

							// tell listeners of the game state change
							informListeners(new ActionEvent(this,
									ActionEvent.ACTION_PERFORMED,
									"changed"));

							try {
								Thread.sleep(750);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						else{
							end = true;
							printDebug("run()", "END REACHED!");
							checkEndState();
							informListeners(new ActionEvent(this,
									ActionEvent.ACTION_PERFORMED,
									"end"));
						}

						// game end, run other logic
					}
				}

				ready = true;
			}
		}

		public void reset(){
			step = 0;
		}

		public void updateListener(){
			current_mission.setListener(this);
		}

		public void actionPerformed(ActionEvent e) {

			Object src = e.getSource();

			if(src instanceof PlayerMovable){
				PlayerMovable pm = (PlayerMovable) src;
				if(e.getActionCommand().equals("stateChange")){
					switch(pm.getState()){
						case CRASHED:
							end = true;
							informListeners(new ActionEvent(pm,
									ActionEvent.ACTION_PERFORMED,
									"crashed"));
							break;
						case TRAPPED:
							end = true;
							informListeners(new ActionEvent(pm,
									ActionEvent.ACTION_PERFORMED,
									"trapped"));
					}
				}
			}
		}
	}

	public boolean getVictory(){
		return victory && end;
	}

	private void checkEndState() {
		victory = current_mission.checkVictory();
	}
}
