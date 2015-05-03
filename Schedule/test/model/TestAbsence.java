package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class TestAbsence {

	Schedule schedule = new Schedule();
	DateServer dateServer = mock(DateServer.class);
	Calendar cal = new GregorianCalendar(2015,Calendar.JANUARY,1,8,0);
	
	@Before
	public void setup() throws Exception{
		schedule.setDateServer(dateServer);
		when(dateServer.getDate()).thenReturn(cal);

		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		Project ABSENCE = new Project("Absence", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2020, Calendar.JANUARY, 1), employee1);
		Project project = new Project("title", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.DECEMBER, 31), employee1); 			// employee1 is assigned project leader
		Task task = new Task("title", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 20), 80);
		Task sickness = new Task("Sickness", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2020, Calendar.JANUARY, 1), 10000);
		Task vacation = new Task("Sickness", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2020, Calendar.JANUARY, 1), 10000);
		Task course = new Task("Sickness", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2020, Calendar.JANUARY, 1), 10000);
		
		schedule.login(employee1.getInitials());

		employee1.createProject(ABSENCE);
		employee1.createTask(sickness, ABSENCE);
		employee1.createTask(vacation, ABSENCE);
		employee1.createTask(course, ABSENCE);
		
		employee1.createProject(project);
		employee1.createTask(task, project);
		employee1.addEmployeeToTask(employee2, task, new GregorianCalendar(2015, Calendar.JANUARY, 1, 8, 0), 80*60);
		
	}
	
	@Test
	public void employeeCallsInSick() throws Exception{
		
		/* 
		 * Given the situation where an employee calls in sick, informing the project leader
		 * the project leader then adds the employee to the "absence" project, specifically the "sick" task
		 * The employee is assigned to at least one project and at least one task
		 */
		
		Employee projectLeader = schedule.getEmployees().get(0);
		Employee employee = schedule.getEmployees().get(1);
		
		// Now the employee calls in sick
		// the project leader acts accordingly
	
		assertFalse(employee.isAbsent());
		
		projectLeader.reportAbsence(employee, Status.SICK, schedule.getDate(), 8*60);
		
		assertTrue(employee.isAbsent());
		assertEquals(employee.getStatus(), Status.SICK);
		
		// the employee should now show up on the Absent list for the day.
		assertEquals(projectLeader.getAbsentEmployees().get(0).getName(),"Lukas Villumsen");
		
	}

	/*
	 * An employee wants to ask for vacation in a specified interval of time
	 */
	@Test
	public void notifyVacation() throws Exception{
		
		Employee projectLeader = schedule.getEmployees().get(0);
		Employee employee = schedule.getEmployees().get(1);
		
		/*
		 *  An employee asks for vacation specified at a certain interval of time
		 *  -------
		 *  The vacation sought is from, say, the 26th of January to, and including, the 30th of January
		 *  Meaning, the vacation starts on the 26th of January 08:00 and lasts for 5 days of 8 working hours = 40 hours
		 */
		
		projectLeader.reportAbsence(employee, Status.VACATION, new GregorianCalendar(2015, Calendar.JANUARY, 26, 8, 0), 40*60);

		assertFalse(employee.isAbsent());
		
		// skip to the middle of the employee's vacation where he/she should be absent
		Calendar newCal = new GregorianCalendar(2015, Calendar.JANUARY, 26, 8, 0);
		when(dateServer.getDate()).thenReturn(newCal);
		
		assertTrue(employee.isAbsent());
		assertEquals(employee.getStatus(), Status.VACATION);
		
		// the employee should now show up on the Absent list for the day.
		assertEquals(projectLeader.getAbsentEmployees().get(0).getName(),"Lukas Villumsen");
	}
	
	/*
	 * The employee asks for vacation but he is busy at that time.
	 * Tasks and work has already been planed for him/her to carry out.
	 */
	@Test
	public void vacationTimeOccupied() throws Exception{
		Employee projectLeader = schedule.getEmployees().get(0);
		Employee employee = schedule.getEmployees().get(1);
		
		/*
		 *  An employee asks for vacation specified at a certain interval of time
		 *  -------
		 *  The vacation sought is from, say, the 10th of January to, and including, the 14th of January
		 *  Meaning, the vacation starts on the 10th of January 08:00 and lasts for 5 days of 8 working hours = 40 hours
		 *  BUT
		 *  The employee is occupied with tasks during this timespan and is denied vacation at this time.
		 */
		
		try{
			projectLeader.reportAbsence(employee, Status.VACATION, new GregorianCalendar(2015, Calendar.JANUARY, 10, 8, 0), 40*60);
			fail("The employee should not have been allowed to go through with the vacation wishes");
		} catch(OperationNotAllowedException e){
			assertEquals("The employee does not have time in this period", e.getMessage());
			assertEquals("Add employee to task", e.getOperation());
		}
		
		// the employee should NOT show up on the Absent list for the day of his/her initial requested time of vacation
		assertEquals(projectLeader.getAbsentEmployees().size(), 0);
	}
}


















