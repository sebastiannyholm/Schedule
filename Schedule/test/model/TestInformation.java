package model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class TestInformation {

	Schedule schedule = new Schedule();
	Employee user;
	Project project;
	Task task;
	
	@Before
	public void setup() throws Exception {
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		project = new Project("ProjectAwesome", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), employee);		//projectName, projectNumber, totalTime (in weeks), project leader
		task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		
		schedule.addEmployee(employee);
		
		schedule.login("seny");
		user = schedule.getUser();
		
		user.createProject(project);
		user.createTask(task, project);
		
	}
	
	@Test
	public void checkDescriptions() {
		
		user.setProjectDescription("Project description", project);
		user.setTaskDescription("Task description", task);
		
		assertEquals("Project description", project.getDescription());
		assertEquals("Task description", task.getDescription());
		
	}

}
