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
	private boolean superWorker;
	
	
	public Employee(String name, String initials, int age, Address address, Schedule schedule) {
		this.name = name;
		this.initials = initials;
		this.age = age;
		this.address = address;
		this.schedule = schedule;
		this.superWorker = false;
		
		projects = new ArrayList<Project>();
		tasks = new ArrayList<Task>();
	}

	public void createProject(Project newProject) throws Exception {
		for (Project project : schedule.getAllProjects())
			if (project.projectExist(newProject)) 
				throw new OperationNotAllowedException("You can't create the same project multiple times.", "Create project");
		projects.add(newProject);
		schedule.addProject(newProject);
		newProject.setProjectNumber(schedule.getAllProjects().size()-1);
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
		}
		project = null;	
	}

	public void removeTask(Task task) {
		tasks.remove(task);
		task.remove(this);
		
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
		else if (task.endsBeforeStart()) 
			throw new OperationNotAllowedException("Task ends before it even begins!", "Add task");
		else if (task.isOutOfBounds(project.getStartWeek(), project.getEndWeek()))
			throw new OperationNotAllowedException("Task span does not comply with project bounds!", "Add task");
		project.addTask(task);
		schedule.addTask(task);
		task.setTaskNumber(schedule.getAllTasks().size()-1);
	}

	public void addEmployeeToTask(Employee employee, Task task) throws Exception {
		// regular employees can't work on more than 10 tasks at once
		if (employee.getTasks().contains(task)) 
			throw new OperationNotAllowedException("The employee " + employee + " is already working on this task!", "Add employee to task");
		else if ((employee.getTasks().size() >= 10 && !employee.isSuperWorker()) || employee.getTasks().size() == 20 )
			throw new OperationNotAllowedException("The employee " + employee + " is already working on the maximum amount of tasks!", 
					"Add employee to task");

		employee.setTasks(task);
		task.addEmployee(employee);
	}

	public boolean match(String critiria) {
		return initials.contains(critiria) || name.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader, Project project) {
		project.changeProjectLeader(newProjectLeader);
		newProjectLeader.projects.add(project);
		projects.remove(project);
	}
	
	public void setTasks(Task task) {
		tasks.add(task);
		
	}

	public String toString(){
		return name + ", " + initials + ", aged " + age + ", living in " + address;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setSuperWorker(boolean state) {
		superWorker  = state;
		
	}
	
	public boolean isSuperWorker(){
		return superWorker;
	}

}
