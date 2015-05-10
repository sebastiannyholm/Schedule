package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.View;
import model.Schedule;

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
		view.getWorkingPanel().setChangeHoursSpentText(view.getWorkingPanel().getAssignment().getHoursSpentString());
		view.getWorkingPanel().setChangeMinutesSpentText(view.getWorkingPanel().getAssignment().getMinutesSpentString());
		view.getWorkingPanel().setChangeSecondsSpentText(view.getWorkingPanel().getAssignment().getSecondsSpentString());
		view.getWorkingPanel().setTimeSpentLabelText(view.getWorkingPanel().getAssignment().getTimeSpentString());
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
