package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Project;
import model.Schedule;

public class ProjectController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Project project;
	
	public ProjectController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getProjectPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Create project":
				view.resetErrorLabels();
				view.remove(view.getProjectPanel());
				view.add(view.getCreateProjectPanel());
				view.reset();
				break;
				
			case "Delete project":
				if ( view.getProjectPanel().getSelectedIndex() > -1 ) {
					try {
						schedule.getUser().deleteProject(view.getProjectPanel().getSelected());
						view.resetErrorLabels();
					} catch (Exception error) {
						view.getProjectPanel().setErrorLabel(error.getMessage());
					}
					
					view.getProjectPanel().updateList();
					
				}
				else 
					view.getProjectPanel().setErrorLabel("Please select a project");
				break;

			case "Manage project":
				if ( view.getProjectPanel().getSelectedIndex() > -1 ) {
					project = view.getProjectPanel().getSelected();
					view.getManageProjectPanel().setProject(project);
					view.getManageProjectPanel().updateList(project);
					view.getManageProjectPanel().setTitleLabel(project.getName());
					view.getManageProjectPanel().setProjectLeaderLabel(project.getProjectLeader().getName());
					view.getManageProjectPanel().setProjectLeaderText(project.getProjectLeader().getInitials());
					view.getManageProjectPanel().setDescriptionLabel(project.getDescription());
					view.getManageProjectPanel().setDescriptionText(project.getDescription());
					view.getManageProjectPanel().setStartDate(project.getStartDate());
					view.getManageProjectPanel().setEndDate(project.getEndDate());
				} else {
					view.getProjectPanel().setErrorLabel("Please select a project");
					break;
				}
				
				view.remove(view.getProjectPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;

			case "Back":
				view.resetErrorLabels();
				view.remove(view.getProjectPanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
