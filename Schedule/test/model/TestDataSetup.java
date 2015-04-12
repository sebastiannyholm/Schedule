package model;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class TestDataSetup {
	
	Schedule schedule = new Schedule();
	@Before
	public void setUp(){
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor"); 
		Employee employee1 = new Employee("Brian Jensen", "brje", 35, address1);
		
		Address address2 = new Address("Bjarkesvej", 14, 3000, "Helsingor");
		Employee employee2 = new Employee("Lasse Larsen", "lala", 42, address2);
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
	}
	
}