package model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TestEmployee {

	@Test
	public void addEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		assertEquals(0,employees.size());
		
		schedule.addEmployee(employee);
		
		assertEquals(1,employees.size());
		
	}
	
	@Test
	public void addEmployeeWithSameInitials() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Gronnegangen", 32, 3070, "Snekkersten");		//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Sebastian Nystorm", "seny", 23, address2, schedule);
		
		schedule.addEmployee(employee1);
		
		try {
			schedule.addEmployee(employee2);
		} catch (OperationNotAllowedException e) {
			assertEquals("Two employees can't have the same initials.",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(1,employees.size());
		
	}
	
	@Test
	public void addEmployeeWithFiveInitials() {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "sn", 25, address, schedule);
		
		assertEquals(0,employees.size());
		
		try {
			schedule.addEmployee(employee);
		} catch (OperationNotAllowedException e) {
			assertEquals("An employee can only have four initials.",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(0,employees.size());
	}
	
	@Test
	public void searchEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		List<Employee> foundEmployees = schedule.searchEmployee("Seb");
		
		assertEquals(1,foundEmployees.size());
		
		foundEmployees = schedule.searchEmployee("as");
		
		assertEquals(2,foundEmployees.size());
		
	}
	
}
