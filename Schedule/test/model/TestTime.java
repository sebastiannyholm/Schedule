package model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestTime {

	Schedule schedule = new Schedule();
	
	@Before
	public void setup() throws Exception{
		Address address1 = new Address("Rolighedsvej", 3, 3000, "Helsingor");
		Employee employee1 = new Employee("Sebastian Nyholm", "seny", 25, address1, schedule);
		
		Address address2 = new Address("Skoleparken", 44, 3600, "Frederikssund");					//street, streetNumber, zipCode, city
		Employee employee2 = new Employee("Lukas Villumsen", "luvi", 19, address2, schedule);		// name, initials, age, address, schedule

		Project project1 = new Project("Project1", 2, 4, employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		Project project2 = new Project("Project2", 4, 6, employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		Project project3 = new Project("Project3", 8, 13, employee2);		//projectName, projectNumber, totalTime (in weeks), project leader
		
		schedule.addEmployee(employee1);
		schedule.addEmployee(employee2);
		employee2.createProject(project1);
		employee2.createProject(project2);
		employee2.createProject(project3);
		
	}
	
	@Test
	public void registerTime(){
		
		//Calendar begin = new GregorianCalendar(2015, 3, 20);
		//System.out.println(begin.get(GregorianCalendar.WEEK_OF_YEAR));
	}
	
	
	// test how many and which projects are found in a specific period of time (in weeks)
	@Test
	public void projectsInPeriod(){
		
		int startWeek = 2;
		int endWeek = 5;
		
		List<Project> projectsInPeriod = schedule.getProjectsInPeriod(startWeek, endWeek);
		assertEquals(2, projectsInPeriod.size());
	}
	
	@Test
	public void tasksInPeriod(){
		
		int startWeek = 2;
		int endWeek = 5;
		
		List<Project> projectsInPeriod = schedule.getProjectsInPeriod(startWeek, endWeek);
		assertEquals(2, projectsInPeriod.size());
	}
	
	@Test
	public void employeeAgenda(){
		
	}
	
}
