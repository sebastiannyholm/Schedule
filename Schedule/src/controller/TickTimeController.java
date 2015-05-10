package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Schedule;
import view.View;

public class TickTimeController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Timer timer;

	public TickTimeController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		// make the timer that'll run the game at gameModels speed
		timer = new Timer(1000, this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		view.getWorkingPanel().setChangeHoursSpentText(view.getWorkingPanel().getAssignment().getRegisteredHoursString());
		view.getWorkingPanel().setChangeMinutesSpentText(view.getWorkingPanel().getAssignment().getRegisteredMinutesString());
		view.getWorkingPanel().setChangeSecondsSpentText(view.getWorkingPanel().getAssignment().getRegisteredSecondsString());
		view.getWorkingPanel().setTimeSpentLabelText(view.getWorkingPanel().getAssignment().getRegisteredTimeString());
		view.getWorkingPanel().checkTimeLimit();
	}

	public void startTickTimer() {
		timer.start();
	}
	
	public void stopTickTimer() {
		timer.stop();
	}

	public Timer getTimer() {
		return timer;
	}
	
}
