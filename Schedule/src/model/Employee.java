package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {

	private Schedule schedule;
	private String initials, name;
	private int age;
	private Address address;
	
	private List<Project> projects;
	private List<Task> tasks;
	
	
	public Employee(String name, String initials, int age, Address address, Schedule schedule) {
		this.name = name;
		this.initials = initials;
		this.age = age;
		this.address = address;
		this.schedule = schedule;
		
		projects = new ArrayList<Project>();
		tasks = new ArrayList<Task>();
	}

	public void createProject(Project project) {
		projects.add(project);
		schedule.addProject(project);
		
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void deleteProject(Project project) throws Exception{
		if (!this.equals(project.getProjectLeader())) 
			throw new OperationNotAllowedException("Cannot remove a project if not its leader", "Delete project");
		
		// remove reference to the object entirely --> removed by garbage collector.
		projects.remove(project);
		for (Task task : project.getTasks()){
			for (Employee employee : task.getEmployees()){
				employee.removeTask(task);
			}
			task = null;
			System.out.println(task);
		}
		project = null;	
	}
	
	private void removeTask(Task task) {
		tasks.remove(task);
		
	}

	public String getInitials() {
		return initials;
	}
	
	public boolean matchInitials(String initials) {
		return this.initials.equals(initials);
	}
	
	public boolean hasInitialsOnFourLetters() {
		return initials.length() != 4;
	}
	
	public boolean hasSameInitials(String initials) {
		return this.initials.equals(initials);
	}

	public void addTask(Task task, Project project) throws Exception {
		if (!this.equals(project.getProjectLeader())){
			throw new OperationNotAllowedException("Only the project leader may add a task to a project", "Add task");
		}
		project.addTask(task);
	}

	public boolean match(String critiria) {
		return initials.contains(critiria) || name.contains(critiria);
	}
	
//	public String toString(){
//		return name + ", " + initials + ", aged " + age + ", living in " + address;
//	}
}
