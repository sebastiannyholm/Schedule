package controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import view.View;
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
		final Employee ABSENCE_MANAGER = new Employee("Absence Manager", "abma", 100, address, schedule);
		final Project ABSENCE = new Project("Absence", startupDate, terminationDate, ABSENCE_MANAGER);
		final Task SICKNESS = new Task("Sickness", startupDate, terminationDate, 99999999);
		final Task VACATION = new Task("Vacation", startupDate, terminationDate, 99999999);
		final Task COURSE = new Task("Course", startupDate, terminationDate, 9999999);
		
		final Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		final Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		employee1.setAdmin(true);
		
		final Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		final Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		employee2.setAdmin(true);
		
		schedule.addEmployee(ABSENCE_MANAGER);
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		schedule.login("abma");
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
		
		View view = new View(schedule);
		
		Controller controller = new Controller(schedule, view);
		
		new ScanController(schedule);
		
	}
}
