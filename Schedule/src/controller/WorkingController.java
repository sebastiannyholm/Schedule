package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import view.View;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Timer;

public class WorkingController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	private Timer timer;
	
	private String timeString = "", hourInDayString = "";
	private int time, hourInDay;
	private Calendar startDate;
	private Employee employee;
	
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
		timer = view.getWorkingPanel().getTimer();
		
		switch (e.getActionCommand()) {
			case "Start timer":
				view.getWorkingPanel().setStartWork(false);
				view.getWorkingPanel().setStopWork(true);
				schedule.getUser().startWorkingOnTask(timer);
				break;
				
			case "Stop timer":
				schedule.getUser().stopWorkingOnTask(timer);
				view.getWorkingPanel().setStartWork(true);
				view.getWorkingPanel().setStopWork(false);
				timeSpent = Integer.toString(schedule.getUser().getTaskLogValue(timer));
				view.getWorkingPanel().setTimeSpentLabel(timeSpent);
				break;
			
			case "Find employees":
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
						view.resetErrorLabels();
					} catch (Exception error) {
						view.getWorkingPanel().setErrorLabel(error.getMessage());
					}
					
					view.getWorkingPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				} else {
					view.getWorkingPanel().setErrorLabel("Choose an employee");
				}
				
				
				break;
				
			case "Change time":
				
				timeSpent = view.getWorkingPanel().getChangeTimeSpent();
				
				try {  
					time = Integer.parseInt(timeSpent);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getWorkingPanel().setErrorLabel("Correct your time");
					break;
				}
				
				schedule.getUser().changeTimeWorkedOnTask(timer, time);
				timeSpent = Integer.toString(schedule.getUser().getTaskLogValue(timer));
				
				view.getWorkingPanel().setTimeSpentLabel(timeSpent);
				if (timer.limitExceeded())
					view.getWorkingPanel().setWorkedToMuch("You have exceeded your time limit!");
				
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.remove(view.getWorkingPanel());
				view.add(view.getAgendaPanel());
				view.reset();
				break;

		}
	}
	
}
