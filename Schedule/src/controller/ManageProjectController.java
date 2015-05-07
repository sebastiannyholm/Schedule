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
		
		switch (e.getActionCommand()) {
			case "Create task":
				view.resetErrorLabels();
				view.getCreateTaskPanel().setProject(project);
				view.remove(view.getManageProjectPanel());
				view.add(view.getCreateTaskPanel());
				view.reset();
				break;
				
			case "Delete task":
				if ( view.getManageProjectPanel().getSelectedIndex() > -1 ) {
					task = view.getManageProjectPanel().getSelected();
					try {
						schedule.getUser().deleteTask(task, project);
						view.resetErrorLabels();
					} catch (Exception error) {
						view.getManageProjectPanel().setErrorLabel(error.getMessage());
					}
					
					view.getManageProjectPanel().updateList(project);
				}
				else
					view.getManageProjectPanel().setErrorLabel("Please select a task");
				break;

			case "Manage task":
				if ( view.getManageProjectPanel().getSelectedIndex() > -1 ) {
					view.resetErrorLabels();					
					task = view.getManageProjectPanel().getSelected();
					task = view.getManageProjectPanel().getSelected();
					view.getManageTaskPanel().setTask(task);
					view.getManageTaskPanel().setTitleLabel(task.getName());
					view.getManageTaskPanel().updateList();
					view.getManageTaskPanel().updateAssistenceList();
				} else {
					view.getManageProjectPanel().setErrorLabel("Please select a task");
					break;
				}
				
				view.remove(view.getManageProjectPanel());
				view.add(view.getManageTaskPanel());
				view.reset();
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.remove(view.getManageProjectPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;

		}
	}
	
}
