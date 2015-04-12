package model;

import java.util.List;

public class Project {

	String projectName;
	int projectNumber, timeInWeeks;
	Employee projectLeader;
	
	List<Task> tasks;
	
	public Project(String projectName, int projectNumber, int timeInWeeks, Employee employee){
		
		this.projectName = projectName;
		this.projectNumber = projectNumber;
		this.timeInWeeks = timeInWeeks;
		this.projectLeader = employee;
	}

	public Employee getProjectleader() {
		return projectLeader;
	}

	public List<Task> getTasks() {
		return tasks;
	}

}
