package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class Project {

	private String projectName, projectNumber;
	private GregorianCalendar startDate, endDate;
	private Employee projectLeader;
	
	List<Task> tasks;
	
	public Project(String projectName, GregorianCalendar startDate, GregorianCalendar endDate, Employee employee){
		
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectLeader = employee;
		
		projectLeader.addProject(this);
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
		int yearDiff = endDate.get(GregorianCalendar.YEAR) - startDate.get(GregorianCalendar.YEAR);
		return projectName + " " + projectNumber + ", Estimated time in weeks: " + (endDate.get(GregorianCalendar.WEEK_OF_YEAR)*yearDiff-startDate.get(GregorianCalendar.WEEK_OF_YEAR)) + " -- Project leader: " + projectLeader;
	}

	public int getProjectNumber() {
		return Integer.parseInt(projectNumber);
	}

	public boolean isInPeriod(GregorianCalendar startDate, GregorianCalendar endDate) {
		return (this.startDate.compareTo(startDate) >= 0  && this.startDate.compareTo(endDate) < 0)
				|| (this.endDate.compareTo(startDate) > 0 && this.endDate.compareTo(endDate) <= 0)
				|| (this.startDate.compareTo(startDate) <= 0 && this.endDate.compareTo(endDate) >= 0);
	}

	public GregorianCalendar getEndDate() {
		return this.endDate;
	}

	public GregorianCalendar getStartDate() {
		return this.startDate;
	}
	
	public boolean projectExist(Project newProject) {
		return this.equals(newProject);
	}
}
