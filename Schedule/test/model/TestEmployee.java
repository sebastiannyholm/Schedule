package model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TestEmployee {

	@Test
	public void addEmployee(){
		Schedule schedule = new Schedule();
		
		List<Employee> users = schedule.getEmployees();
		
		Address address = new Address("Street", 12, 3850, "City");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Cat", "cat1", 23, address);
		
		assertEquals(0,users.size());
		schedule.addEmpolyee(employee);
		assertEquals(1,users.size());
		
	}
}
