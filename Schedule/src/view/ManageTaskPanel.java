package view;

import javax.swing.JPanel;

import controller.ManageTaskController;
import model.Schedule;
import model.Task;

public class ManageTaskPanel extends JPanel {

	private Schedule schedule;
	private Task task;
	
	public ManageTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		
		this.setLayout(null);
	}

	public void registerListener(ManageTaskController controller) {
		
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
}
