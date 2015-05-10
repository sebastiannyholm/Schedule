package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Project {

	private String projectName, projectNumber, description;
	private Calendar startDate, endDate;
	private Employee projectLeader;
	private List<Task> tasks;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public Project(String projectName, Calendar startDate, Calendar endDate, Employee projectLeader){
		
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectLeader = projectLeader;
		
		this.tasks = new LinkedList<Task>();
	
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
	
	public void removeTask(Task task) {
		tasks.remove(task);
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
		
		// ex. Project number = 20150132    for the 133rd project created in 2015.
	}

	public boolean match(String critiria) {
		return projectName.contains(critiria) || projectNumber.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader) {
		projectLeader = newProjectLeader;
		
	}	
	
	public String toString(){
		return projectNumber + " - " + projectName + " from " + df.format(startDate.getTime()) + " to " + df.format(endDate.getTime());
	}

	public int getProjectNumber() {
		return Integer.parseInt(projectNumber);
	}

	/*
	 * Checks whether the project is within a certain
	 * span of time. All possible permutation of nessessary date
	 * comparisons has been listed.
	 */
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

	public String getName() {
		return projectName;
	}

	public void removeTasks() throws Exception {
		for (Task task : tasks)
			task.removeEmployees();
		tasks.clear();
		
	}
	
	public boolean hasTask(Task task) {
		return tasks.contains(task);
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
