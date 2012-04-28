import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author:      Grant Kurtz
 */
public class View implements ActionListener{

	// Model to hold game state information
	private Model model;

	// Paint class to handle graphics
	private Painter painter;

	static final private String gameName = "BackWorks";

	// Primary Window
	JFrame window;

	// GUI Elements
	JButton leftControl = new JButton("<-");
	JButton rightControl = new JButton("->");
	JButton upControl = new JButton("^");
	JButton downControl = new JButton("V");
	JButton haltControl = new JButton("Halt");
	JTextArea moveTextList = new JTextArea(30, 10);
	JButton runControl = new JButton("Run!");
	JButton clearControl = new JButton("Clear");
	JButton resetControl = new JButton("Reset");
	JButton removeControl = new JButton("Remove");
	JTextArea messageBox = new JTextArea(8, 80);

	public View(Model m) {
		this.model = m;

		// Create the window
		window = new JFrame();
		window.setTitle("BackWorks");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());

		// Tell model to start
		model.start();

		// Draw Shit
		painter = new Painter(model.getRows(), model.getCols());
		painter.setMission(model.getCurrentMission());
		window.getContentPane().add(painter, BorderLayout.CENTER);
		model.addListener(painter);
		model.addListener(this);

		// Create movement command list
		JPanel moveListPanel = new JPanel();
		moveTextList.setEditable(false);
		JScrollPane moveTextScroller = new JScrollPane(moveTextList);
		moveTextScroller.setHorizontalScrollBar(null);
		moveTextScroller.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		moveTextScroller.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		moveListPanel.add(moveTextScroller);

		// Create movement control buttons
		JPanel moveControl = new JPanel();
		moveControl.setLayout(new GridLayout(3, 3));
		moveControl.add(clearControl);
		moveControl.add(upControl);
		moveControl.add(resetControl);
		moveControl.add(leftControl);
		moveControl.add(haltControl);
		moveControl.add(rightControl);
		moveControl.add(runControl);
		moveControl.add(downControl);
		moveControl.add(removeControl);

		// Setup mnemonics for the buttons, because whatever
		upControl.setMnemonic('w');
		leftControl.setMnemonic('a');
		rightControl.setMnemonic('d');
		downControl.setMnemonic('s');
		haltControl.setMnemonic('q');

		// Setup Listeners for the buttons
		upControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDirection(Direction.UP);
			}
		});
		leftControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDirection(Direction.LEFT);
			}
		});
		rightControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDirection(Direction.RIGHT);
			}
		});
		haltControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDirection(Direction.HALT);
			}
		});
		downControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDirection(Direction.DOWN);
			}
		});
		clearControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!model.isRunning()){
					model.clearMoveList();
					moveTextList.setText("");
				}
			}
		});
		runControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model.isPausable()){
					pauseGame();
					runControl.setText("Resume");
				}
				else if(model.isPaused()){
					resumeGame();
					runControl.setText("Pause");
				}
				else{
					runGame();
					runControl.setText("Pause");
				}
			}
		});
		resetControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model.isRunning() && !model.getVictory()){
					window.setTitle(gameName);
					model.reset();
					runControl.setText("Run!");
				}
			}
		});
		removeControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!model.isRunning()){
					model.removeDirection();
					updateDirectionList();
				}
			}
		});

		// Create panel to contain movement list and move buttons
		JPanel movePanel = new JPanel();
		movePanel.setBorder(BorderFactory.createTitledBorder("Robot Commands"));
		movePanel.setLayout(new GridBagLayout());
		movePanel.add(moveListPanel);
		movePanel.add(moveControl);

		// Add the control scheme to the primary window
		window.getContentPane().add(movePanel, BorderLayout.EAST);

		// Add the message box to the window
		JPanel messagePanel = new JPanel();
		JScrollPane messageScroller = new JScrollPane(messageBox);
		messageScroller.setHorizontalScrollBar(null);
		messageScroller.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		messageScroller.setAutoscrolls(true);
		messageScroller.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		messageBox.setWrapStyleWord(true);
		messagePanel.add(messageScroller);
		window.getContentPane().add(messagePanel, BorderLayout.SOUTH);

		// Give the initial message
		messageBox.setText(
			"Boss:\tWelcome to your first day at BackWorks! My name is Arford Clex"
			+ " and here at BackWorks you will solve some of the hardest (easiest)"
			+ " challenges in\n\tadvanced synchronized robotic control. I must"
			+ " admit that our facilities aren't exactly up to spec, but we"
			+ " strive to make anything possible with\n\tsynergized group"
			+ " communication and *manager level bizspeak*.");
		messageBox.setText(messageBox.getText() +
			"\n*Speaker*\tWarning, stage 4 quantum field collapse eminent in"
			+ " Serial Robot Control #1.  Warning, stage 4-");
		messageBox.setText(messageBox.getText() +
			"\nBoss:\tWe better get you down there quick, no time for proper"
			+ " training, you will just have to figure out the controls and"
			+ " try to get the Serial Robots back on\n\ttheir charging pads! Our"
			+ " last intern, whom we also didn't properly train, did not move"
			+ " the robots back to their charge pads once he finished his\n\ttask."
			+ " If the robots are not properly charged their fusion reactor's"
			+ " quantum containment field will collapse, destroying everything!"
		);

		// Adjust the window size to fit all the elements snugly
		window.pack();

		// Make everything visible now that all the assets are ready
		window.setVisible(true);
	}

	private void runGame() {
		model.run();
		window.setTitle(gameName + " - Running");
	}

	private void resumeGame() {
		model.resume();
		window.setTitle(gameName + " - Running");
	}

	private void pauseGame() {
		model.pause();
		window.setTitle(gameName + " - Paused");
	}

	private void addDirection(Direction dir) {
		if(model.isRunning())
			return;
		model.addDirection(dir);
		updateDirectionList();
	}

	private void updateDirectionList() {
		moveTextList.setText("");
		for(Direction d: model.getMoveList()){
			switch(d){
				case UP:
					moveTextList.append("^\n");
					break;
				case LEFT:
					moveTextList.append("<-\n");
					break;
				case RIGHT:
					moveTextList.append("->\n");
					break;
				case DOWN:
					moveTextList.append("V\n");
					break;
				case HALT:
					moveTextList.append("HALT\n");
			}
		}
	}


	public void actionPerformed(ActionEvent e) {

		Object src = e.getSource();
		String msg = e.getActionCommand();

		if(src instanceof Model.GameSimulation){
			if(msg.equals("end")){

				// Check to see if we need to run a victory screen
				if(model.getVictory()){
					appendMessage("Victory, you have reached the charge pads!");
				}
				else{
					appendMessage("Failure, your stupid has blown BackWorks to"
							+ " smithereens!");
				}
			}
		}

		if(msg.equals("crashed")){
			appendMessage("Failure, your stupid has blown the town to"
							+ " dust!");
		}
		else if(msg.equals("trapped")){
			appendMessage("Failure, your dumb has trapped the world's most" +
							" expensive robot to an eternity of no designation!");
		}
	}

	private void appendMessage(String s) {
		messageBox.setText(messageBox.getText() + "\n" + s);
	}
}
