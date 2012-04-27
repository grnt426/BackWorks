import javax.swing.*;
import java.awt.*;

/**
 * Author:      Grant Kurtz
 */
public class View {

	private Model model;

	public View(Model m) {
		this.model = m;

		// Create the window
		JFrame window = new JFrame();
		window.setTitle("BackWorks");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(800, 600));
		window.setLayout(new BorderLayout());

		// Draw Shit


		// Make everything visible now that all the assets are ready
		window.setVisible(true);

		// Tell model to start
		model.start();
	}



}
