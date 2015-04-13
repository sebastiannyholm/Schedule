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
				
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		// adds leading zeros so that the number reaches 6 digits.   --> projectCount = 6  becomes 000006
		this.taskNumber = "" + year + String.format("%06d", (taskCount % 1000000));
		
		// total taskNumber:  2015000255   for the 254th task in 2015
	}

	public void remove(Employee employee) {
		employees.remove(employee);
		
	}
	
	public String toString(){
		return name + ", " + taskNumber + ", weeks " + startTime + " .. " + endTime + " = " + budgetedTime + " hours";
	}
}
