package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;
import model.Task;

public class WorkingController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	
	public WorkingController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getWorkingPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String timeSpent = "";
		int time = 0;
		task = view.getWorkingPanel().getTask();
		
		switch (e.getActionCommand()) {
			case "Start timer":
				view.getWorkingPanel().setStartWork(false);
				view.getWorkingPanel().setStopWork(true);
				schedule.getUser().startWorkingOnTask(task);
				break;
				
			case "Stop timer":
				schedule.getUser().stopWorkingOnTask(task);
				view.getWorkingPanel().setStartWork(true);
				view.getWorkingPanel().setStopWork(false);
				timeSpent = Integer.toString(schedule.getUser().getTaskLogValue(task));
				view.getWorkingPanel().setTimeSpentLabel(timeSpent);
				break;
				
			case "Change time":
				
				timeSpent = view.getWorkingPanel().getChangeTimeSpent();
				
				try {  
					time = Integer.parseInt(timeSpent);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getWorkingPanel().setErrorLabel("Correct your time");
					break;
				}
				
				schedule.getUser().changeTimeWorkedOnTask(task, time);
				timeSpent = Integer.toString(schedule.getUser().getTaskLogValue(task));
				
				view.getWorkingPanel().setTimeSpentLabel(timeSpent);
				
				break;
				
			case "Back":
				view.remove(view.getWorkingPanel());
				view.add(view.getAgendaPanel());
				view.reset();
				break;

		}
	}
	
}
