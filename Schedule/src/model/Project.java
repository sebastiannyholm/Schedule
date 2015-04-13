package model;

import java.util.LinkedList;
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
		
		tasks = new LinkedList<Task>();
	
	}

	public Employee getProjectLeader() {
		return projectLeader;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void addTask(Task task) {
		tasks.add(task);
		
	}

	public boolean match(String critiria) {
		return projectName.contains(critiria) || projectNumber.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader) {
		projectLeader = newProjectLeader;
		
	}	
	
//	public String toString(){
//		return projectName + " " + projectNumber + ", Estimated time in weeks: " + timeInWeeks + " -- Project leader: " + projectLeader;
//	}
}
