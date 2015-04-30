package model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestTime {

	Schedule schedule = new Schedule();
	Employee user;
	
	DateServer dateServer = mock(DateServer.class);
	Calendar cal = new GregorianCalendar(2015,Calendar.APRIL,10,8,0);
	
	@Before
	public void setup() throws Exception {
		
		schedule.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(cal);
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		Project project1 = new Project("Project1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.DECEMBER, 31), employee1);		//projectName, projectNumber, totalTime (in weeks), project leader
		Project project2 = new Project("Project2", new GregorianCalendar(2015, Calendar.JANUARY, 22), new GregorianCalendar(2015, Calendar.FEBRUARY, 3), employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		Project project3 = new Project("Project3", new GregorianCalendar(2015, Calendar.FEBRUARY, 16), new GregorianCalendar(2015, Calendar.MARCH, 23), employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		schedule.login("seny");
		user = schedule.getUser();
		
		user.createProject(project1);
		user.createProject(project2);
		user.createProject(project3);		
		
	}
	
	@Test
	public void registerTime() throws Exception {
		
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 8*60);
		when(dateServer.getDate()).thenReturn(newCal);
		
		
		// the employee automatically punches out as he logs out
		schedule.logOut(); 			// punches out
		assertEquals(8*60, user.getWorkLogValue(schedule.getDate().getTime()));
		
		// log in another employee
		schedule.login("luvi");
		user = schedule.getUser();
		
		// He works for 5 hours and logs out (5*60 min)
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 5*60 + 8*60);
		when(dateServer.getDate()).thenReturn(newCal);
	
		schedule.logOut();
		
		assertEquals(5*60, user.getWorkLogValue(schedule.getDate().getTime()));
	}
	
	@Test
	public void registerTimeMultipleTimesADay() throws Exception {
		
		// set the time of punchIn
		// skip 5 hours ahead, set time -- hours += 5
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 5*60);
		when(dateServer.getDate()).thenReturn(newCal);
		
		// the employee automatically punches out as he logs out
		schedule.logOut(); 			// punches out
		
		assertEquals(5*60, user.getWorkLogValue(schedule.getDate().getTime()));
		
		// log in the same employee
		schedule.login("seny");
		user = schedule.getUser();
		
		// He works for 3 hours and logs out
		newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 5*60 + 3*60);
		when(dateServer.getDate()).thenReturn(newCal);
		
		schedule.logOut();
		
		assertEquals(8*60, user.getWorkLogValue(schedule.getDate().getTime()));
	}
	
	// test how many and which projects are found in a specific period of time (in weeks)
	@Test
	public void projectsInPeriod() {
		
		Calendar startDate = new GregorianCalendar(2015, Calendar.JANUARY, 8);
		Calendar endDate = new GregorianCalendar(2015, Calendar.JANUARY, 29);
		
		List<Project> projectsInPeriod = schedule.getProjectsInPeriod(startDate, endDate);
		assertEquals(2, projectsInPeriod.size());
	}

	// get the agenda for a given employee
	// agenda being the working schedule for the employee in the current >>week<<

	@Test
	public void employeeAgenda() throws Exception {
		
		Calendar date = schedule.getDate();
		Calendar startDate = (GregorianCalendar) date.clone();
		Calendar endDate = (GregorianCalendar) date.clone();
		
		startDate.add(GregorianCalendar.WEEK_OF_YEAR,-4);
		endDate.add(GregorianCalendar.WEEK_OF_YEAR, 6);
		Project project = new Project("project!!022", startDate, endDate, schedule.getUser());
		
		Calendar endDate2 = (GregorianCalendar) date.clone();
		endDate2.add(GregorianCalendar.WEEK_OF_YEAR, 1);
		Task task1 = new Task("newTask", startDate, endDate2, 200);		// within week agenda
		
		Task task2 = new Task("popTask", date, endDate2, 100);			// within week agenda
		Calendar startDate2 = (GregorianCalendar) date.clone();
		startDate2.add(GregorianCalendar.WEEK_OF_YEAR, 2);
		Calendar endDate3 = (GregorianCalendar) date.clone();
		endDate3.add(GregorianCalendar.WEEK_OF_YEAR, 5);
		
		Task task3 = new Task("badTask", startDate2, endDate3, 80);		// outside week agenda
		
		user.createProject(project);
		user.createTask(task1, project);
		user.createTask(task2, project);
		user.createTask(task3, project);
		user.addEmployeeToTask(user, task1);
		user.addEmployeeToTask(user, task2);
		user.addEmployeeToTask(user, task3);
		
		List<Task> employeeAgenda = user.getAgenda();
		assertEquals(2, employeeAgenda.size());
		
	}
	
	@Test
	public void registerTaskTime() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);
	
		Task task = new Task("name", new GregorianCalendar(2015, Calendar.JANUARY, 22), new GregorianCalendar(2015, Calendar.DECEMBER, 20), 1000);
		
		user.createTask(task, project);
		user.startWorkingOnTask(task);
		
		// work on the task for 270 minutes then stop
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 270);
		when(dateServer.getDate()).thenReturn(newCal);
		
		user.stopWorkingOnTask(task);
		
		assertEquals(270, user.getTaskLogValue(task));
		
	}
	
	// add working time to an already active task (the employee has already worked on it before)
	@Test
	public void registerTaskTimeMulitple() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);
		Task task = new Task("name", new GregorianCalendar(2015, Calendar.JANUARY, 22), new GregorianCalendar(2015, Calendar.DECEMBER, 20), 1000);
		
		user.createTask(task, project);
		user.startWorkingOnTask(task);
		
		// work on the task for 270 minutes then stop
		Calendar newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 270);
		when(dateServer.getDate()).thenReturn(newCal);
		
		user.stopWorkingOnTask(task);
		
		assertEquals(270, user.getTaskLogValue(task));
		
		// begin working on the task again
		user.startWorkingOnTask(task);
		
		// work on the task for 40 minutes then stop
		newCal = new GregorianCalendar();
		newCal.setTime(cal.getTime());
		newCal.add(Calendar.MINUTE, 270 + 40);
		when(dateServer.getDate()).thenReturn(newCal);
		
		user.stopWorkingOnTask(task);
		
		assertEquals(270+40, user.getTaskLogValue(task));
		
	}
	
}
