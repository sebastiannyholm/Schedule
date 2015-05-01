package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

	private List<Employee> employees;
	private List<SingleJob> jobs;
	private Map<Employee, List<SingleJob>> employeesAndJobs;
	
	
	private String name, taskNumber;
	private Calendar startDate, endDate;
	private int budgetedTime;
	private Map<Employee, Integer> log = new HashMap<Employee, Integer>();

	
	public Task(String name, Calendar startDate, Calendar endDate, int budgetedTime){
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.budgetedTime = budgetedTime;
		this.employees = new ArrayList<Employee>();
		this.jobs = new ArrayList<SingleJob>();
		this.employeesAndJobs = new HashMap<Employee, List<SingleJob>>();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
		this.employeesAndJobs.put(employee, new ArrayList<SingleJob>());
	}

	public void addJob(Employee employee, SingleJob job) {
		jobs.add(job);
		if (!employees.contains(employee))
			addEmployee(employee);
		employeesAndJobs.get(employee).add(job);
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

	public void remove(Employee employee) {
		employees.remove(employee);
		employeesAndJobs.remove(employee);
	}
	
	public String toString(){
		return name + ", " + taskNumber + ", from " + startDate + " to " + endDate + " = " + budgetedTime + " hours";
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

	public List<SingleJob> getJobs() {
		return jobs;
	}
	
	public List<SingleJob> getJobsForAnEmployee(Employee employee) {
		return employeesAndJobs.get(employee);
	}

}
