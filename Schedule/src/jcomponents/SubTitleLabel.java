package jcomponents;

import java.awt.Font;

import javax.swing.JLabel;

public class SubTitleLabel extends JLabel {

	public SubTitleLabel(String title) {
		super(title);
		
		setFont(new Font("Serif", Font.PLAIN, 18));
	}
	
}
