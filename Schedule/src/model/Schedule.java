package model;

import java.util.LinkedList;
import java.util.List;

public class Schedule {

	private List<Employee> employees;
	
	public Schedule(){
		this.employees = new LinkedList<Employee>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

}
