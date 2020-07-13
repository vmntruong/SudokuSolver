package main.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class Field extends JTextField {
   private int x;      // X position in game.
   private int y;      // Y position in game.

   /**
    * Constructs the label and sets x and y positions in game.
    *
    * @param x     X position in game.
    * @param y     Y position in game.
    */
   public Field(int x, int y) {
   	 // Set maximum size of the text field to 1
       super("", 1);
       setHorizontalAlignment(CENTER);
       this.x = x;
       this.y = y;
       
       // set the limit number of characters to 1
       setDocument(new TextFieldLimit(1));
       
       setPreferredSize(new Dimension(50, 50));
       setBorder(BorderFactory.createLineBorder(Color.GRAY));
       setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
       setOpaque(true);
       setBackground(Color.WHITE);
   }

   /**
    * Sets number and foreground color according to userInput.
    *
    * @param number        Number to be set.
    * @param userInput     Boolean indicating number is user input or not.
    */
   public void setNumber(int number, boolean userInput) {
       setForeground(userInput ? Color.BLUE : Color.BLACK);
       setBackground(userInput ? Color.WHITE : Color.CYAN);
       setText(number > 0 ? number + "" : "");
   }

   /**
    * Returns x position in game.
    *
    * @return  X position in game.
    */
   public int getFieldX() {
       return x;
   }

   /**
    * Return y position in game.
    *
    * @return  Y position in game.
    */
   public int getFieldY() {
       return y;
   }
}