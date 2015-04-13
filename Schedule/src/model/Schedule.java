package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Schedule {

	private List<Employee> employees;
	private List<Project> allProjects;
	private boolean loggedIn = false;
	
	public Schedule(){
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
	
	public void login(String initials) {
		for (Employee employee : employees)
			if (employee.matchInitials(initials)) {
				loggedIn = true;
				break;
			}			
	}

	public void addProject(Project project) {
		allProjects.add(project);
		
	}

	public List<Employee> searchEmployee(String critiria) {
		List<Employee> foundEmployees = new ArrayList<Employee>();
		
		for (Employee employee : employees)
			if(employee.match(critiria))
				foundEmployees.add(employee);
		
		return foundEmployees;
	}
}
