package model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Task {

	List<Employee> employees;
	
	private String name, taskNumber;
	private int startWeek, endWeek, budgetedTime;
	private Map<Employee, Integer> log = new HashMap<Employee, Integer>();

	
	public Task(String name, int startWeek, int endWeek, int budgetedTime){
		this.name = name;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
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
		return name + ", " + taskNumber + ", weeks " + startWeek + " .. " + endWeek + " = " + budgetedTime + " hours";
	}

	// this.startWeek > 45 to make up for tasks overlapping new years eve
	public boolean isOutOfBounds(int startWeek, int endWeek) {
		return (this.startWeek < startWeek || this.startWeek >= endWeek || this.endWeek < startWeek || this.endWeek >= endWeek 
				|| this.startWeek < 1 || this.endWeek < 1 || this.startWeek > 52 || this.endWeek > 52) 
				&& (this.startWeek < 45 || this.startWeek > 52);
	}

	public boolean endsBeforeStart() {
		// take into account tasks which overlap new years eve
		if (startWeek >= 45)
			return !(endWeek < startWeek && endWeek <= 10);
		
		return endWeek < startWeek;
	}

	public boolean isInPeriod(int startWeek, int endWeek) {
		return (this.startWeek >= startWeek && this.startWeek < endWeek) || (this.endWeek > startWeek && this.endWeek <= endWeek)
				|| (this.startWeek <= startWeek && this.endWeek >= endWeek);
	}

	
	public void setTaskLog(Employee employee, int timeWorkedOnTask) {
		
		if (log.containsKey(employee))
			log.put(employee, log.get(employee) + timeWorkedOnTask);
		else 
			log.put(employee, timeWorkedOnTask);
		
	}
}
