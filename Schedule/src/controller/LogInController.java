package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;

public class LogInController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Controller controller;
	
	public LogInController(Schedule schedule, View view, Controller controller) {
		this.schedule = schedule;
		this.view = view;
		this.controller = controller;
		
		view.getLogInPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Log in":
			
				try {
					schedule.login(view.getLogInPanel().getInitials());
					view.resetErrorLabels();
				} catch (Exception error) {
					view.getLogInPanel().setErrorLabel(error.getMessage());
				}
				
				if (schedule.isLoggedIn()) {
					view.setUpPanels();
					controller.setUpController();
					view.getLogInPanel().setErrorLabel("");
					view.remove(view.getLogInPanel());
					view.add(view.getControlPanel());
					view.reset();
				} else {
					view.getLogInPanel().setErrorLabel("Wrong initials");
				}
				break;
				
			case "Shut down":
				System.exit(0);
				break;

		}
	}
	
}
