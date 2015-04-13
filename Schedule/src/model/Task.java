package model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Task {

	List<Employee> employees;
	
	private String name, taskNumber;
	private int startTime, endTime, budgetedTime;

	
	public Task(String name, int startTime, int endTime, int budgetedTime){
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.budgetedTime = budgetedTime;
		
		employees = new LinkedList<Employee>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
		
	}

	public void setTaskNumber(int taskCount) {
				
		// adds leading zeros so that the number reaches 8 digits.   --> taskCount = 207  becomes 00000207
		this.taskNumber = String.format("%04d", (taskCount % 10000000));
		
	}
}
