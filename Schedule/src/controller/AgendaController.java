package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;
import model.Task;

public class AgendaController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	
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
					task = view.getAgendaPanel().getSelected().getTask();
					view.getWorkingPanel().setTask(task);
					view.getWorkingPanel().setTitleLabel(task.getName());
					
					if (schedule.getUser().getTaskLog().containsKey(task)) {
						String timeSpent = Integer.toString(schedule.getUser().getTaskLogValue(task));
						view.getWorkingPanel().setTimeSpentLabel(timeSpent);
					} else {
						view.getWorkingPanel().setTimeSpentLabel("0");
					}
					
					view.remove(view.getAgendaPanel());
					view.add(view.getWorkingPanel());
					view.reset();
				}
				break;
				
			case "Back":
				view.remove(view.getAgendaPanel());
				view.add(view.getControlPanel());
				view.reset();
				break;
		}
	}
	
}
