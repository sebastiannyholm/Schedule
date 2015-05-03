package model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class TestTask {
	
	Schedule schedule = new Schedule();
	Employee user;
	
	@Before
	public void setup() throws Exception{
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		Project project = new Project("ProjectAwesome", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		schedule.login("luvi");
		user = schedule.getUser();
		
		user.createProject(project);
		
	}
	
	@Test
	public void createTask() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		user.createTask(task, project);
		assertEquals(1,project.getTasks().size());
		
		
	}
	
	// only the project leader can add tasks
	@Test
	public void createTaskFailed() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		schedule.logOut();
		schedule.login("seny");
		user = schedule.getUser();
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		try {
			user.createTask(task, project);
		} catch (OperationNotAllowedException e) {
			assertEquals("Only the project leader may add a task to a project", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0,project.getTasks().size());
		
	}
	
	/* 
	 * Task exeeding project limits/bounds
	 * A task with a startWeek after the project has expired or
	 * a task with an endWeek before the project has begun or
	 * a task with an endWeek after the project has expired or
	 * a task with a startWeek before the project has begun,
	 * cannot be applied to the project
	 */
	@Test
	public void createTaskOutOfBounds() throws Exception{
		Project project = schedule.getAllProjects().get(0);				// list of 1
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 29), new GregorianCalendar(2015, Calendar.FEBRUARY, 16), 37*(8-5));		// name, number, startWeek, endWeek, budgetedHours - OUT OF BOUNDS
		
		assertEquals(0,project.getTasks().size());
		try {
			user.createTask(task, project);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Task span does not comply with project bounds!", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0,project.getTasks().size());
	}
	
	// cannot have a task where it ends before it begins!
	@Test
	public void createTaskEndBeforeStart() throws Exception{
		Project project = schedule.getAllProjects().get(0);				// list of 1
		
		Task fineTask =  new Task("Fine task", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(4-3));
		Task badTask = new Task("Bad task", new GregorianCalendar(2015, Calendar.JANUARY, 17), new GregorianCalendar(2015, Calendar.JANUARY, 15), 37*(1-4));		// name, number, startWeek, endWeek, budgetedHours - 
																	// begins before it begins with negative budget time..
		user.createTask(fineTask, project);
		assertEquals(1,project.getTasks().size());
		
		try {
			user.createTask(badTask, project);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Task ends before it even begins!", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(1,project.getTasks().size());
	}
	
	@Test
	public void addEmployeeToTask() throws Exception {
		Project project = schedule.getAllProjects().get(0);		
		Employee employee = schedule.getEmployees().get(0);		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));	// name, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		
		// task knows which employees are working on it
		// and the employee knows which tasks they are working on
		assertEquals(0, employee.getTasks().size());
		assertEquals(0, task.getEmployees().size());	
		
		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 200);				// main focus here, adds an employee to the created task
		
		assertEquals(1, employee.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
		// add more than 1 employee to the task, here the project leader adds him-/herself to the task
		user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 200);
		
		assertEquals(2, task.getEmployees().size());
	}
	
	// If an employee is already working on one activity, he/she cannot be assigned the very same activity.
	@Test
	public void addEmployeeToTaskAlreadyWorkingOnIt() throws Exception {
		Project project = schedule.getAllProjects().get(0);		
		Employee employee = schedule.getEmployees().get(0);		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));	// name, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 200);
		
		assertEquals(1, employee.getTasks().size());
		
		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 11, 20), 200);		// add another instance of the task to the employee
		
		// the task has not been added to the employee
		assertEquals(1, employee.getTasks().size());
	}
	
	// add more than 10 tasks to an employee who isn't allowed to work on 20
	// default max task = 10
	
	@Test
	public void tenTaskEmployee() throws Exception {
		Project project = schedule.getAllProjects().get(0);		
		
		for (int i = 0; i < 10; i++){
			//add 10 tasks to the project leader
			Task task = new Task("task"+i, new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));
			user.createTask(task, project);
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, i), 1);
		}
		
		assertEquals(10, user.getTasks().size());
		// add another tasks to the project leader -- file an error
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 8), new GregorianCalendar(2015, Calendar.JANUARY, 15), 37*(3-2));				// name, startWeek, endWeek, budgetedHours
		user.createTask(task, project); // add the task to the project
		
		try{
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 10), 200);
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("The employee " + user + " is already working on the maximum amount of tasks!", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(10, user.getTasks().size());
	}
	
	// employee allowed to work on more than 10 tasks but not more than 20
	@Test
	public void twentyTaskEmployee() throws Exception {
		Project project = schedule.getAllProjects().get(0);		
		
		for (int i = 0; i < 10; i++){
			//add 10 tasks to the project leader
			Task task = new Task("task"+i, new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));
			user.createTask(task, project);
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, i), 1);
		}
		
		assertEquals(10, user.getTasks().size());
		
		user.setSuperWorker(true);
		// add another tasks to the project leader -- now allowed because super worker
		Task task11 = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));				// name, startWeek, endWeek, budgetedHours
		
		user.createTask(task11, project); // add the task to the project
		user.addEmployeeToTask(user, task11, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 10), 1);
		
		assertEquals(11, user.getTasks().size());
		
		// adding more than 20 tasks even though super worker
		for (int i = 0; i < 9; i++){
			//add 9 tasks to the project leader
			Task task2 = new Task("task"+i, new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));
			user.createTask(task2, project);
			user.addEmployeeToTask(user, task2, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, i+11), 1);
		}
		
		assertEquals(20, user.getTasks().size());
		// add another tasks to the project leader -- not allowed even though super worker
		Task task21 = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 8), new GregorianCalendar(2015, Calendar.JANUARY, 15), 37*(3-2));				// name, startWeek, endWeek, budgetedHours
		
		user.createTask(task21, project); // add the task to the project
		
		try{
			user.addEmployeeToTask(user, task21, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 21), 100);
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("The employee " + user + " is already working on the maximum amount of tasks!", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(20, user.getTasks().size());
		
	}
	
	@Test
	public void removeTask() throws Exception{
		Project project = schedule.getAllProjects().get(0);				
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 8), new GregorianCalendar(2015, Calendar.JANUARY, 22), 37*(4-2));	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 8, 8, 0), 200);
		
		assertEquals(1, user.getTasks().size());
		assertEquals(1, task.getEmployees().size());
		
		user.deleteTask(task, project);

		assertEquals(0, user.getTasks().size());
		assertEquals(0, task.getEmployees().size());
		
	}
	
