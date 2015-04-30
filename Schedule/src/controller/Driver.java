package controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Address;
import model.Employee;
import model.Project;
import model.Schedule;
import model.Task;


public class Driver {

	public static void main(String[] args) throws Exception{
		Schedule schedule = new Schedule();
		
		Calendar startupDate = schedule.getDate();
		Calendar terminationDate = schedule.getDate();
		terminationDate.add(GregorianCalendar.YEAR, 1);
		
		final Address address = new Address("XXXXXX", 0, 9999, "YYYYYY");	
		final Employee ABSENCE_MANAGER = new Employee("Absence Manager", "AbMa", 100, address, schedule);
		final Project ABSENCE = new Project("Absence", startupDate, terminationDate, ABSENCE_MANAGER);
		final Task SICKNESS = new Task("Sickness", startupDate, terminationDate, 0);
		final Task VACATION = new Task("Vacation", startupDate, terminationDate, 0);
		final Task COURSE = new Task("Course", startupDate, terminationDate, 0);
		
		schedule.login("AbMa");
		ABSENCE_MANAGER.createProject(ABSENCE);
		ABSENCE_MANAGER.createTask(SICKNESS, ABSENCE);
		ABSENCE_MANAGER.createTask(VACATION, ABSENCE);
		ABSENCE_MANAGER.createTask(COURSE, ABSENCE);
		schedule.logOut();
		
		/*
		 * Required features and settings completed
		 * Program can now commence
		 * 
		 * Let the user of the program interact
		 */
		
//		View view = new View(model);
//		new Controller(schedule, view);
		
	}
}
