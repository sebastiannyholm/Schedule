package model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Task {

	private List<Employee> employees;
	private List<Employee> employeesAsAssistance;
	private String name, taskNumber;
	private Calendar startDate, endDate;
	private int budgetedTime;
	private Map<Employee, Integer> log = new HashMap<Employee, Integer>();

//	private Project project;		// the project connected to this task

	
	public Task(String name, Calendar startDate, Calendar endDate, int budgetedTime){
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.budgetedTime = budgetedTime;
		
		this.employees = new LinkedList<Employee>();
		this.employeesAsAssistance = new LinkedList<Employee>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
		
	}

	public void setTaskNumber(int taskCount, int year) {
		
		/*
		 *  adds leading zeros so that the number reaches 6 digits.   --> projectCount = 6  becomes 000006
		 *  Thus maximum number of tasks per year becomes 1 million (should suffice?)
		 *  ex.  yyyy999999
		 */
		this.taskNumber = "" + year + String.format("%06d", (taskCount % 1000000));
		
		// total taskNumber:  2015000255   for the 256th task in 2015
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
	}
	
	public void removeEmployees() throws Exception {
		for (Employee employee : employees)
			employee.removeEmployeeFromTask(employee, this);
		employees.clear();
	}
	
	public String toString(){
		return taskNumber + " - " + name;
	}

	// this.startWeek > 45 to make up for tasks overlapping new years eve
	public boolean isOutOfBounds(Calendar startDate, Calendar endDate) {
		return (this.startDate.before(startDate) || this.endDate.after(endDate));
	}

	public boolean endsBeforeStart() {
		// take into account tasks which overlap new years eve
		return this.endDate.before(this.startDate);
	}

	public boolean isInPeriod(Calendar startDate, Calendar endDate) {
		return (this.startDate.compareTo(startDate) >= 0 && this.startDate.before(endDate)) 
				|| (this.endDate.after(startDate) && this.endDate.compareTo(endDate) <= 0)
				|| (this.startDate.compareTo(startDate) <= 0 && this.endDate.compareTo(endDate) >= 0);
	}

	
	public void setTaskLog(Employee employee, int timeWorkedOnTask) {
		
		if (log.containsKey(employee))
			log.put(employee, log.get(employee) + timeWorkedOnTask);
		else 
			log.put(employee, timeWorkedOnTask);
		
	}

//	public void belongsTo(Project project) {
//		this.project = project;
//		
//	}
//
//	public Project getProject() {
//		return project;
//	}

	public int getBudgetedTime() {
		return budgetedTime;
	}

	public int getTimeSpent() {
		int time = 0;
		for (Employee employee : employees) {
			time += employee.getTimeForATask(this);
		}
		return time;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}

//	public void setStartDate(Calendar startDate) {
//		this.startDate = startDate;
//	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void addEmployeeAsAssistance(Employee employee) {
		employeesAsAssistance.add(employee);
	}
	
	public List<Employee> getEmployeesAsAssistance() {
		return employeesAsAssistance;
	}

//	public void setEndDate(Calendar endDate) {
//		this.endDate = endDate;
//	}

	public String getName(){
		return name;
	}
	
}
