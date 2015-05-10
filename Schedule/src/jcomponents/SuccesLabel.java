package jcomponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class SuccesLabel extends JLabel {

	public SuccesLabel(String title) {
		super(title);
		
		setFont(new Font("Serif", Font.PLAIN, 14));
		setForeground(Color.GREEN);
	}
	
}
