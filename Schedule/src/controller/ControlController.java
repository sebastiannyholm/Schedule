package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.OperationNotAllowedException;
import model.Schedule;
import view.View;

public class ControlController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	public ControlController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		view.getControlPanel().registerListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		
			case "Absence":
				view.remove(view.getControlPanel());
				view.add(view.getAbsencePanel());
				view.reset();
				break;
				
			case "My agenda":
				view.remove(view.getControlPanel());
				view.add(view.getAgendaPanel());
				view.reset();
				break;
				
			case "My projects":
				view.remove(view.getControlPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;
				
			case "Log out":
				try {
					schedule.logOut();
				} catch (OperationNotAllowedException error) {
					System.err.println(error.getMessage());
				}
				view.remove(view.getControlPanel());
				view.add(view.getLogInPanel());
				view.reset();
				break;
			
		}	
		
	}

}
