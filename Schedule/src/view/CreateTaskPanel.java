package view;

import javax.swing.JPanel;

import controller.CreateTaskController;
import model.Schedule;

public class CreateTaskPanel extends JPanel {

	private Schedule schedule;
	
	public CreateTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		
		this.setLayout(null);
	}

	public void registerListener(CreateTaskController controller) {
		// TODO Auto-generated method stub
		
	}
	
}
