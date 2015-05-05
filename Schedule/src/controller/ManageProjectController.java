package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Project;
import model.Schedule;
import model.Task;

public class ManageProjectController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Project project;
	private Task task;
	
	public ManageProjectController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getManageProjectPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		project = view.getManageProjectPanel().getProject();
		task = view.getManageProjectPanel().getSelected();
		
		switch (e.getActionCommand()) {
			case "Create task":
				view.getCreateTaskPanel().setProject(project);
				view.remove(view.getManageProjectPanel());
				view.add(view.getCreateTaskPanel());
				view.reset();
				break;
				
			case "Delete task":
				if ( view.getManageProjectPanel().getSelectedIndex() > -1 ) {
					try {
						schedule.getUser().deleteTask(task, project);
					} catch (Exception error) {
						System.err.println(error);
					}
					
					view.getManageProjectPanel().updateList(project);
				}
				break;

			case "Manage task":
				if ( view.getManageProjectPanel().getSelectedIndex() > -1 ) {
					task = view.getManageProjectPanel().getSelected();
					view.getManageTaskPanel().setTask(task);
				} else {
					break;
				}
				
				view.remove(view.getProjectPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;
				
			case "Back":
				view.remove(view.getManageProjectPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;

		}
	}
	
}
