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
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		employee1.setAdmin(true);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		employee2.setAdmin(true);
		
		Address address3 = new Address("Venlighedsvej", 32, 3800, "Horsholm");					//street, streetNumber, zipCode, city
		Employee employee3 = new Employee("Anders Jensen", "anje", 22, address3, schedule);		// name, initials, age, address, schedule
		
		Address address4 = new Address("Kollegiebakken", 9, 2800, "Lyngby");					//street, streetNumber, zipCode, city
		Employee employee4 = new Employee("Louise Bak", "loba", 22, address4, schedule);		// name, initials, age, address, schedule
		
		Project project1 = new Project("Ventiba", new GregorianCalendar(2015, Calendar.MAY, 1), new GregorianCalendar(2015, Calendar.JULY, 29), employee1);		//projectName, projectNumber, totalTime (in weeks), project leader
		Project project2 = new Project("Leg og Sjov", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 20), employee1);
		Project project3 = new Project("Snukas", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.AUGUST, 31), employee2);
		
		Task task11 = new Task("Regnskabssystem", new GregorianCalendar(2015, Calendar.MAY, 1), new GregorianCalendar(2015, Calendar.JUNE, 30), 500);	// name, number, startWeek, endWeek, budgetedHours
		Task task12 = new Task("Hjemmeside", new GregorianCalendar(2015, Calendar.MAY, 1), new GregorianCalendar(2015, Calendar.JUNE, 30), 400);
		Task task21 = new Task("Kalendersystem", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 15), 300);
		Task task22 = new Task("Hjemmeside", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 5), 300);
		Task task23 = new Task("Brugersystem", new GregorianCalendar(2015, Calendar.APRIL, 5), new GregorianCalendar(2015, Calendar.JUNE, 20), 300);
		Task task31 = new Task("Postsystem", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JULY, 30), 500);
		
		schedule.addEmployee(ABSENCE_MANAGER);
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		schedule.addEmployee(employee3);
		schedule.addEmployee(employee4);
		
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
		employee1.changeTimeWorkedOnAnAssignment(employee1.getTasksAndTime().get(task11).get(0), 18*60*60);
		employee1.addEmployeeToTask(employee1, task12, new GregorianCalendar(2015, Calendar.MAY, 5, 10, 0), 4*60);
		employee1.changeTimeWorkedOnAnAssignment(employee1.getTasksAndTime().get(task12).get(0), 4*60*60);
		employee1.addEmployeeToTask(employee1, task21, new GregorianCalendar(2015, Calendar.MAY, 8, 8, 0), 10*60);
		employee1.changeTimeWorkedOnAnAssignment(employee1.getTasksAndTime().get(task21).get(0), 8*60*60);
		employee1.addEmployeeToTask(employee1, task22, new GregorianCalendar(2015, Calendar.MAY, 11, 10, 0), 4*60);
		employee1.addEmployeeToTask(employee1, task23, new GregorianCalendar(2015, Calendar.MAY, 11, 14, 0), 80*60);
		employee1.addEmployeeToTask(employee3, task11, new GregorianCalendar(2015, Calendar.MAY, 11, 8, 0), 18*60);
		employee1.addEmployeeToTask(employee4, task11, new GregorianCalendar(2015, Calendar.MAY, 8, 8, 0), 30*60);
		employee4.changeTimeWorkedOnAnAssignment(employee4.getTasksAndTime().get(task11).get(0), 8*60*60);
		employee1.addEmployeeToTask(employee4, task12, new GregorianCalendar(2015, Calendar.MAY, 13, 14, 0), 60*60);
		
		// Set assignment descriptions
		employee1.getTasksAndTime().get(task11).get(0).setDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus");
		employee1.getTasksAndTime().get(task12).get(0).setDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus");
		employee1.getTasksAndTime().get(task21).get(0).setDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus");
		employee1.getTasksAndTime().get(task22).get(0).setDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus");
		employee1.getTasksAndTime().get(task23).get(0).setDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus");
		schedule.logOut();
		
		schedule.login("luvi");
		employee2.createProject(project3);
		employee2.createTask(task31, project3);
		
		employee2.setProjectDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", project3);
		employee2.addEmployeeToTask(employee2, task31, new GregorianCalendar(2015, Calendar.MAY, 11, 8, 0), 200*60);
		employee2.addEmployeeToTask(employee3, task31, new GregorianCalendar(2015, Calendar.MAY, 13, 10, 0), 40*60);

		// Set assignment descriptions
		employee2.setTaskDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus", task31);
		employee2.getTasksAndTime().get(task31).get(0).setDescription("Lorem ipsum dolor sit amet, magnis ultricies cursus lorem dolor. Diam aenean morbi, nibh praesent at. Ut amet ante, consectetuer in pellentesque non, optio etiam curabitur, arcu purus");
		schedule.logOut();
		
		schedule.login("anje");
		employee3.requireAssistance(ABSENCE_MANAGER, task31, new GregorianCalendar(2015, Calendar.MAY, 13, 14, 0), 10*60);
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
