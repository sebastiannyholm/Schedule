package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestProject {

	
	
	@Test
	public void createProject() throws Exception{
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");					//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);	// name, initials, age, address, schedule
		schedule.addEmployee(employee);
		
		Project project = new Project("ProjectAwesome", 5, employee);						//projectName, projectNumber, totalTime (in weeks)
		
		assertEquals(0,employee.getProjects().size());
		employee.createProject(project);
		assertEquals(1,schedule.getAllProjects().size());
		assertEquals(1,employee.getProjects().size());
		
	}
	
	@Test
	public void deleteProject() throws Exception{
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");					//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);	// name, initials, age, address, schedule
		schedule.addEmployee(employee);
		
		Project project = new Project("ProjectAwesome", 5, employee);							//projectName, totalTime (in weeks), employee
		
		employee.createProject(project);
		
		Employee projectLeader = project.getProjectLeader();
		projectLeader.deleteProject(project);
		assertEquals(0,projectLeader.getProjects().size());
	}
	
	@Test
	public void projectNumberGeneration() throws OperationNotAllowedException{
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");					//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);	// name, initials, age, address, schedule
		schedule.addEmployee(employee);
		
		Project project = new Project("ProjectAwesome", 5, employee);							//projectName, totalTime (in weeks), employee
		Project newProject = new Project("ProjectEXO", 9, employee);
		Project anotherProject = new Project("Tea Party", 10, employee);
		
		employee.createProject(project);
		employee.createProject(newProject);
		employee.createProject(anotherProject);
		
		assertEquals(20150000, project.getProjectNumber());
		assertEquals(20150001, newProject.getProjectNumber());
		assertEquals(20150002, anotherProject.getProjectNumber());
	}
	
	public void searchProjects() throws Exception {
		Schedule schedule = new Schedule();
		
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");					//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);
		
		schedule.addEmployee(employee);
		
		Project project = new Project("ProjectAwesome", 5, employee);						//projectName, projectNumber, totalTime (in weeks)
		employee.createProject(project);
		
		List<Project> foundProjects = schedule.searchProjects("wrong");
		
		assertEquals(0,foundProjects.size());
		
		foundProjects = schedule.searchProjects("ProjectAwesome");
		
		assertEquals(1,foundProjects.size());
		
	}
	
	@Test
	public void changeProjectLeader() throws Exception {
		Schedule schedule = new Schedule();
		
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");		//street, streetNumber, zipCode, city
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		
		Project project = new Project("ProjectAwesome", 5, employee1);						//projectName, projectNumber, totalTime (in weeks)
		employee1.createProject(project);
		
		assertEquals(1,employee1.getProjects().size());
		assertEquals(0,employee2.getProjects().size());
		
		employee1.changeProjectLeader(employee2, project);
		
		assertEquals(0,employee1.getProjects().size());
		assertEquals(1,employee2.getProjects().size());
		
	}
	
	@Test
	public void changeProjectLeaderBySearch() {
		
	}
}
