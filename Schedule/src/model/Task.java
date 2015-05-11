package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Task {

	private List<Employee> employees;
	private List<Employee> employeesAsAssistance;
	private String name, taskNumber, description = "";
	private Calendar startDate, endDate;
	private int budgetedTime;
	private Map<Employee, Integer> log = new HashMap<Employee, Integer>();
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	
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
		return taskNumber + " - " + name + " from " + df.format(startDate.getTime()) + " to " + df.format(endDate.getTime());
	}
	
	/*
	 * Make sure the specified start- and endDate of the task
	 * is within the allowed bounds.
	 */
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

	public int getBudgetedTime() {
		return budgetedTime;
	}

	public int getTimeSpent() {
		List<Employee> employeesList = new LinkedList<Employee>();
		int time = 0;
		for (Employee employee : employees)
			employeesList.add(employee);
		
		for (Employee employee : employeesAsAssistance)
			if (!employeesList.contains(employee))
				employeesList.add(employee);
			
		
		for (Employee employee : employeesList) {
			time += employee.getTimeForATask(this);
		}
		return time;
	}
	
	public int getRegisteredTime() {
		List<Employee> employeesList = new LinkedList<Employee>();
		int time = 0;
		for (Employee employee : employees)
			employeesList.add(employee);
		
		for (Employee employee : employeesAsAssistance)
			if (!employeesList.contains(employee))
				employeesList.add(employee);
			
		
		for (Employee employee : employeesList) {
			time += employee.getRegisteredTimeForATask(this);
		}
		return time;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void addEmployeeAsAssistance(Employee employee) {
		employeesAsAssistance.add(employee);
	}
	
	public List<Employee> getEmployeesAsAssistance() {
		return employeesAsAssistance;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
