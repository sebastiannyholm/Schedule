package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestTask {
	
	Schedule schedule = new Schedule();
	
	@Before
	public void setup() throws Exception{
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
		Employee projectLeader = schedule.getEmployees().get(1);		// list of 2
		
		Task task = new Task("taskName", 1, 2, 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		projectLeader.addTask(task, project);
		assertEquals(1,project.getTasks().size());
		
		
	}
	
	@Test
	public void addTaskFailed() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		Employee employee = schedule.getEmployees().get(0);		// list of 2
		
		Task task = new Task("taskName", 1, 2, 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		try {
			employee.addTask(task, project);
		} catch (OperationNotAllowedException e) {
			assertEquals("Only the project leader may add a task to a project", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0,project.getTasks().size());
		
	}
	
	/* Task exeeding project limits/bounds
	 * A task with a startWeek after the project has expired or
	 * a task with an endWeek before the project has begun or
	 * a task with an endWeek after the project has expired or
	 * a task with a startWeek before the project has begun,
	 * cannot be applied to the project
	 */
	@Test
	public void addTaskOutOfBounds() throws Exception{
		Project project = schedule.getAllProjects().get(0);				// list of 1
		Employee projectLeader = schedule.getEmployees().get(1);		// list of 2
		
		Task task = new Task("taskName", 5, 8, 37*(8-5));		// name, number, startWeek, endWeek, budgetedHours - OUT OF BOUNDS
		
		assertEquals(0,project.getTasks().size());
		try {
			projectLeader.addTask(task, project);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Task span does not comply with project bounds!", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0,project.getTasks().size());
	}
	
	// cannot have a task where it ends before it begins!
	@Test
	public void addTaskEndBeforeStart() throws Exception{
		Project project = schedule.getAllProjects().get(0);				// list of 1
		Employee projectLeader = schedule.getEmployees().get(1);		// list of 2
		
		Task fineTask =  new Task("Fine task", 3, 4, 37*(4-3));
		Task badTask = new Task("Bad task", 4, 1, 37*(1-4));		// name, number, startWeek, endWeek, budgetedHours - 
																	// begins before it begins with negative budget time..
		projectLeader.addTask(fineTask, project);
		assertEquals(1,project.getTasks().size());
		
		try {
			projectLeader.addTask(badTask, project);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Task ends before it even begins!", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(1,project.getTasks().size());
	}
		
	/*
	 * While it is usually assumed that a task cannot have
	 * an endWeek which is lower than a startWeek
	 * when the startWeek of the task is sufficiently high (close to 52)
	 * it is allowed for the endWeek to be lower than the startWeek.
	 * From the assignment: a task usually has a length of 2-3 weeks
	 * As a safety net for unusually long tasks, we allow endWeeks
	 * to be lower than startWeeks with a week number of 45 or greater.
	 */
	
	@Test
	public void addTaskOverlapNewYear() throws Exception{
		Project project = schedule.getAllProjects().get(0);				// list of 1
		Employee projectLeader = schedule.getEmployees().get(1);		// list of 2
		
		// the task which is usually bad but has a high startWeek number (50)
		Task exBadTask =  new Task("task", 50, 2, 37*(52+2-50));	
		
		assertEquals(0,project.getTasks().size());
		
		projectLeader.addTask(exBadTask, project);
	
		assertEquals(1,project.getTasks().size());
	}
	
	// cannot add a task which has negative week number or exceed 52
	@Test
	public void addTaskHighLowWeek() throws Exception{
		Project project = schedule.getAllProjects().get(0);				// list of 1
		Employee projectLeader = schedule.getEmployees().get(1);		// list of 2
		
		Task badTaskHigh = new Task("task", 56, 2, 37*(52+2-56));
		
		assertEquals(0,project.getTasks().size());
		try {
			projectLeader.addTask(badTaskHigh, project);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Task span does not comply with project bounds!", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0, project.getTasks().size());
		
	}
	
	@Test
	public void addEmployeeToTask() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
		Employee employee = schedule.getEmployees().get(0);		
		Task task = new Task("taskName", 1, 2, 37*(2-1));	// name, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task, project);
		
		// task knows which employees are working on it
		// and the employee knows which tasks they are working on
		assertEquals(0, employee.getTasks().size());
		assertEquals(0, task.getEmployees().size());	
		
		projectLeader.addEmployeeToTask(employee, task);				// main focus here, adds an employee to the created task
		
		assertEquals(1, employee.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
		// add more than 1 employee to the task, here the project leader adds him-/herself to the task
		projectLeader.addEmployeeToTask(projectLeader, task);
		
		assertEquals(2, task.getEmployees().size());
	}
	
	// If an employee is already working on one activity, he/she cannot be assigned the very same activity.
	@Test
	public void addEmployeeToTaskAlreadyWorkingOnIt() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
		Employee employee = schedule.getEmployees().get(0);		
		Task task = new Task("taskName", 1, 2, 37*(2-1));	// name, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task, project);
		projectLeader.addEmployeeToTask(employee, task);
		
		assertEquals(1, employee.getTasks().size());
		
		try{
			projectLeader.addEmployeeToTask(employee, task);		// add another instance of the task to the employee
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e){
			assertEquals("The employee " + employee + " is already working on this task!", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		assertEquals(1, employee.getTasks().size());
	}
	
	// add more than 10 tasks to an employee who isn't allowed to work on 20
	// default max task = 10
	
	@Test
	public void tenTaskEmployee() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
		
		for (int i = 0; i < 10; i++){
			//add 10 tasks to the project leader
			Task task = new Task("task"+i, 1, 2, 37*(2-1));
			projectLeader.addTask(task, project);
			projectLeader.addEmployeeToTask(projectLeader, task);
		}
		
		assertEquals(10, projectLeader.getTasks().size());
		// add another tasks to the project leader -- file an error
		Task task = new Task("taskName", 2, 3, 37*(3-2));				// name, startWeek, endWeek, budgetedHours
		projectLeader.addTask(task, project); // add the task to the project
		
		try{
			projectLeader.addEmployeeToTask(projectLeader, task);
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("The employee " + projectLeader + " is already working on the maximum amount of tasks!", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(10, projectLeader.getTasks().size());
	}
	
	// employee allowed to work on more than 10 tasks but not more than 20
	@Test
	public void twentyTaskEmployee() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
		
		for (int i = 0; i < 10; i++){
			//add 10 tasks to the project leader
			Task task = new Task("task"+i, 1, 2, 37*(2-1));
			projectLeader.addTask(task, project);
			projectLeader.addEmployeeToTask(projectLeader, task);
		}
		
		assertEquals(10, projectLeader.getTasks().size());
		
		projectLeader.setSuperWorker(true);
		// add another tasks to the project leader -- now allowed because super worker
		Task task11 = new Task("taskName", 1, 2, 37*(2-1));				// name, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task11, project); // add the task to the project
		projectLeader.addEmployeeToTask(projectLeader, task11);
		
		assertEquals(11, projectLeader.getTasks().size());

		// adding more than 20 tasks even though super worker
		for (int i = 0; i < 9; i++){
			//add 9 tasks to the project leader
			Task task2 = new Task("task"+i, 1, 2, 37*(2-1));
			projectLeader.addTask(task2, project);
			projectLeader.addEmployeeToTask(projectLeader, task2);
		}
		
		assertEquals(20, projectLeader.getTasks().size());
		// add another tasks to the project leader -- not allowed even though super worker
		Task task21 = new Task("taskName", 2, 3, 37*(3-2));				// name, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task21, project); // add the task to the project
		
		try{
			projectLeader.addEmployeeToTask(projectLeader, task21);
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("The employee " + projectLeader + " is already working on the maximum amount of tasks!", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(20, projectLeader.getTasks().size());
		
	}
	
	@Test
	public void removeTask() throws Exception{
		Project project = schedule.getAllProjects().get(0);		
		Employee projectLeader = schedule.getEmployees().get(1);		
		
		Task task = new Task("taskName", 2, 4, 37*(4-2));	// name, number, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task, project);
		projectLeader.addEmployeeToTask(projectLeader, task);
		
		assertEquals(1, projectLeader.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
		projectLeader.removeTask(task);

		assertEquals(0, projectLeader.getTasks().size());
		assertEquals(0, task.getEmployees().size());
		
	}
	
	// remove a tasks which the employee is not working on
	@Test
	public void removeTaskNonExist() throws Exception{
		Project project = schedule.getAllProjects().get(0);		
		Employee projectLeader = schedule.getEmployees().get(1);		
		
		Task task = new Task("taskName", 1, 2, 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		Task task2 = new Task("taskName2", 2, 3, 37*(3-2));	// name, number, startWeek, endWeek, budgetedHours
		
		projectLeader.addTask(task, project);
		projectLeader.addEmployeeToTask(projectLeader, task);
		
		assertEquals(1, projectLeader.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
		projectLeader.removeTask(task2);

		assertEquals(1, projectLeader.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
	}	
	
	@Test
	public void removeProjectWithTasks() throws Exception {
		Project project = schedule.getAllProjects().get(0);	
		Employee projectLeader = schedule.getEmployees().get(1);	
		
		for (int i = 0; i < 7; i++){
			//add 10 tasks to the project leader
			Task task = new Task("task"+i, 1, 2, 37*(2-1));
			projectLeader.addTask(task, project);
			projectLeader.addEmployeeToTask(projectLeader, task);
		}
		
		assertEquals(7, projectLeader.getTasks().size());
		
		projectLeader.deleteProject(project);
		
		assertEquals(0, projectLeader.getTasks().size());
		assertEquals(0, projectLeader.getProjects().size());

	}
}
