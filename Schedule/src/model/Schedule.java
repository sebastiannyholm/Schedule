package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Schedule {

	private List<Employee> employees;
	private List<Project> allProjects;
	private List<Task> allTasks;
	private boolean loggedIn = false;
	private Employee user;
	
	public Schedule(){

		this.employees = new LinkedList<Employee>();
		this.allProjects = new LinkedList<Project>();
		this.allTasks = new LinkedList<Task>();

		this.employees = new ArrayList<Employee>();
		this.allProjects = new ArrayList<Project>();

	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (employee.hasInitialsOnFourLetters()) 
			throw new OperationNotAllowedException("An employee can only have four initials.", "Add employee");
		
		for (Employee employeeInSystem : employees) {
			if (employeeInSystem.hasSameInitials(employee.getInitials())) 
				throw new OperationNotAllowedException("Two employees can't have the same initials.", "Add employee");
		}
		
		employees.add(employee);
	}

	public List<Project> getAllProjects(){
		return allProjects;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public void login(String initials) throws Exception {
		if (isLoggedIn()) throw new OperationNotAllowedException("You can't log in when someone else is using the system.", "Log in");
		for (Employee employee : employees)
			if (employee.matchInitials(initials)) {
				loggedIn = true;
				this.user = employee;
				break;
			}
	}
	
	public void logOut() throws OperationNotAllowedException {
		if (!loggedIn)
			throw new OperationNotAllowedException("You can't log out, if you are not logged in.", "Log out");
		
		loggedIn = false;
		user = null;
	}

	public void addProject(Project project) {
		allProjects.add(project);
		
	}

	public List<Task> getAllTasks() {
		return allTasks;
	}

	public void addTask(Task task) {
		allTasks.add(task);
		
	}
		
	public List<Employee> searchEmployee(String critiria) {
		List<Employee> foundEmployees = new ArrayList<Employee>();
		
		for (Employee employee : employees)
			if(employee.match(critiria))
				foundEmployees.add(employee);
		
		return foundEmployees;
	}

	public List<Project> searchProjects(String critiria) {
		List<Project> foundEmployees = new ArrayList<Project>();
		
		for (Project project : allProjects)
			if(project.match(critiria))
				foundEmployees.add(project);
		
		return foundEmployees;
	}

	public List<Project> getProjectsInPeriod(int startWeek, int endWeek) {
		List<Project> projectsInPeriod = new ArrayList<Project>();
		
		for (Project project : allProjects)
			if (project.isInPeriod(startWeek, endWeek))
				projectsInPeriod.add(project);
		return projectsInPeriod;
	}

	public Employee getUser() {
		return user;
	}

}
