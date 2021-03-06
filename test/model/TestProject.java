package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestProject {

	Schedule schedule = new Schedule();
	Employee user;
	
	@Before
	public void setup() throws Exception {
		Address address = new Address("Rolighedsvej", 3, 3000, "Helsingor");					//street, streetNumber, zipCode, city
		Employee employee = new Employee("Sebastian Nyholm", "seny", 25, address, schedule);	// name, initials, age, address, schedule
		schedule.addEmployee(employee);
		
		schedule.login("seny");
		
		user = schedule.getUser();
	}
	
	@Test
	public void createProject() throws Exception{
		
		Project project = new Project("ProjectAwesome", 1, 5, user);						//projectName, projectNumber, totalTime (in weeks)
	
		assertEquals(0,user.getProjects().size());
		user.createProject(project);
		assertEquals(1,schedule.getAllProjects().size());
		assertEquals(1,user.getProjects().size());
		
	}
	
	@Test
	public void createProjectWhenNotLoggedIn() throws Exception {
		Project project = new Project("ProjectAwesome", 1, 5, user);						//projectName, projectNumber, totalTime (in weeks)
		
		schedule.logOut();
		
		assertFalse(schedule.isLoggedIn());
		
		assertEquals(0,user.getProjects().size());
		
		try {
			user.createProject(project);	
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("You need to be logged in to create a project",e.getMessage());
			assertEquals("Create project",e.getOperation());
		}
		
		assertEquals(0,schedule.getAllProjects().size());
		
	}
	
	@Test
	public void projectOverlapNewYear(){
		
	}
	
	@Test
	public void deleteProject() throws Exception{
		
		Project project = new Project("ProjectAwesome",1, 5, user);							//projectName, totalTime (in weeks), employee
		
		user.createProject(project);
		
		Employee projectLeader = project.getProjectLeader();
		projectLeader.deleteProject(project);
		assertEquals(0,projectLeader.getProjects().size());
	}
	
	@Test
	public void deleteProjectWhenNotProjectLeader() throws Exception {
		
		Project project = new Project("ProjectAwesome",1, 5, user);							//projectName, totalTime (in weeks), employee
		user.createProject(project);
		
		assertEquals(1,schedule.getAllProjects().size());
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee2);

		schedule.logOut();
		schedule.login("luvi");
		
		user = schedule.getUser();
		
		try {
			user.deleteProject(project);	
			fail("OperationNotAlloedException should have been thrown from the above statement");
		} catch (OperationNotAllowedException e) {
			assertEquals("Cannot remove a project if not its leader",e.getMessage());
			assertEquals("Delete project",e.getOperation());
		}
	
		assertEquals(1,schedule.getAllProjects().size());
		
	}
	
	@Test
	public void createTheSameProjectMultipleTimes() throws Exception {
		
		Project project = new Project("ProjectAwesome", 1, 5, user);							//projectName, totalTime (in weeks), employee
		
		user.createProject(project);
		
		try {
			user.createProject(project);	
		} catch (OperationNotAllowedException e) {
			assertEquals("You can't create the same project multiple times.",e.getMessage());
			assertEquals("Create project",e.getOperation());
		}
		
		assertEquals(1,user.getProjects().size());
	}
	
	@Test
	public void projectNumberGeneration() throws Exception {
		
		Project project = new Project("ProjectAwesome",1, 5, user);							//projectName, totalTime (in weeks), employee
		Project newProject = new Project("ProjectEXO",4, 9, user);
		Project anotherProject = new Project("Tea Party",5, 10, user);
		
		user.createProject(project);
		user.createProject(newProject);
		user.createProject(anotherProject);
		
		// this will change according to the current year so won't work forever...
		assertEquals(20150000, project.getProjectNumber());
		assertEquals(20150001, newProject.getProjectNumber());
		assertEquals(20150002, anotherProject.getProjectNumber());
	}
	
	public void searchProjects() throws Exception {
		
		Project project = new Project("ProjectAwesome",1, 5, user);						//projectName, start, end (in weeks)

		user.createProject(project);
		
		List<Project> foundProjects = schedule.searchProjects("wrong");
		
		assertEquals(0,foundProjects.size());
		
		foundProjects = schedule.searchProjects("ProjectAwesome");
		
		assertEquals(1,foundProjects.size());
		
	}
	
	@Test
	public void changeProjectLeader() throws Exception {
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee2);
		
		Project project = new Project("ProjectAwesome",1, 5, user);						//projectName, projectNumber, totalTime (in weeks)

		user.createProject(project);
		assertEquals(user, project.getProjectLeader());
		
		assertEquals(1,user.getProjects().size());
		assertEquals(0,employee2.getProjects().size());
		
		user.changeProjectLeader(employee2, project);
		
		assertEquals(0,user.getProjects().size());
		assertEquals(1,employee2.getProjects().size());
		assertEquals(employee2, project.getProjectLeader());
	}
	
	@Test
	public void changeProjectLeaderBySearch() throws Exception {		
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		schedule.addEmployee(employee2);
		
		Project project = new Project("ProjectAwesome",1, 5, user);								//projectName, totalTime (in weeks)
		user.createProject(project);
		
		List<Employee> foundEmployees = schedule.searchEmployee("Lukas Villumsen");
		assertEquals(1,foundEmployees.size());
		
		Employee newProjectLeader = foundEmployees.get(0);		
		
		List<Project> foundProjects = schedule.searchProjects("ProjectAwesome");
		assertEquals(1,foundProjects.size());
		
		Project currentProject = foundProjects.get(0);
		
		Employee currentProjectLeader = currentProject.getProjectLeader();
		
		currentProjectLeader.changeProjectLeader(newProjectLeader, currentProject);
		
		assertEquals(0,currentProjectLeader.getProjects().size());
		assertEquals(1,newProjectLeader.getProjects().size());
		
		
	}
}
