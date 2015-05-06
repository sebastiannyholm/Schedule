package model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Two employees can't have the same initials.",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(1,employees.size());
		
	}
	
	@Test
	public void addEmployeeWithFiveInitials() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "sn", 25, address, schedule);
		
		assertEquals(0,employees.size());
		
		try {
			schedule.addEmployee(employee);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("An employee can only have four initials.",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(0,employees.size());
	}
	
	@Test
	public void removeEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		schedule.addEmployee(employee);
		
		assertEquals(1,employees.size());
		
		schedule.removeEmployee(employee);
		
		assertEquals(0,employees.size());
		
	}
	
	@Test
	public void removeEmployeeThatDoesNotExist() throws Exception {
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		List<Employee> employees = schedule.getEmployees();
		
		assertEquals(0,employees.size());
		
		try {
			schedule.removeEmployee(employee);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("You can't remove an employee that doesn't exist",e.getMessage());
			assertEquals("Remove employee",e.getOperation());
		}
		
		assertEquals(0,employees.size());
		
	}
	
	@Test
	public void removeEmployeeThatIsProjectLeader() throws Exception {
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		schedule.addEmployee(employee);
		assertEquals(1,schedule.getEmployees().size());
		
		schedule.login(employee.getInitials());
		Employee user = schedule.getUser();
		
		Project project = new Project("ProjectAwesome", new GregorianCalendar(2015, Calendar.JANUARY, 1), new GregorianCalendar(2015, Calendar.JANUARY, 29), user);						//projectName, projectNumber, totalTime (in weeks)
		
		user.createProject(project);
		assertEquals(1,user.getProjects().size());
		
		try {
			schedule.removeEmployee(user);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("You can't remove an employee that is project leader",e.getMessage());
			assertEquals("Remove employee",e.getOperation());
		}
		
		assertEquals(1,schedule.getEmployees().size());
		
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
		// two employees with the char sequence "as"
		assertEquals(2,foundEmployees.size());
		
	}	

	@Test
	public void adminAddEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		employee1.setAdmin(true);
		
		schedule.addEmployee(employee1);
		
		assertEquals(1,employees.size());
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		schedule.login("seny");
		Employee user = schedule.getUser();
		
		user.addEmployee(employee2);
		
		assertEquals(2,employees.size());
		
	}	
	
	@Test
	public void notLoggedInAddEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		employee1.setAdmin(true);
		
		schedule.addEmployee(employee1);
		
		assertEquals(1,employees.size());
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		try {
			employee1.addEmployee(employee2);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("You can't add an employee if you are not logged in!",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(1,employees.size());
		
	}
	
	@Test
	public void notAdminAddEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		employee1.setAdmin(false);
		
		schedule.addEmployee(employee1);
		
		assertEquals(1,employees.size());
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		schedule.login("seny");
		Employee user = schedule.getUser();
		
		try {
			user.addEmployee(employee2);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Only administrators can add employees!",e.getMessage());
			assertEquals("Add employee",e.getOperation());
		}
		
		assertEquals(1,employees.size());
		
	}
	
	@Test
	public void adminRemoveEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		employee1.setAdmin(true);
		
		schedule.addEmployee(employee1);
		
		assertEquals(1,employees.size());
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		schedule.login("seny");
		Employee user = schedule.getUser();
		
		user.addEmployee(employee2);
		
		assertEquals(2,employees.size());
		
	}	
	
	@Test
	public void notLoggedInRemoveEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		employee1.setAdmin(true);
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		assertEquals(2,employees.size());
		
		try {
			employee1.removeEmployee(employee2);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("You can't remove an employee if you are not logged in!",e.getMessage());
			assertEquals("Remove employee",e.getOperation());
		}
		
		assertEquals(2,employees.size());
		
	}
	
	@Test
	public void notAdminRemoveEmployee() throws Exception {
		Schedule schedule = new Schedule();
		
		List<Employee> employees = schedule.getEmployees();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule
		
		employee1.setAdmin(false);
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		assertEquals(2,employees.size());
		
		schedule.login("seny");
		Employee user = schedule.getUser();
		
		try {
			user.removeEmployee(employee2);
			fail("OperationNotAllowedException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Only administrators can remove employees!",e.getMessage());
			assertEquals("Remove employee",e.getOperation());
		}
		
		assertEquals(2,employees.size());
		
	}
	
	@Test
	public void removeYourself() throws Exception{
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");		
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		// ------------
		
		employee1.setAdmin(true);
		schedule.login("seny");
		
		assertEquals(2, schedule.getEmployees().size());
		
		// remove the non-admin employee
		employee1.removeEmployee(employee2);
		assertEquals(1, schedule.getEmployees().size());
		
		// the employee1 tries to remove himself --> not allowed
		
		try{
			employee1.removeEmployee(employee1);
		} catch (OperationNotAllowedException e){
			assertEquals("You cannot remove yourself from the system!", e.getMessage());
			assertEquals("Remove employee", e.getOperation());
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
