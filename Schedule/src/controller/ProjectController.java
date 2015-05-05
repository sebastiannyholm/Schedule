package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;

public class ProjectController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	public ProjectController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getProjectPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Create project":
				break;
				
			case "Delete project":
				break;

			case "Manage project":
				break;

			case "Back":
				view.remove(view.getProjectPanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
