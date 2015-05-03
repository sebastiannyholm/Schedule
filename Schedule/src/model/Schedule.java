package model;

import java.util.LinkedList;
import java.util.Calendar;
import java.util.List;

public class Schedule {

	private List<Employee> employees;
	private List<Project> allProjects;
//	private List<Task> allTasks;
	private boolean loggedIn = false;
	private Employee user;
	private DateServer dateServer = new DateServer();
	
	public Schedule(){

		this.employees = new LinkedList<Employee>();
		this.allProjects = new LinkedList<Project>();
//		this.allTasks = new LinkedList<Task>();

	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) throws Exception {
		if (employee.hasInitialsOnFourLetters()) 
			throw new OperationNotAllowedException("An employee can only have four initials.", "Add employee");
		
		for (Employee employeeInSystem : employees) {
			if (employeeInSystem.hasSameInitials(employee.getInitials())) 
				throw new OperationNotAllowedException("Two employees can't have the same initials.", "Add employee");
		}
		
		employees.add(employee);
	}
	
	public void removeEmployee(Employee employee) throws Exception {
		if (!employees.contains(employee))
			throw new OperationNotAllowedException("You can't remove an employee that doesn't exist", "Remove employee");
		if (employee.getProjects().size() > 0)
			throw new OperationNotAllowedException("You can't remove an employee that is project leader", "Remove employee");
		
		employees.remove(employee);
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
				user.punchIn();
				break;
			}
	}

	public void logOut() throws OperationNotAllowedException {
		if (!loggedIn)
			throw new OperationNotAllowedException("You can't log out, if you are not logged in.", "Log out");
		
		user.punchOut();
		loggedIn = false;
		user = null;
		
	}

	

	public void addProject(Project project) {
		allProjects.add(project);
		
	}

	public void removeProject(Project project) {
		allProjects.remove(project);
	}
	
	public List<Task> getAllTasks() {
		List<Task> allTasks = new LinkedList<Task>();
		
		for (Project project : allProjects) {
			allTasks.addAll(project.getTasks());
		}
		
		return allTasks;
	}

//	public void addTask(Task task) {
//		allTasks.add(task);
//	}
		
	public List<Employee> searchEmployee(String criteria) {
		List<Employee> foundEmployees = new LinkedList<Employee>();
		
		for (Employee employee : employees)
			if(employee.match(criteria))
				foundEmployees.add(employee);
		
		return foundEmployees;
	}

	public List<Project> searchProjects(String critiria) {
		List<Project> foundEmployees = new LinkedList<Project>();
		
		for (Project project : allProjects)
			if(project.match(critiria))
				foundEmployees.add(project);
		
		return foundEmployees;
	}

	public List<Project> getProjectsInPeriod(Calendar startDate, Calendar endDate) {
		List<Project> projectsInPeriod = new LinkedList<Project>();
		
		for (Project project : allProjects)
			if (project.isInPeriod(startDate, endDate))
				projectsInPeriod.add(project);
		return projectsInPeriod;
	}

	public Employee getUser() {
		return user;
	}

	public Calendar getDate() {
		return dateServer.getDate(); 
	}

	public void setDateServer(DateServer dateServer) {
		this.dateServer = dateServer;
	}

}
