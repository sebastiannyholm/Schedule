package view;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Schedule;

public class LogInPanel extends JPanel {

	private Schedule schedule;
	
	private JButton logIn = new JButton("Log in");
	private JButton shutDown = new JButton("Shut down");
	
	public LogInPanel(Schedule schedule) {
		this.schedule = schedule;
		
		
		
	}
	
}
