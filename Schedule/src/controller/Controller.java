package controller;

import view.View;
import model.Schedule;

public class Controller {

	Schedule schedule;
	View view;
	
	public Controller(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
	}
	
}
