import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Painter extends JPanel implements ActionListener {

	private Mission mission;
	private int rows, cols;
	private int cell_length;
	private int panel_height;
	private int panel_length;

	public Painter(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		cell_length = 60;
		panel_height = rows * cell_length;
		panel_length = cols * cell_length;
	}

	public void paint(Graphics g){

		// init basics
		super.paintComponent(g);
		setBackground(Color.WHITE);

		// make sure we have a mission before doing anything
		if(mission == null)
			return;

		// Draw Current Mission State
		g.setColor(Color.BLACK);
		ArrayList<ArrayList<Tile>> board = mission.getBoard();
		for(int r = 0; r < cols + 1; r++){
			for(int c = 0; c < rows + 1; c++){

				// draw grid lines (just in case)
				g.drawLine((r * cell_length), (c * cell_length),
						(r * cell_length), panel_height);
				g.drawLine((r * cell_length), (c * cell_length),
						panel_length, (c * cell_length));
			}
		}

		for(int r = 0; r < rows; r++){
			for(int c = 0; c < cols; c++){

				// draw Tile picture
				if(board.get(r).get(c).getImage() != null)
					g.drawImage(board.get(r).get(c).getImage(), (c * cell_length),
							(r * cell_length), null);
			}
		}
	}

	public void setMission(Mission m){
		this.mission = m;
	}

	public Dimension getPreferredSize(){
		return new Dimension(panel_length, panel_height);
	}

	public void actionPerformed(ActionEvent e) {

		Object src = e.getSource();

		if(src instanceof Model){

			// reset event
			mission = ((Model) src).getCurrentMission();
		}

		repaint();
	}
}
