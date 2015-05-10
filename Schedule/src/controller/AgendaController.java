package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;
import model.Task;
import model.Timer;

public class AgendaController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	private Timer timer;
	
	public AgendaController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getAgendaPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Check task":
				if ( view.getAgendaPanel().getSelectedIndex() > -1 ) {
					timer = view.getAgendaPanel().getSelected();
					task = timer.getTask();
					view.getWorkingPanel().setTimer(timer);
					view.getWorkingPanel().setTask(task);
					view.getWorkingPanel().setTitleLabel(task.getName());
					
					if (schedule.getUser().getTaskLog().containsKey(task)) {
						String timeSpent = Integer.toString(schedule.getUser().getTaskLogValue(timer));
						view.getWorkingPanel().setTimeSpentLabel(timeSpent);
					} else {
						view.getWorkingPanel().setTimeSpentLabel("0");
					}
					
					if (task.getEmployees().contains(schedule.getUser()))
						view.getWorkingPanel().setAddAssistence();

					if (timer.limitExceeded())
						view.getWorkingPanel().setWorkedToMuch("You have exceeded your time limit!");
					
					view.resetErrorLabels();
					view.remove(view.getAgendaPanel());
					view.add(view.getWorkingPanel());
					view.reset();
				}
				else
					view.getAgendaPanel().setErrorLabel("Please select a task");
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.remove(view.getAgendaPanel());
				view.add(view.getControlPanel());
				view.reset();
				break;
		}
	}
	
}
