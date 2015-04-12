package model;

import java.util.LinkedList;
import java.util.List;

public class Schedule {

	private List<Employee> employees;
	private List<Project> allProjects;
	private boolean loggedIn = false;
	
	public Schedule(){
		this.employees = new LinkedList<Employee>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (employee.hasInitialsOnFourLetters()) 
			throw new OperationNotAllowedException("An employee can only have four initials.", "Add employee");
		
		for (Employee employeeInSystem : employees)
			if (employeeInSystem.hasSameInitials(employee.getInitials())) 
				throw new OperationNotAllowedException("Two employees can't have the same initials.", "Add employee");
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
}
