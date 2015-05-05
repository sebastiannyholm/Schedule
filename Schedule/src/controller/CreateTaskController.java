package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;

public class CreateTaskController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	public CreateTaskController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getCreateTaskPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "1":
				break;
				
			case "2":
				break;

		}
	}
	
}
