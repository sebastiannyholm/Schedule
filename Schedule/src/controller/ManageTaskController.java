package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import view.View;
import model.Employee;
import model.Schedule;
import model.Task;

public class ManageTaskController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	private String timeString = "", hourInDayString = "";
	private int time, hourInDay;
	private Calendar startDate;
	private Employee employee;
	private Task task;
	
	public ManageTaskController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getManageTaskPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Find employees":
				timeString = view.getManageTaskPanel().getTimeText();
				hourInDayString = view.getManageTaskPanel().getHourInDayText();
				
				if (view.getManageTaskPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getManageTaskPanel().getStartDate());
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getManageTaskPanel().setErrorLabel("Correct your hour in day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getManageTaskPanel().setErrorLabel("Correct your time");
					break;
				}
				
				if (startDate == null) {
					System.out.println("Set start date!");
					view.getManageTaskPanel().setErrorLabel("Set a start date");
					break;
				}
				
				startDate.set(Calendar.HOUR_OF_DAY, hourInDay);
				startDate.set(Calendar.MINUTE,0);
				startDate.set(Calendar.SECOND,0);
				
				view.getManageTaskPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				
				break;
				
			case "Create sub task":
				timeString = view.getManageTaskPanel().getTimeText();
				hourInDayString = view.getManageTaskPanel().getHourInDayText();
				
				if (view.getManageTaskPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getManageTaskPanel().getStartDate());
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getManageTaskPanel().setErrorLabel("Correct your hour in day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getManageTaskPanel().setErrorLabel("Correct your time");
					break;
				}
				
				if (startDate == null) {
					System.out.println("Set start date!");
					view.getManageTaskPanel().setErrorLabel("Set a start date");
					break;
				}
				
				startDate.set(Calendar.HOUR_OF_DAY, hourInDay);
				startDate.set(Calendar.MINUTE,0);
				startDate.set(Calendar.SECOND,0);
				
				if ( view.getManageTaskPanel().getSelectedIndex() > -1 ) {
					employee = view.getManageTaskPanel().getSelected();
					task = view.getManageTaskPanel().getTask();
					try {
						schedule.getUser().addEmployeeToTask(employee, task, startDate, time*60);
					} catch (Exception error) {
						System.err.println(error);
					}
					view.getManageTaskPanel().updateList();
					view.getManageTaskPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				} else {
					view.getManageTaskPanel().setErrorLabel("Choose an employee");
				}
				
				
				break;
				
			case "Back":
				view.getManageTaskPanel().updateFindEmployeesList(new LinkedList<Employee>());
				view.remove(view.getManageTaskPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;

		}
	}
	
}
