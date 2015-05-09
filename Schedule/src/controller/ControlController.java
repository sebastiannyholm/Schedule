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
		
			case "Check absence":
				view.getAbsencePanel().updateListSick();
				view.remove(view.getControlPanel());
				view.add(view.getAbsencePanel());
				view.reset();
				break;
				
			case "My agenda":
				view.getAgendaPanel().updateList();
				view.remove(view.getControlPanel());
				view.add(view.getAgendaPanel());
				view.reset();
				break;
				
			case "My projects":
				view.getProjectPanel().updateList();
				view.remove(view.getControlPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;
				
			case "Manage employees":
				view.getManageEmployeesPanel().updateList();
				view.remove(view.getControlPanel());
				view.add(view.getManageEmployeesPanel());
				view.reset();
				break;
			
			case "Log out":
				try {
					schedule.logOut();
				} catch (OperationNotAllowedException error) {
					view.getControlPanel().setErrorLabel(error.getMessage());
				}
				view.remove(view.getControlPanel());
				view.add(view.getLogInPanel());
				view.reset();
				break;
			
		}	
		
	}

}
