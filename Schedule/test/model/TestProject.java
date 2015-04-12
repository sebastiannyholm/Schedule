package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestProject {

	
	
	@Test
	public void createProject(){
		Schedule schedule = new Schedule();
		
		Address address = new Address("Street", 12, 3850, "City");			//street, streetNumber, zipCode, city
		Employee employee = new Employee("Cat", "cat1", 23, address);
		schedule.addEmployee(employee);
		
		Project project = new Project("ProjectAwesome", 1, 5, employee);	//projectName, projectNumber, totalTime (in weeks)
		
		assertEquals(0,employee.getProjects());
		employee.createProject(project);
		assertEquals(1,employee.getProjects());
		
		
		
		// employee.addTask(project)     --> if (employee.equals(project.getProjectLeader)) NO TASK IS ADDED
	
	}
	
	@Test
	public void deleteProject(){
		Schedule schedule = new Schedule();
		
		Address address = new Address("Street", 12, 3850, "City");			//street, streetNumber, zipCode, city
		Employee employee = new Employee("Cat", "cat1", 23, address);
		schedule.addEmployee(employee);
		
		Project project = new Project("ProjectAwesome", 1, 5, employee);	//projectName, projectNumber, totalTime (in weeks)
		
		assertEquals(0,employee.getProjects());
		employee.createProject(project);
		assertEquals(1,employee.getProjects());
		
		employee.deleteProject(project);
		assertEquals(0,employee.getProjects());
		
	}
}
