package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestLogin {

	Schedule schedule = new Schedule();
	
	@Before
	public void setUp() throws Exception {
		
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

	
}
