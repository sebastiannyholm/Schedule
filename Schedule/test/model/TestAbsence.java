package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAbsence {

	@Test
	public void employeeCallsInSick() throws Exception{
		
		/* 
		 * Given the situation where an employee calls in sick, informing a project leader
		 * the project leader then adds the employee to the "absence" project.
		 * The employee is assigned to at least one project and at least one task
		 */
		
		Schedule schedule = new Schedule();
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		Project project = new Project("title", 5, 10, employee1); 			// employee is assigned project leader
		Task task = new Task("title", 6,8, 50);
		
		schedule.login(employee1.getInitials());
		employee1.createProject(project);
		employee1.createTask(task, project);
		employee1.addEmployeeToTask(employee2, task);
		
		// ----------------------------------
		// ----------------------------------
		
		// Now the employee calls in sick
		// the project leader acts accordingly
		
		assertFalse(employee2.isAbsent());
		
		employee1.reportAbsence(employee2);
		
		assertTrue(employee2.isAbsent());
		
		
	}
	
	@Test
	public void notifyVacation(){
		
	}

}
