package main.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import main.controller.ButtonController;


public class ControlPanel extends JPanel {
	private JButton solveBtn;
	private JButton resetBtn;
	private JButton exitBtn;
	
	public ControlPanel() {
		/*
		 * Initiate solve, reset and exit buttons
		 */
		solveBtn = new JButton("Solve");
		
		resetBtn = new JButton("Reset");
		
		exitBtn = new JButton("Exit");
		
		/*
		 * Create layout for these buttons
		 */
		createLayout(solveBtn, resetBtn, exitBtn);
	}
	
	private void createLayout(JComponent...arg) {
		var gl = new GroupLayout(this);
		
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		
		this.setLayout(gl);
		
		gl.setHorizontalGroup(gl.createParallelGroup()
				.addGap(50)
				.addComponent(arg[0])
				.addGap(50)
				.addComponent(arg[1])
				.addGap(50)
				.addComponent(arg[2])
				.addGap(50)
		);
		
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
				.addComponent(arg[1])
				.addComponent(arg[2])
		);
		
		// Set the same size for the buttons
		gl.linkSize(arg);
	}
	
	/**
    * Adds controller to all components.
    *
    * @param buttonController Controller which controls all user actions.
    */
   public void setController(ButtonController buttonController) {
       solveBtn.addActionListener(buttonController);
       resetBtn.addActionListener(buttonController);
       exitBtn.addActionListener(buttonController);
   }
}
