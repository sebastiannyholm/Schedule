package model;

import java.util.List;

import org.junit.Test;

public class TestTask {

	@Test
	public void addTask() throws Exception {
		
		Schedule schedule = new Schedule();

		List<Employee> users = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingør");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address);
		
		schedule.addEmployee(employee);
				
	}
	
}
