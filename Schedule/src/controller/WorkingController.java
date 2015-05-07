package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import view.View;
import model.Employee;
import model.Schedule;
import model.Task;

public class WorkingController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Task task;
	
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
			
			case "Find employees":
				timeString = view.getWorkingPanel().getTimeText();
				hourInDayString = view.getWorkingPanel().getHourInDayText();
				
				if (view.getWorkingPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getWorkingPanel().getStartDate());
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getWorkingPanel().setErrorLabel("Correct your hour in day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getWorkingPanel().setErrorLabel("Correct your time");
					break;
				}
				
				if (startDate == null) {
					System.out.println("Set start date!");
					view.getWorkingPanel().setErrorLabel("Set a start date");
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
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getWorkingPanel().setErrorLabel("Correct your hour in day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getWorkingPanel().setErrorLabel("Correct your time");
					break;
				}
				
				if (startDate == null) {
					System.out.println("Set start date!");
					view.getWorkingPanel().setErrorLabel("Set a start date");
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
						System.err.println(error);
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
