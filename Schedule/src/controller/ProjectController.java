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
				view.remove(view.getProjectPanel());
				view.add(view.getCreateProjectPanel());
				view.reset();
				break;
				
			case "Delete project":
				if ( view.getProjectPanel().getSelectedIndex() > -1 ) {
					try {
						schedule.getUser().deleteProject(view.getProjectPanel().getSelected());
					} catch (Exception error) {
						System.err.println(error);
					}
					
					view.getProjectPanel().updateList();
				}
				break;

			case "Manage project":
				if ( view.getProjectPanel().getSelectedIndex() > -1 ) {
					project = view.getProjectPanel().getSelected();
					view.getManageProjectPanel().setProject(project);
					view.getManageProjectPanel().updateList(schedule.getUser().getProjects().indexOf(project));
				} else {
					break;
				}
				
				view.remove(view.getProjectPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;

			case "Back":
				view.remove(view.getProjectPanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
