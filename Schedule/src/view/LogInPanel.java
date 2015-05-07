package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Schedule;
import controller.LogInController;

public class LogInPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	
	private JLabel titleLabel, initialslabel, errorLabel;
	private JTextField initialsText;
	private JButton logInButton, shutDownButton;
	
	public LogInPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Welcome");
		this.initialslabel = new JLabel("Login (initials):");
		this.errorLabel = new JLabel("");
		this.initialsText = new JTextField("");
		this.logInButton = new JButton("Log in");
		this.shutDownButton = new JButton("Shut down");
		
		this.setLayout(null);
		
		errorLabel.setForeground(Color.RED);
		
		titleLabel.setBounds(20,20,200,40);
		initialslabel.setBounds(25,80,200,40);
		initialsText.setBounds(20,120,200,40);
		logInButton.setBounds(240,120,100, 40);
		errorLabel.setBounds(20, 180, 200, 40);
		shutDownButton.setBounds(20,240,100,40);
		
		this.add(titleLabel);
		this.add(initialslabel);
		this.add(initialsText);
		this.add(logInButton);
		this.add(errorLabel);
		this.add(shutDownButton);
	}
	
	public void registerListener(LogInController controller) {
		logInButton.addActionListener(controller);
		shutDownButton.addActionListener(controller);	
	}
	
	public String getInitials() {
		return initialsText.getText();
	}
	
	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
	
}
