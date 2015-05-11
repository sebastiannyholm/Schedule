package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Project;
import model.Schedule;
import model.Task;
import view.View;

public class CreateTaskController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Project project;
	private Calendar startDate = null, endDate = null;
	
	public CreateTaskController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getCreateTaskPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Create task":
				
				String name = view.getCreateTaskPanel().getTaskName(); 
				String bugetTime = view.getCreateTaskPanel().getbudgetTime();
				int time = 0;
				
				project = view.getCreateTaskPanel().getProject();
				
				if (view.getCreateTaskPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getCreateTaskPanel().getStartDate());
				}
				
				if (view.getCreateTaskPanel().getEndDate() != null) {
					endDate = new GregorianCalendar();
					endDate.setTime(view.getCreateTaskPanel().getEndDate());
				}
				
				if (name.equals("")) {
					view.getCreateTaskPanel().setErrorLabel("Give the task a name");
					break;
				}
				
				try {  
					time = Integer.parseInt(bugetTime);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getCreateTaskPanel().setErrorLabel("Budget can only be numbers");
					break;
				}
				
				if (startDate == null) {
					view.getCreateTaskPanel().setErrorLabel("Set a start date");
					break;
				}
				
				if (endDate == null) {
					view.getCreateTaskPanel().setErrorLabel("Set a end date");
					break;
				}
				
				Task task = new Task(name, startDate, endDate, time);
				try {
					schedule.getUser().createTask(task, project);
					view.resetErrorLabels();
				} catch (Exception error) {
					System.err.println(error);
					view.getCreateTaskPanel().setErrorLabel(error.getMessage());
					break;
				}
				
				task.setDescription(view.getCreateTaskPanel().getTaskDescription());
				view.getManageProjectPanel().updateList(project);
				
				view.remove(view.getCreateTaskPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.remove(view.getCreateTaskPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;
	
		}
	}
	
}
