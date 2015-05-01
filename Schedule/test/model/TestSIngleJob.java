package model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class TestSIngleJob {

	Schedule schedule = new Schedule();
	Employee user;
	
	@Before
	public void setup() throws Exception{
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		schedule.login("seny");
		user = schedule.getUser();
		
		Project project = new Project("ProjectAwesome", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), employee1);		//projectName, projectNumber, totalTime (in weeks), project leader
		user.createProject(project);
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 5), 37);	// name, number, startWeek, endWeek, budgetedHours
		user.createTask(task, project);
		
		user.addEmployeeToTask(employee2, task);				// main focus here, adds an employee to the created task
	}
	
	@Test
	public void createSingleJob() {
		SingleJob job = new SingleJob(new GregorianCalendar(2015,Calendar.JANUARY,1,8,0), new GregorianCalendar(2015,Calendar.JANUARY,1,12,0));
		
		Employee employee = schedule.searchEmployee("luvi").get(0);
		
		Task task = schedule.getAllTasks().get(0);
		
		assertEquals(0, task.getJobs().size());
		assertEquals(0, employee.getJobs().size());
		
		user.addEmployeeToTaskJob(employee, task, job);
		
		assertEquals(1, task.getJobs().size());
		assertEquals(1, employee.getJobs().size());
		
		assertEquals(1, task.getJobsForAnEmployee(employee).size());
		assertEquals(1, employee.getJobsForATask(task).size());
	
	}

}
