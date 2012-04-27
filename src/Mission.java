import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Mission {

	ArrayList<ArrayList<Tile>> board;

	public Mission(){
		board = new ArrayList<ArrayList<Tile>>();

		// Create the board
		for(int i = 0; i < 10; i++){
			ArrayList<Tile> col = new ArrayList<Tile>();
			for(int j = 0; j < 10; j++){
				col.add(new Tile());
			}
			board.add(col);
		}
	}
}
