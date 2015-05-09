package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jcomponents.Label;
import jcomponents.ErrorLabel;
import jcomponents.TitleLabel;
import model.Schedule;
import controller.LogInController;

public class LogInPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	
	private TitleLabel titleLabel;
	private Label initialslabel;
	private ErrorLabel errorLabel;
	private JTextField initialsText;
	private JButton logInButton, shutDownButton;
	
	public LogInPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Welcome");
		this.initialslabel = new Label("Login (initials):");
		this.errorLabel = new ErrorLabel("");
		this.initialsText = new JTextField("");
		this.logInButton = new JButton("Log in");
		this.shutDownButton = new JButton("Shut down");
		
		this.setLayout(null);
		
		titleLabel.setLocation(20, 170);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		
		initialslabel.setBounds(345,230,200,40);
		initialsText.setBounds(335,270,300,40);
		logInButton.setBounds(335,320,100,40);
		errorLabel.setBounds(445, 230, 200, 40);
		shutDownButton.setBounds(530,320,100, 40);
		
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
