
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestLogin {

	Schedule schedule = new Schedule();
	
	@Before
	public void setup() throws Exception {
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		schedule.addEmployee(employee);
	}
	
	@Test
	public void login() throws Exception {
		
		assertFalse(schedule.isLoggedIn());
		
		schedule.login("seny");

		assertTrue(schedule.isLoggedIn());
		
	}
	
	@Test
	public void loginFailed() throws Exception {
		
		schedule.login("sn");

		assertFalse(schedule.isLoggedIn());
	}
	
	@Test
	public void loginTwice() throws Exception{
		schedule.login("seny");
		assertTrue(schedule.isLoggedIn());

		Address address2 = new Address("Rolighedsvej", 3, 3000, "Frederikssund");
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);
		schedule.addEmployee(employee2);
		
		try {
			schedule.login("luvi");
		} catch(OperationNotAllowedException e) {
			assertEquals("You can't log in when someone else is using the system.",e.getMessage());
			assertEquals("Log in",e.getOperation());
		}
		
		// verify that the first employee is still logged in and "luvi" was denied access
		assertEquals(schedule.getUser(), schedule.getEmployees().get(0));
		
		schedule.logOut();
		assertFalse(schedule.isLoggedIn());
		assertEquals(schedule.getUser(), null);
		
		schedule.login("luvi");
		assertEquals(schedule.getUser(), employee2);
	}

	@Test
	public void logOut() throws Exception {
		
		schedule.login("seny");
		assertTrue(schedule.isLoggedIn());
		
		schedule.logOut();
		assertFalse(schedule.isLoggedIn());
		
	}
	
	@Test
	public void logOutFailed() {
		
		try {
			schedule.logOut();
		} catch(OperationNotAllowedException e) {
			assertEquals("You can't log out, if you are not logged in.",e.getMessage());
			assertEquals("Log out",e.getOperation());
		}
		
		
	}
	
}
