package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import view.View;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Assignment;

public class WorkingController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	private Assignment assignment;
	
	private String timeString = "", hourInDayString = "";
	private int hourInDay;
	private Calendar startDate;
	private Employee employee;
	private TickTimeController tickTimeController;
	
	public WorkingController(Schedule schedule, View view, TickTimeController tickTimeController) {
		this.schedule = schedule;
		this.view = view;
		this.tickTimeController = tickTimeController;
		
		view.getWorkingPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int time = 0;
		task = view.getWorkingPanel().getTask();
		assignment = view.getWorkingPanel().getAssignment();
		
		switch (e.getActionCommand()) {
			case "Start timer":
				if (tickTimeController.getTimer().isRunning()) {
					view.getWorkingPanel().setErrorLabel("Another task is running");
					break;
				}
					
				view.getWorkingPanel().setStartWork(false);
				view.getWorkingPanel().setChangeTimeSpent(false);
				view.getWorkingPanel().setStopWork(true);
				schedule.getUser().startWorkingOnAssignment(assignment);
				tickTimeController.startTickTimer();
				break;
				
			case "Stop timer":
				schedule.getUser().stopWorkingOnAssignment(assignment);
				tickTimeController.stopTickTimer();
				view.getWorkingPanel().setStartWork(true);
				view.getWorkingPanel().setStopWork(false);
				view.getWorkingPanel().setChangeTimeSpent(true);
				break;
				
			case "Find employees":
				timeString = view.getWorkingPanel().getTimeText();
				hourInDayString = view.getWorkingPanel().getHourInDayText();
				
				if (view.getWorkingPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getWorkingPanel().getStartDate());
				}

				if (startDate == null) {
					view.getWorkingPanel().setErrorLabel("Please set a start date");
					break;
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getWorkingPanel().setErrorLabel("Correct your hour in day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getWorkingPanel().setErrorLabel("Correct your time");
					break;
				}
				
				startDate.set(Calendar.HOUR_OF_DAY, hourInDay);
				startDate.set(Calendar.MINUTE,0);
				startDate.set(Calendar.SECOND,0);
				
				if (schedule.getUser().getFreeEmployeesInPeriod(startDate, time).size() == 0) {
					view.getWorkingPanel().setErrorLabel("No employees available");
					break;
				}
				
				view.getWorkingPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				
				break;
				
			case "Add assistence":
				timeString = view.getWorkingPanel().getTimeText();
				hourInDayString = view.getWorkingPanel().getHourInDayText();
				
				if (view.getWorkingPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getWorkingPanel().getStartDate());
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getWorkingPanel().setErrorLabel("Correct your hour in day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getWorkingPanel().setErrorLabel("Correct your time");
					break;
				}
				
				if (startDate == null) {
					view.getWorkingPanel().setErrorLabel("Please set a start date");
					break;
				}
				
				startDate.set(Calendar.HOUR_OF_DAY, hourInDay);
				startDate.set(Calendar.MINUTE,0);
				startDate.set(Calendar.SECOND,0);
				
				if ( view.getWorkingPanel().getSelectedIndex() > -1 ) {
					employee = view.getWorkingPanel().getSelected();
					task = view.getWorkingPanel().getTask();
					try {
						schedule.getUser().requireAssistance(employee, task, startDate, time*60);
					} catch (Exception error) {
						view.getWorkingPanel().setErrorLabel(error.getMessage());
						break;
					}
					view.resetErrorLabels();
					employee.getTasksAndTime().get(task).getLast().setDescription(assignment.getDescription());
					view.getWorkingPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				} else {
					view.getWorkingPanel().setErrorLabel("Choose an employee");
				}
				
				break;
				
			case "Change time":
				view.getWorkingPanel().setStartWork(false);
				view.getWorkingPanel().addChangeTime();
				view.reset();
				break;
				
			case "Save time":
				
				String hoursString = view.getWorkingPanel().getChangeHoursSpentText();
				String minutesString = view.getWorkingPanel().getChangeMinutesSpentText();
				String secondsString = view.getWorkingPanel().getChangeSecondsSpentText();
				
				if (hoursString.equals("") || minutesString.equals("") || secondsString.equals("")) {
					view.getWorkingPanel().setErrorLabelTime("Inset a correct time");
					break;
				}
					
				int hours = 0, minutes = 0, seconds = 0;
				
				try {
					hours = Integer.parseInt(hoursString);
					minutes = Integer.parseInt(minutesString);
					seconds = Integer.parseInt(secondsString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getWorkingPanel().setErrorLabelTime("Only numbers!");
					break;
				}
				
				schedule.getUser().changeTimeWorkedOnAnAssignment(assignment, hours * 60 * 60 + minutes * 60 + seconds);
				
				view.getWorkingPanel().setTimeSpent();
				view.getWorkingPanel().removeChangeTime();
				view.getWorkingPanel().setStartWork(true);
				view.reset();
				
				if (assignment.limitExceeded())
					view.getWorkingPanel().setErrorLabelTime("You have exceeded your time limit!");
				else 
					view.getWorkingPanel().setErrorLabelTime("");
				
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.getWorkingPanel().removeChangeTime();
				if (!tickTimeController.getTimer().isRunning())
					view.getWorkingPanel().setStartWork(true);
				view.remove(view.getWorkingPanel());
				view.add(view.getAgendaPanel());
				view.reset();
				break;

		}
	}
	
}
