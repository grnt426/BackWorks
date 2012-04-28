import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
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

	public Model() throws IOException {
		missions = new ArrayList<Mission>();
		createMissions();
		moveList = new ArrayList<Direction>();
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

			Mission m = new Mission((mc + 1), board);
			System.out.println(m);
			missions.add(m);
		}
	}

	/**
	 * Starts the game
	 */
	public void start() {
		current_mission = missions.get(0);
	}

	public Mission nextMission(){
		if(current_mission.getMissionNumber() == mission_count){
			System.out.println("WIN!");
			return null;
		}
		return current_mission = missions.get(
				current_mission.getMissionNumber());
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
}
