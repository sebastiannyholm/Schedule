package model;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class TestDataSetup {
		
	@Before
	public void setUp(){
		
		Schedule schedule = new Schedule();
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor"); 
		Employee employee1 = new Employee("brje","Brian Jensen",35,address1);
		
		Address address2 = new Address("Bjarkesvej", 14, 3000, "Helsingor");
		Employee employee2 = new Employee("lala","Lasse Larsen",42,address2);
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
	}
	
	
	/* Test 1
	 * As an employee I would like to log into the system, when I arrive at work.
	 */
	@Test
	public void testLogin() {

		// Check if a user is logged in
		// Will fail, since no user are logged in.
		
		assertFalse(schedule.adminLoggedIn());
		
		// Step 1)
		
		schedule.login("user");
		
		// Step 2) Check that the method returned true and check that admin is logged in.
		assertTrue(login);
		assertTrue(libApp.adminLoggedIn());
	}
	
}