package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import model.Schedule;
import model.Task;
import model.Assignment;

public class AgendaController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	private Assignment assignment;
	private TickTimeController tickTimeController;
	
	public AgendaController(Schedule schedule, View view, TickTimeController tickTimeController) {
		this.schedule = schedule;
		this.view = view;
		this.tickTimeController = tickTimeController;
		
		view.getAgendaPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Check task":
				if ( view.getAgendaPanel().getSelectedIndex() > -1 ) {
					assignment = view.getAgendaPanel().getSelected();
					task = assignment.getTask();
					view.getWorkingPanel().setAssignment(assignment);
					view.getWorkingPanel().setTask(task);
					view.getWorkingPanel().setTitleLabel(task.getName());
					view.resetErrorLabels();
					
					if (!assignment.getTimer().isRunning())
						view.getWorkingPanel().setTimeSpent();
					
					if (task.getEmployees().contains(schedule.getUser()))
						view.getWorkingPanel().setAddAssistence();
					
					view.getWorkingPanel().checkTimeLimit();
					view.getWorkingPanel().setDescriptionTask(task.getDescription());
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
