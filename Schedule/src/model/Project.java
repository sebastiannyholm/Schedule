package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class Project {

	private String projectName, projectNumber;
	private int startWeek, endWeek;
	private Employee projectLeader;
	
	List<Task> tasks;
	
	public Project(String projectName, int startWeek, int endWeek, Employee employee){
		
		this.projectName = projectName;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
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

	public void setProjectNumber(int projectCount) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		// adds leading zeros so that the number reaches 4 digits.   --> projectCount = 4  becomes 0004
		this.projectNumber = "" + year + String.format("%04d", (projectCount % 10000));

	}

	public boolean match(String critiria) {
		return projectName.contains(critiria) || projectNumber.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader) {
		projectLeader = newProjectLeader;
		
	}	
	
	public String toString(){
		return projectName + " " + projectNumber + ", Estimated time in weeks: " + (endWeek-startWeek) + " -- Project leader: " + projectLeader;
	}

	public int getProjectNumber() {
		return Integer.parseInt(projectNumber);
	}

	public boolean isInPeriod(int startWeek, int endWeek) {
		return (this.startWeek >= startWeek && this.startWeek < endWeek) || (this.endWeek >= startWeek && this.endWeek < endWeek);
	}

	public int getEndWeek() {
		return endWeek;
	}

	public int getStartWeek() {
		return startWeek;
	}
	
	public boolean projectExist(Project newProject) {
		return this.equals(newProject);
	}
}
