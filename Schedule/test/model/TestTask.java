package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
		
		Project project = schedule.getAllProjects().get(0);			// list of 1
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		
		assertEquals(0,project.getTasks().size());
		user.createTask(task, project);
		assertEquals(1,project.getTasks().size());
		
		
	}
	
	@Test
	public void createTaskWhenNotLoggedIn() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37*(2-1));	// name, number, startWeek, endWeek, budgetedHours
		schedule.logOut();
		assertEquals(0,project.getTasks().size());
		try{
			user.createTask(task, project);
		} catch(OperationNotAllowedException e){
			assertEquals("You need to be logged in to add a task", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		assertEquals(0,project.getTasks().size());
		
		
	}
	
	
	// only the project leader can add tasks
	@Test
	public void createTaskFailed() throws Exception {
		
		Project project = schedule.getAllProjects().get(0);
		
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
	 * Task exceeding project limits/bounds
	 * A task with a startWeek after the project has expired or
	 * a task with an endWeek before the project has begun or
	 * a task with an endWeek after the project has expired or
	 * a task with a startWeek before the project has begun,
	 * cannot be applied to the project
	 */
	@Test
	public void createTaskOutOfBounds() throws Exception{
		Project project = schedule.getAllProjects().get(0);
		
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
	
	// cannot have a task which ends before it begins!
	@Test
	public void createTaskEndBeforeStart() throws Exception{
		Project project = schedule.getAllProjects().get(0);	
		
		Task badTask = new Task("Bad task", new GregorianCalendar(2015, Calendar.JANUARY, 17), new GregorianCalendar(2015, Calendar.JANUARY, 15), 37);		// name, number, startWeek, endWeek, budgetedHours - 
																	// begins before it begins with negative budget time..
		try {
			user.createTask(badTask, project);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Task ends before it even begins!", e.getMessage());
			assertEquals("Add task", e.getOperation());
		}
		
		assertEquals(0,project.getTasks().size());
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
	
	@Test
	public void addEmployeeToTaskIfNotProjectLeader() throws Exception {
		Project project = schedule.getAllProjects().get(0);		
		Employee employee = schedule.getEmployees().get(0);
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));	// name, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		
		// task knows which employees are working on it
		// and the employee knows which tasks they are working on	
		
		schedule.logOut();
		schedule.login("seny");
		user = schedule.getUser();
		
		// A user that is not project leader of the given project tries to add a employee to a task.
		try{
			user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 200);				// main focus here, adds an employee to the created task
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("Only the project leader may add an employee to the task", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(0, employee.getTasks().size());
		
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
	
	@Test
	public void checkTaskDates() throws Exception{
		Project project = schedule.getAllProjects().get(0);				
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 8), new GregorianCalendar(2015, Calendar.JANUARY, 22), 37*(4-2));	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 8, 8, 0), 200);
		
		assertEquals("2015000000 - taskName from 08/01/2015 to 22/01/2015", task.toString()); 	// overview of both start and end date
		assertEquals("Thu Jan 08 00:00:00 CET 2015", task.getStartDate().getTime().toString());	// start only
		assertEquals("Thu Jan 22 00:00:00 CET 2015", task.getEndDate().getTime().toString());	// end only
		
	}
	
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
	public void addTooMuchTimeToAnEmployeeForAGivenTask() throws Exception {
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		
		try{
			user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 38*60);
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("You have exceeded the time limit for the task", e.getMessage());
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
			assertEquals("You have exceeded the time limit for the task", e.getMessage());
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
	
	/*
	 * Test which employees that are free to work on a new task in a given timespan
	 * We have two employees, which both are assigned to a job, but one of them are free from monday the 5. 8am.
	 * The other one is first ready tuesday the 6. 2pm.
	 * We want to test how many employees that are ready to work on a task monday the 5. 8am and 20 hours ahead.
	 */
	@Test
	public void checkIfEmployeesAreWorkingInATimespan() throws Exception {
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Task task = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 80);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		
		Employee employee1 = schedule.getEmployees().get(0);
		Employee employee2 = schedule.getEmployees().get(1);
		
		user.addEmployeeToTask(employee1, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 24*60);
		user.addEmployeeToTask(employee2, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 16*60);
		
		assertEquals(user.getFreeEmployeesInPeriod(new GregorianCalendar(2015, Calendar.JANUARY, 5, 8, 0), 20).size(), 1);
		
		
		assertFalse(task.isInPeriod(new GregorianCalendar(2016,Calendar.JANUARY,1), new GregorianCalendar(2016,Calendar.JANUARY,2)));
		
	}
	
	@Test
	public void requireAssistance() throws Exception{
		Project project = schedule.getAllProjects().get(0);
		
		Employee employee1 = schedule.getEmployees().get(0);
		Employee employee2 = schedule.getEmployees().get(1);
		
		Task task = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 80);	// name, number, startWeek, endWeek, budgetedHours

		user.createTask(task, project);
		
		user.addEmployeeToTask(employee1, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 24*60);
		
		schedule.logOut();
		schedule.login("seny");
		user = schedule.getUser();
		
		user.requireAssistance(employee2, task, new GregorianCalendar(2015, Calendar.JANUARY, 2, 8, 0), 16*60);
		
		assertEquals(task.getEmployeesAsAssistance().size(), 1);
		
	}
	
	@Test
	public void requireAssistanceIfNotOnTheTask() throws Exception{
		Project project = schedule.getAllProjects().get(0);		// list of 1
		
		Employee employee1 = schedule.getEmployees().get(0);
		Employee employee2 = schedule.getEmployees().get(1);
		
		Task task = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 80);	// name, number, startWeek, endWeek, budgetedHours

		user.createTask(task, project);
		
		user.addEmployeeToTask(employee2, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 24*60);
		
		schedule.logOut();
		schedule.login("seny");
		user = schedule.getUser();
		
		try {
			user.requireAssistance(employee1, task, new GregorianCalendar(2015, Calendar.JANUARY, 2, 8, 0), 16*60);
			fail("OperationNotAllowedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("You can not require assistance if you are not on the task!", e.getMessage());
			assertEquals("Require assistance", e.getOperation());
		}
		
		assertEquals(task.getEmployeesAsAssistance().size(), 0);
		
	}
	
	@Test
	public void requireAssistanceTooManyTasks() throws Exception {
		Project project = schedule.getAllProjects().get(0);
		Employee employee = schedule.getEmployees().get(0);
		
		for (int i = 0; i < 10; i++){
			//add 10 tasks to the employee
			Task task = new Task("task"+i, new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));
			user.createTask(task, project);
			user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, i), 1);
		}
		
		assertEquals(10, employee.getTasks().size());
		Task task = new Task("task", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 37*(2-1));
		user.createTask(task, project);
		user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1,8,0), 37*60);
		// the employee asks the project leader for assistance but has too many tasks
		
		try{
			user.requireAssistance(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1,8,0), 2*60);
			fail("OperationNotAllowedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("The employee " + employee + " is already working on the maximum amount of tasks!", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		assertEquals(10, employee.getTasks().size());
		
	}
	
	@Test
	public void requireAssistanceOverBudget() throws Exception {
		Project project = schedule.getAllProjects().get(0);
		Employee employee = schedule.getEmployees().get(0); 
		
		// employee = seny
		// user = luvi
		
		// task has 37 hours of budgeted time
		Task task = new Task("taskName", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), 37);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		// add the employee to the task with an estimated time of 30 hours.
		user.addEmployeeToTask(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 1,8,0), 30*60);
		
		// the employee requires assistance in order to complete the task.
		// the amount of time the employee needs assistance for exceeds the budgeted time of the task.
		
		try{
			user.requireAssistance(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 6, 14, 0), 8*60);
			fail("OperationNotAllowedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("You have exceeded the time limit for the task", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
	}
	
	@Test
	public void requireAssistanceBusy() throws Exception{
		Project project = schedule.getAllProjects().get(0);
		Employee employee = schedule.getEmployees().get(0);
		
		// employee = seny
		// user = luvi
		
		// have two different tasks in the same period with two employees working on one each.
		Task task = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 80);	// name, number, startWeek, endWeek, budgetedHours
		Task task2 = new Task("taskName2", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 8), 80);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		user.createTask(task2, project);
		
		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 24*60);
		user.addEmployeeToTask(user, task2, new GregorianCalendar(2015, Calendar.JANUARY, 5, 8, 0), 16*60);
		
		try {
			// the employee asks for assistance on the 5th of January at 12:45 for just 15 minutes.
			// this is not possible, as the user himself is working on another task at this moment.
			employee.requireAssistance(user, task, new GregorianCalendar(2015, Calendar.JANUARY, 5, 12, 45), 15);
			fail("OperationNotAllowedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e){
			assertEquals("The employee does not have time in this period", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
	}
	
	// for the user to see which tasks he/she is working on today
	@Test
	public void getTodaysAgenda() throws Exception{
		
		// setup mock
		//--------
		DateServer dateServer = mock(DateServer.class);
		
		//make this the "today" date and time the user checks his/her agenda
		Calendar cal = new GregorianCalendar(2015,Calendar.JANUARY,13,8,0);
		
		schedule.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(cal);
		
		//---------
		
		Project project = schedule.getAllProjects().get(0);
		Employee employee = schedule.getEmployees().get(0);
		

		// employee = seny
		// user = luvi
		
		// create a couple of tasks, one for today and one for the future
		Task task = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 15), 80);	// name, number, startWeek, endWeek, budgetedHours
		Task task2 = new Task("taskName2", new GregorianCalendar(2015, Calendar.JANUARY, 14), new GregorianCalendar(2015, Calendar.JANUARY, 18), 40);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		user.createTask(task2, project);
		
		assertEquals(0, user.getTodaysAgenda().size());
		// add the employee to one task of today (or overlapping today)
		// and the other not - hence checking todays agenda should only yield 1 task
		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 11, 8, 0), 24*60);
		user.addEmployeeToTask(employee, task2, new GregorianCalendar(2015, Calendar.JANUARY, 15, 8, 0), 8*60);
		
		assertEquals(1, employee.getTodaysAgenda().size());
		
		
	}
	
	// for the user to see which tasks he/she is working on today
	@Test
	public void getTodaysAgendaWithMultipleTasks() throws Exception{
		
		// setup mock
		//--------
		DateServer dateServer = mock(DateServer.class);
		
		//make this the "today" date and time the user checks his/her agenda
		Calendar cal = new GregorianCalendar(2015,Calendar.JANUARY,15,8,0);
		
		schedule.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(cal);
		
		//---------
		
		Project project = schedule.getAllProjects().get(0);
		Employee employee = schedule.getEmployees().get(0);
		

		// employee = seny
		// user = luvi
		
		// create a couple of tasks, one for today ending at noon 
		// and another one starting right after, thus giving the employee 2 tasks on his agenda "today"
		Task task = new Task("taskName1", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 16), 80);	// name, number, startWeek, endWeek, budgetedHours
		Task task2 = new Task("taskName2", new GregorianCalendar(2015, Calendar.JANUARY, 10), new GregorianCalendar(2015, Calendar.JANUARY, 18), 40);	// name, number, startWeek, endWeek, budgetedHours
		
		user.createTask(task, project);
		user.createTask(task2, project);
		
		assertEquals(0, user.getTodaysAgenda().size());
		// add the employee to both tasks of today
		// hence checking todays agenda should yield both of the tasks

		user.addEmployeeToTask(employee, task, new GregorianCalendar(2015, Calendar.JANUARY, 11, 8, 0), 28*60); // ends on the 15th at 12:00
		user.addEmployeeToTask(employee, task2, new GregorianCalendar(2015, Calendar.JANUARY, 15, 13, 0), 8*60);	// begins on the 15th at 13:00
		
		// expecting both tasks, as both are in the period 13th, January, 08:00 - 16:00
		assertEquals(2, employee.getTodaysAgenda().size());
		
		assertEquals(employee.getTodaysAgenda().get(0).getTask(), task);
		assertEquals(employee.getTodaysAgenda().get(1).getTask(), task2);
	}
}