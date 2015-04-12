package model;

import java.util.LinkedList;
import java.util.List;

public class Schedule {

	private List<Employee> employees;
	private List<Project> allProjects;
	
	public Schedule(){
		this.employees = new LinkedList<Employee>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (employee.getInitials().length() != 4) throw new OperationNotAllowedException("An employee can only have four initials.", "Add employee");
		employees.add(employee);
	}

	public List<Project> getAllProjects(){
		return allProjects;
	}
}
