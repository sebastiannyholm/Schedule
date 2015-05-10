
package jcomponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel {

	public Label(String title) {
		super(title);
		
		setFont(new Font("Serif", Font.PLAIN, 14));
		setForeground(Color.BLACK);
	}
	
}
