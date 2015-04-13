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

		Project project = new Project("ProjectAwesome", 5, employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		employee2.createProject(project);
		
	}
	
	@Test
	public void addTask() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		Employee projectLeader = schedule.getEmployees().get(1);		// list of 2
		
		Task task = new Task("taskName", 5, 8, 37*(8-5));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		projectLeader.addTask(task, project);
		assertEquals(1,project.getTasks().size());
		
		
	}
	
	@Test
	public void addTaskFailed() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		Employee employee = schedule.getEmployees().get(0);		// list of 2
		
		Task task = new Task("taskName", 5, 8, 37*(8-5));	// name, number, startWeek, endWeek, budgetedHours
		
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
	public void addEmployeeToTask() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
		Employee employee = schedule.getEmployees().get(0);		
		Task task = new Task("taskName", 5, 8, 37*(8-5));	// name, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task, project);
		
		// task knows which employees are working on it
		// and the employee knows which tasks they are working on
		assertEquals(0, employee.getTasks().size());
		assertEquals(0, task.getEmployees().size());	
		
		projectLeader.addEmployee(employee, task);				// main focus here, adds an employee to the created task
		
		assertEquals(1, employee.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
		// add more than 1 employee to the task, here the project leader adds him-/herself to the task
		projectLeader.addEmployee(projectLeader, task);
		
		assertEquals(2, task.getEmployees().size());
	}
	
	// add more than 10 tasks to an employee who isn't allowed to work on 20
	// default max task = 10
	
	@Test
	public void tenTaskEmployee() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
	}
	
	// employee allowed to work on more than 10 tasks but not more than 20
	@Test
	public void twentyTaskEmployee() throws Exception {
		
	}
}
