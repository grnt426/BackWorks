import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Mission {

	private ArrayList<ArrayList<Tile>> board;
	private int mission_number;

	public Mission(int mc, ArrayList<ArrayList<Tile>> board){
		mission_number = mc;
		this.board = board;
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
}