//	// remove a tasks which the employee is not working on
//	@Test
//	public void removeTaskNonExist() throws Exception {
//		Project project = schedule.getAllProjects().get(0);				
//		
//		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
//		Task task2 = new Task("taskName2", new GregorianCalendar(2015, Calendar.JANUARY, 8), new GregorianCalendar(2015, Calendar.JANUARY, 15), 37*(3-2));	// name, number, startWeek, endWeek, budgetedHours
//		
//		user.createTask(task, project);
//		user.addEmployeeToTask(user, task, 200);
//		
//		assertEquals(1, user.getTasks().size());
//		assertEquals(1, task.getEmployees().size());
//		
//		try{
//			user.removeTask(task2);
//			fail("OperationNotAllowedException should have been thrown from the above statement");
//		} catch (OperationNotAllowedException e) {
//			assertEquals("You can't remove a task that doesn't exist", e.getMessage());
//			assertEquals("Remove task", e.getOperation());
//		}
//
//		assertEquals(1, user.getTasks().size());
//		assertEquals(1, task.getEmployees().size());
//		
//	}	
	
	@Test
	public void removeProjectWithTasks() throws Exception {
		Project project = schedule.getAllProjects().get(0);		
		
		for (int i = 0; i < 7; i++){
			//add 10 tasks to the project leader
			Task task = new Task("task"+i, new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));
			user.createTask(task, project);
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, i), 1);
		}
		
		assertEquals(7, user.getTasks().size());
		
		user.deleteProject(project);
		
		assertEquals(0, user.getTasks().size());
		assertEquals(0, user.getProjects().size());

	}
	
	@Test
	public void addToMuchTimeToAnEmployeeForAGivenTask() throws Exception {
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		
		try{
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 38*60);
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("You have exceeded the time limit fot the task", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(0, user.getTasks().size());
		
	}

	@Test
	public void addEmployeeToTaskThatAlreadySpentNearlyToMuchSoItLimitsExceed() throws Exception {
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		
		Employee employee = schedule.getEmployees().get(0);

		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 34*60);
		
		try{
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 5, 10, 0), 4*60); // will excite the limit by one hour
			fail("OperationNotAllowedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("You have exceeded the time limit fot the task", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(1, employee.getTasks().size());
		assertEquals(0, user.getTasks().size());
		
	}
	
	@Test
	public void addTwoTaskToAnEmployeeAndCheckIfDatesAreCorrect() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task1 = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 40);	// name, number, startWeek, endWeek, budgetedHours
		Task task2 = new Task("taskName2", new GregorianCalendar(2015, Calendar.JANUARY, 4), new GregorianCalendar(2015, Calendar.JANUARY, 9), 40);	// name, number, startWeek, endWeek, budgetedHours
	
		user.createTask(task1, project);
		user.createTask(task2, project);
		
		Employee employee = schedule.getEmployees().get(0);
		
		assertEquals(0, employee.getTasks().size());
		
		user.addEmployeeToTask(employee, task1, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 30*60);
		user.addEmployeeToTask(employee, task2, new GregorianCalendar(2015, Calendar.JANUARY, 6, 14, 0), 7*60);
		
		Calendar checkEndDate = new GregorianCalendar(2015, Calendar.JANUARY, 7, 13, 0);
		
		assertEquals(2, employee.getTasks().size());
		assertTrue(employee.getTasksAndTime().get(task2).get(0).getEndDate().getTime().equals(checkEndDate.getTime()));		
	}	

	
	@Test
	public void addTaskToAnEmployeeThatDoesNotHaveTime() throws Exception {
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task1 = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 40);	// name, number, startWeek, endWeek, budgetedHours
		Task task2 = new Task("taskName2", new GregorianCalendar(2015, Calendar.JANUARY, 4), new GregorianCalendar(2015, Calendar.JANUARY, 9), 40);	// name, number, startWeek, endWeek, budgetedHours
		Task task3 = new Task("taskName3", new GregorianCalendar(2015, Calendar.JANUARY, 4), new GregorianCalendar(2015, Calendar.JANUARY, 7), 30);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task1, project);
		user.createTask(task2, project);
		user.createTask(task3, project);
		
		Employee employee = schedule.getEmployees().get(0);
		
		user.addEmployeeToTask(employee, task3, new GregorianCalendar(2015, Calendar.JANUARY, 4, 8, 0), 24*60);
		
		assertEquals(1, employee.getTasks().size());
		
		user.removeEmployeeFromTask(employee, task3);
		
		assertEquals(0, employee.getTasks().size());
		
		user.addEmployeeToTask(employee, task1, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 30*60);
		user.addEmployeeToTask(employee, task2, new GregorianCalendar(2015, Calendar.JANUARY, 6, 14, 0), 7*60);
		
		assertEquals(2, employee.getTasks().size());
		
		try {
			user.addEmployeeToTask(employee, task3, new GregorianCalendar(2015, Calendar.JANUARY, 6, 14, 0), 2*60);
			fail("OperationNotAllowedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("The employee does not have time in this period", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(2, employee.getTasks().size());
		
	}
	
}
