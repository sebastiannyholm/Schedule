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
	public void login() {
		
		assertFalse(schedule.isLoggedIn());
		
		schedule.login("seny");

		assertTrue(schedule.isLoggedIn());
		
	}
	
	@Test
	public void loginFailed() {
		
		schedule.login("sn");

		assertFalse(schedule.isLoggedIn());
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
