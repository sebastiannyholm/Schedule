package model;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class TestDataSetup {
	
	Schedule schedule = new Schedule();
	
	@Before
	public void setUp() throws OperationNotAllowedException{
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");			//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);	// name, initials, age, address
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");	
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);	

		Project project = new Project("ProjectAwesome", 5, employee1);				//projectName, projectNumber, totalTime (in weeks)
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		schedule.addProject(project);
		
	}
	
}