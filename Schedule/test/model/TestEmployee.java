package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEmployee {

	@Test
	public void addEmployee(){
		Schedule schedule = new Schedule();
		
		List<Employee> users = schedule.getEmployees();
		assertEquals(0,users.size());
		shedule.addEmpolyee();
		assertEquals(1,users.size());
	}
}
