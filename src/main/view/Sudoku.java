package main.view;

import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

import main.controller.ButtonController;

/**
 * Main Class with the main function to run the program
 * 
 * @author Nhon
 *
 */
public class Sudoku extends JFrame {
	
	public Sudoku() {
		initUI();
	}
	
	/**
	 * Initiate the UI
	 */
	private void initUI() {
		/* Sudoku panel */
		var sudokuPanel = new SudokuPanel();
		
		/* Control panel */
		var controlPanel = new ControlPanel();
		
		/* Button controller */
		var buttonController = new ButtonController(sudokuPanel);
		controlPanel.setController(buttonController);
		
		/* Create the layout */ 
		createLayout(sudokuPanel, controlPanel);
		
		setTitle("Sudoku Solver");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Create the layout for components
	 * 
	 * @param arg: list of components
	 */
	private void createLayout(JComponent...arg) {
		var pane = getContentPane();
		var gl = new GroupLayout(pane);
		pane.setLayout(gl);
		
		gl.setAutoCreateContainerGaps(true);
		
		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addComponent(arg[0], 450, 450, 450)
				.addComponent(arg[1], 150, 150, 150)
		);
		
		gl.setVerticalGroup(gl.createParallelGroup()
				.addComponent(arg[0], 450, 450, 450)
				.addComponent(arg[1], 150, 150, 150)
		);
		
		pack();
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			var sudoku = new Sudoku();
			sudoku.setVisible(true);
		});

	}

}
