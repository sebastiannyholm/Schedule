package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;

public class AbsenceController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	public AbsenceController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getAbsencePanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Add employee":
				break;
				
			case "Back":
				view.remove(view.getAbsencePanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
