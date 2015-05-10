package jcomponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class ErrorLabel extends JLabel {

	public ErrorLabel(String title) {
		super(title);
		
		setFont(new Font("Serif", Font.PLAIN, 14));
		setForeground(Color.RED);
	}
	
}
