package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.view.SudokuPanel;

/**
 * Class ButtonController implements ActionListener
 * 
 * @author Nhon
 *
 */
public class ButtonController implements ActionListener {
	private SudokuPanel sudokuPanel;
	
	public ButtonController(SudokuPanel sudokuPanel) {
		this.sudokuPanel = sudokuPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch ( e.getActionCommand() ) {
			case "Exit":
				System.exit(0); break;
			case "Reset":
				sudokuPanel.reset(); break;
			case "Solve":
				sudokuPanel.solve(); break;
			default: break;
		}
	}

}
