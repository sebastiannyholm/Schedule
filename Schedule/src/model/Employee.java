package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {

	private String initials, name;
	private int age;
	private Address address;
	
	List<Project> projects = new ArrayList<Project>();
	
	public Employee(String name, String initials, int age, Address address) {
		this.name = name;
		this.initials = initials;
		this.age = age;
		this.address = address;
	}

	public void createProject(Project project) {
		projects.add(project);
		
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void deleteProject(Project project) throws Exception{
		try{
			// remove reference to the object entirely --> removed by garbage collector.
			projects.remove(project);
			for (Task task : project.getTasks()){
				task.removeTask();
			}
			
			
			project = null;	
		} catch (IllegalArgumentException e){
			throw new ProjectNotFoundException("Cannot remove a project which does not exist!", "Delete project");
		}
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
	
}
