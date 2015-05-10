package controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Address;
import model.Employee;
import model.Project;
import model.Schedule;
import model.Task;
import view.View;


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
		
		final Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		final Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		employee1.setAdmin(true);
		
		final Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		final Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		employee2.setAdmin(true);
		
		Project project1 = new Project("Ventiba", new GregorianCalendar(2015, Calendar.MAY, 1), new GregorianCalendar(2015, Calendar.JULY, 29), employee1);		//projectName, projectNumber, totalTime (in weeks), project leader
		Project project2 = new Project("Leg og Sjov", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 20), employee1);		//projectName, projectNumber, totalTime (in weeks), project leader
		
		Task task11 = new Task("Regnskabssystem", new GregorianCalendar(2015, Calendar.MAY, 1), new GregorianCalendar(2015, Calendar.JUNE, 30), 500);	// name, number, startWeek, endWeek, budgetedHours
		Task task12 = new Task("Hjemmeside", new GregorianCalendar(2015, Calendar.MAY, 1), new GregorianCalendar(2015, Calendar.JUNE, 30), 400);	// name, number, startWeek, endWeek, budgetedHours
		Task task21 = new Task("Kalendersystem", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 15), 300);	// name, number, startWeek, endWeek, budgetedHoursTask task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		Task task22 = new Task("Hjemmeside", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 5), 300);	// name, number, startWeek, endWeek, budgetedHours
		Task task23 = new Task("Brugersystem", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 20), 300);	// name, number, startWeek, endWeek, budgetedHours
		
		schedule.addEmployee(ABSENCE_MANAGER);
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		schedule.login("abma");
		ABSENCE_MANAGER.createProject(ABSENCE);
		ABSENCE_MANAGER.createTask(SICKNESS, ABSENCE);
		ABSENCE_MANAGER.createTask(VACATION, ABSENCE);
		schedule.logOut();
		
		schedule.login("seny");
		employee1.createProject(project1);
		employee1.createTask(task11, project1);
		employee1.createTask(task12, project1);
		employee1.setProjectDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", project1);
		employee1.setTaskDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", task11);
		employee1.setTaskDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", task12);
		
		employee1.createProject(project2);
		employee1.createTask(task21, project2);
		employee1.createTask(task22, project2);
		employee1.createTask(task23, project2);
		employee1.setProjectDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", project2);
		employee1.setTaskDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", task21);
		employee1.setTaskDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", task22);
		employee1.setTaskDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", task23);
		
		employee1.addEmployeeToTask(employee1, task11, new GregorianCalendar(2015, Calendar.MAY, 1, 8, 0), 18*60);
		employee1.addEmployeeToTask(employee1, task21, new GregorianCalendar(2015, Calendar.MAY, 5, 10, 0), 4*60);
		employee1.addEmployeeToTask(employee1, task22, new GregorianCalendar(2015, Calendar.MAY, 7, 14, 0), 25*60);
		
		schedule.logOut();
		
		/*
		 * Required features and settings completed
		 * Program can now commence
		 * 
		 * Let the user of the program interact
		 */
		
		View view = new View(schedule);
		new Controller(schedule, view);
		
	}
}
