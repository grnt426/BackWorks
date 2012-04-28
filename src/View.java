import javax.swing.*;
import java.awt.*;

/**
 * Author:      Grant Kurtz
 */
public class View {

	private Model model;
	private Painter painter;

	public View(Model m) {
		this.model = m;

		// Create the window
		JFrame window = new JFrame();
		window.setTitle("BackWorks");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.setSize(new Dimension(801, 601));
		window.setLayout(new BorderLayout());

		// Tell model to start
		model.start();

		// Draw Shit
		painter = new Painter(model.getRows(), model.getCols());
		painter.setMission(model.getCurrentMission());
		window.getContentPane().add(painter);
		window.pack();

		// Make everything visible now that all the assets are ready
		window.setVisible(true);
	}



}
