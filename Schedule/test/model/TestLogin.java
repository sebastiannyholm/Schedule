package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

public class TestLogin {

	@Test
	public void login() throws Exception {
		
		Schedule schedule = new Schedule();

		List<Employee> users = schedule.getEmployees();
		
		Address address = new Address("Street", 12, 3850, "City");
		Employee employee = new Employee("Cat", "cat1", 23, address);
		
		schedule.addEmployee(employee);
		
		assertFalse(schedule.loggedIn());
		
		schedule.login(employee);

		assertTrue(schedule.loggedIn());
		
	}
	
	@Test
	public void loginFailed() {

		Schedule schedule = new Schedule();

		List<Employee> users = schedule.getEmployees();
		
		Address address = new Address("Street", 12, 3850, "City");
		Employee employee = new Employee("Cat", "cat1", 23, address);
		
		schedule.login(employee);

		assertFalse(schedule.loggedIn());
	}

	
}
