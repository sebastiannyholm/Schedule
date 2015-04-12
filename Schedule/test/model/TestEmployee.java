package model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TestEmployee {

	@Test
	public void addEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> users = schedule.getEmployees();
		
		Address address = new Address("Street", 12, 3850, "City");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Cat", "cat1", 23, address);
		
		assertEquals(0,users.size());
		
		schedule.addEmployee(employee);
		
		assertEquals(1,users.size());
	}
	
	@Test
	public void addEmployeeWithFiveInitials() {
		Schedule schedule = new Schedule();
		
		List<Employee> users = schedule.getEmployees();
		
		Address address = new Address("Street", 12, 3850, "City");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Cat", "cat12", 23, address);
		
		assertEquals(0,users.size());
		
		try {
			schedule.addEmployee(employee);
		} catch (OperationNotAllowedException e) {
			assertEquals("An employee can only have four initials.",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(0,users.size());
	}
	
}
