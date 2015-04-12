package model;

import java.util.List;

import org.junit.Test;

public class TestTask {

	@Test
	public void addTask() throws Exception {
		
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address);	//name, initials, age, address
		
		schedule.addEmployee(employee);
				
	}
	
}
