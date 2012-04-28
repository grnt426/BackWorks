import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author:      Grant Kurtz
 */
public class View {

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
	JTextArea moveTextList = new JTextArea(20, 10);
	JButton runControl = new JButton("Run!");
	JButton clearControl = new JButton("Clear");
	JButton resetControl = new JButton("Reset");
	JButton removeControl = new JButton("Remove");

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
		model.setListener(painter);

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
				if(model.isRunning()){
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

		// Adjust the window size to fit all the elements snuggly
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


}
