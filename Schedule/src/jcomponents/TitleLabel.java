package jcomponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class TitleLabel extends JLabel {

	public TitleLabel(String title) {
		super(title);
		
		setFont(new Font("Serif", Font.PLAIN, 26));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.setSize(920, 40);
	}
	
}
