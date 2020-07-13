package main.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.Solution99;
import main.Sudoku99;

public class SudokuPanel extends JPanel {
	private Field[][] fields;       // Array of fields.
   private JPanel[][] panels;      // Panels holding the fields.

   
   /**
    * Constructs the panel, adds sub panels and adds fields to these sub panels.
    */
   public SudokuPanel() {
       super(new GridLayout(3, 3));
       setOpaque(true);

       panels = new JPanel[3][3];
       for (int y = 0; y < 3; y++) {
           for (int x = 0; x < 3; x++) {
               panels[y][x] = new JPanel(new GridLayout(3, 3));
               panels[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
               add(panels[y][x]);
           }
       }

       fields = new Field[9][9];
       for (int y = 0; y < 9; y++) {
           for (int x = 0; x < 9; x++) {
               fields[y][x] = new Field(x, y);
               fields[y][x].setText( Integer.toString(Sudoku99.grille[y][x]) );////
               panels[y / 3][x / 3].add(fields[y][x]);
           }
       }
   }
   
   /**
    * Reset the grid
    */
   public void reset() {
   	for (int y = 0; y < 9; y++) {
         for (int x = 0; x < 9; x++) {
         	fields[y][x].setText("");
         	fields[y][x].setBackground(Color.WHITE);
         	fields[y][x].setForeground(Color.BLACK);
         }
   	}
   }
   
   /**
    * Get input from the panel
    */
   public int[][] getInput() {
   	int[][] input = new int[9][9];
   	for (int y = 0; y < 9; y++) {
         for (int x = 0; x < 9; x++) {
         	if ( !fields[y][x].getText().equals("") )
         		input[y][x] = Integer.parseInt(fields[y][x].getText());
         }
   	}
   	return input;
   }
   
   /**
    * Solve
    */
   public void solve() {
   	int[][] solution;
   	int[][] input = getInput();
   	Solution99.resolve(input);
   	if (Solution99.getNumberOfSolutions()>0) {
   	   solution = Solution99.getAllSolutions().get(0);
   	   for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
            	if ( input[y][x] > 0 )
            		fields[y][x].setNumber(solution[y][x], false);
            	else 
            		fields[y][x].setNumber(solution[y][x], true);
            }
         }
   	}
   }
}
