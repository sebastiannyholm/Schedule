package model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Project {

	private String projectName, projectNumber;
	private Calendar startDate, endDate;
	private Employee projectLeader;
	
	List<Task> tasks;
	
	public Project(String projectName, Calendar startDate, Calendar endDate, Employee projectLeader){
		
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectLeader = projectLeader;
		
		tasks = new LinkedList<Task>();
	
	}
	
	public void addProjectToProjectLeader() {
		projectLeader.addProject(this);
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

	public void setProjectNumber(int projectCount, int year) {
		
		/* 
		 * adds leading zeros so that the number reaches 4 digits.   --> projectCount = 4  becomes 0004
		 * thus the upper limit for project creations per year is 9999.
		 * =>  yyyy9999
		 * if more projects are added after this point (during the same year) 
		 * it will overwrite the first project, created that year.
		 */
		
		this.projectNumber = "" + year + String.format("%04d", (projectCount % 10000));
		
		// Project number = 20150132    for the 133rd project created in 2015.
	}

	public boolean match(String critiria) {
		return projectName.contains(critiria) || projectNumber.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader) {
		projectLeader = newProjectLeader;
		
	}	
	
	public String toString(){
		return projectName + " " + projectNumber + ", Project period from " + startDate + " to " + endDate + " -- Project leader: " + projectLeader;
	}

	public int getProjectNumber() {
		return Integer.parseInt(projectNumber);
	}

	public boolean isInPeriod(Calendar startDate, Calendar endDate) {
		return (this.startDate.compareTo(startDate) >= 0  && this.startDate.compareTo(endDate) < 0)
				|| (this.endDate.compareTo(startDate) > 0 && this.endDate.compareTo(endDate) <= 0)
				|| (this.startDate.compareTo(startDate) <= 0 && this.endDate.compareTo(endDate) >= 0);
	}

	public Calendar getEndDate() {
		return this.endDate;
	}

	public Calendar getStartDate() {
		return this.startDate;
	}
	
	public boolean projectExist(Project newProject) {
		return this.equals(newProject);
	}
}
