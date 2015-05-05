package controller;

import view.View;
import model.Schedule;

public class Controller {

	private Schedule schedule;
	private View view;
	
	public Controller(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		new LogInController(schedule, view, this);
	}
	
	public void setUpController() {
		new ControlController(schedule, view);
		new ProjectController(schedule, view);
		new AbsenceController(schedule, view);
		new CreateProjectController(schedule, view);
		new ManageProjectController(schedule, view);
		new AgendaController(schedule, view);
		new CreateTaskController(schedule, view);
		new ManageTaskController(schedule, view);
		new WorkingController(schedule, view);
	}
	
}
