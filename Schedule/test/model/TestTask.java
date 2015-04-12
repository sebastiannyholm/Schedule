package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestTask {
	
	Schedule schedule = new Schedule();
	
	@Before
	public void setup() throws OperationNotAllowedException{
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		Project project = new Project("ProjectAwesome", 1, 5, employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		employee2.createProject(project);
		
	}
	
	@Test
	public void addTask() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		Employee employee = schedule.getEmployees().get(1);		// list of 2
		
		Task task = new Task("taskName", 1, 5, 8, 37*(8-5));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		employee.addTask(task, project);
		assertEquals(1,project.getTasks().size());
		
		
	}
	
	@Test
	public void addTaskFailed() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		Employee employee = schedule.getEmployees().get(0);		// list of 2
		
		Task task = new Task("taskName", 1, 5, 8, 37*(8-5));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		try {
			employee.addTask(task, project);
		} catch (OperationNotAllowedException e) {
			assertEquals("Only the project leader may add a task to a project", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0,project.getTasks().size());
		
	}
	
	@Test
	public void addEmployeeToTask() {
		
	}
	
}
