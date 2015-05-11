package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import view.View;
import model.Assignment;
import model.Employee;
import model.Schedule;
import model.Task;

public class CreateAssignmentController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	private String timeString = "", hourInDayString = "";
	private int time, hourInDay;
	private Calendar startDate;
	private Employee employee;
	private Task task;
	
	public CreateAssignmentController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getCreateAssignmentPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Find employees":
				timeString = view.getCreateAssignmentPanel().getTimeText();
				hourInDayString = view.getCreateAssignmentPanel().getHourInDayText();
				
				if (view.getCreateAssignmentPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getCreateAssignmentPanel().getStartDate());
				}
				
				if (startDate == null) {
					view.getCreateAssignmentPanel().setErrorLabel("Please set a start date");
					break;
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getCreateAssignmentPanel().setErrorLabel("Correct your hour of day");
					System.err.println(error);
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getCreateAssignmentPanel().setErrorLabel("Correct your time");
					break;
				}
				
				startDate.set(Calendar.HOUR_OF_DAY, hourInDay);
				startDate.set(Calendar.MINUTE,0);
				startDate.set(Calendar.SECOND,0);
				
				view.getCreateAssignmentPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				
				break;
				
			case "Create Assignment":
				
				timeString = view.getCreateAssignmentPanel().getTimeText();
				hourInDayString = view.getCreateAssignmentPanel().getHourInDayText();
				
				if (view.getCreateAssignmentPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getCreateAssignmentPanel().getStartDate());
				}
				
				if (startDate == null) {
					view.getCreateAssignmentPanel().setErrorLabel("Please set a start date");
					break;
				}
				
				try {
					hourInDay = Integer.parseInt(hourInDayString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getCreateAssignmentPanel().setErrorLabel("Correct your hour of day");
					break;
				}
				
				try {  
					time = Integer.parseInt(timeString);
					view.resetErrorLabels();
				} catch(NumberFormatException error) {
					view.getCreateAssignmentPanel().setErrorLabel("Correct your time");
					break;
				}
				
				startDate.set(Calendar.HOUR_OF_DAY, hourInDay);
				startDate.set(Calendar.MINUTE,0);
				startDate.set(Calendar.SECOND,0);
				
				if ( view.getCreateAssignmentPanel().getSelectedIndex() > -1 ) {
					employee = view.getCreateAssignmentPanel().getSelected();
					task = view.getCreateAssignmentPanel().getTask();
					try {
						System.out.println(schedule.getUser());
						schedule.getUser().addEmployeeToTask(employee, task, startDate, time*60);
						view.resetErrorLabels();
					} catch (Exception error) {
						view.getCreateAssignmentPanel().setErrorLabel(error.getMessage());
						break;
					}
					
					view.getCreateAssignmentPanel().updateFindEmployeesList(schedule.getUser().getFreeEmployeesInPeriod(startDate, time));
				} else {
					view.getCreateAssignmentPanel().setErrorLabel("Choose an employee");
					break;
				}
				
				view.resetErrorLabels();					
			
				Assignment assignment = employee.getTasksAndTime().get(task).get(employee.getTasksAndTime().get(task).size()-1);
				assignment.setDescription(view.getCreateAssignmentPanel().getAssignmentDescription());
				view.getManageTaskPanel().updateList();
				view.getManageTaskPanel().updateAssignmentsList();
				view.getManageTaskPanel().setSpentTimeTaskTLabel();
				view.remove(view.getCreateAssignmentPanel());
				view.add(view.getManageTaskPanel());
				view.reset();
				break;
				
			case "Back":
				view.resetErrorLabels();
				
				view.getManageTaskPanel().updateList();
				view.getManageTaskPanel().updateAssignmentsList();
				view.getManageTaskPanel().setSpentTimeTaskTLabel();
				view.remove(view.getCreateAssignmentPanel());
				view.add(view.getManageTaskPanel());
				view.reset();
				break;

		}
	}
	
}
