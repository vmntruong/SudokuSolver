package main.view;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 * Class that implements PlainDocument to limit the number of characters in a text field
 * @author Nhon
 *
 */
public class TextFieldLimit extends PlainDocument {
	private int limit;
	
	public TextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString( int offset, String str, AttributeSet attr ) 
			throws BadLocationException {
		if (str == null) return;
	
		if ((getLength() + str.length()) <= limit) {
			char c = str.charAt(0);
			if (c>='1' && c<='9')
				super.insertString(offset, str, attr);
		}
	}
}