package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {

	private Schedule schedule;
	private String initials, name;
	private int age;
	private Address address;
	
	private List<Project> projects;
	private List<Task> tasks;
	private boolean superWorker;
	private int punchIn, punchOut, taskIn, taskOut;
	private Map<String, Integer> workLog = new HashMap<String, Integer>();
	private Map<Task, Integer> taskLog = new HashMap<Task, Integer>();
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private boolean absence = false;
	private Enum<Status> status;
	
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
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You need to be logged in to create a project", "Create project");
		else if (!this.equals(schedule.getUser()))
			throw new OperationNotAllowedException("You need to be the one logged in to create a project", "Add task");
		
		for (Project project : schedule.getAllProjects())
			if (project.projectExist(newProject)) 
				throw new OperationNotAllowedException("You can't create the same project multiple times.", "Create project");
		
		schedule.addProject(newProject);
		newProject.setProjectNumber(schedule.getAllProjects().size()-1, schedule.getDate().get(GregorianCalendar.YEAR));
		newProject.addProjectToProjectLeader();
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void deleteProject(Project project) throws Exception{
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You need to be logged in to delete a project", "Create project");
		else if (!this.equals(schedule.getUser()))
			throw new OperationNotAllowedException("You need to be the one logged in to delete a project", "Add task");
		
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
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You need to be logged in to add a task", "Add task");
		else if (!this.equals(schedule.getUser()))
			throw new OperationNotAllowedException("You need to be the one logged in to add a task", "Add task");
		else if (!this.equals(project.getProjectLeader()))
			throw new OperationNotAllowedException("Only the project leader may add a task to a project", "Add task");
		else if (task.endsBeforeStart()) 
			throw new OperationNotAllowedException("Task ends before it even begins!", "Add task");
		else if (task.isOutOfBounds(project.getStartDate(), project.getEndDate()))
			throw new OperationNotAllowedException("Task span does not comply with project bounds!", "Add task");
		project.addTask(task);
		schedule.addTask(task);
		task.setTaskNumber(schedule.getAllTasks().size()-1, schedule.getDate().get(GregorianCalendar.YEAR));
		task.belongsTo(project);
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
		task.getProject().addEmployee(this);
		
	}

	public boolean match(String critiria) {
		return initials.contains(critiria) || name.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader, Project project) throws Exception {
		if (!this.equals(project.getProjectLeader())) 
			throw new OperationNotAllowedException("You can't change the project leader, if you are not the current one", "Change project leader");
		
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

	public List<Task> getAgenda() {
		Calendar date = schedule.getDate();
		Calendar startDate = (GregorianCalendar) date.clone();
		Calendar endDate = (GregorianCalendar) date.clone();
		endDate.add(GregorianCalendar.DAY_OF_YEAR, 7);
		return this.getTasksInPeriod(startDate, endDate);
		
	}

	public List<Task> getTasksInPeriod(Calendar startDate, Calendar endDate) {
		List<Task> tasksInPeriod = new ArrayList<Task>();
		
		for (Task task : tasks)
			if (task.isInPeriod(startDate, endDate))
				tasksInPeriod.add(task);
		return tasksInPeriod;
	}

	public void punchIn() {		
		// get hour of day
		punchIn = schedule.getDate().get(Calendar.HOUR_OF_DAY)*60+schedule.getDate().get(Calendar.MINUTE);		// get the current time in minutes
		//punchIn = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);		// get the current time in minutes
		
	}
	
	public void punchOut() {
		// get the new time 
		punchOut = schedule.getDate().get(Calendar.HOUR_OF_DAY)*60+schedule.getDate().get(Calendar.MINUTE);		// get the current time in minutes
		//punchOut = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);		// get the current time in minutes
		
		int workMinutes = punchOut-punchIn;
		// to compensate for late shifts working past midnight
		
		 if (workMinutes < 0)
			 workMinutes += 24*60;
		 
		 Date date = schedule.getDate().getTime();
		 
		if (workLog.containsKey(df.format(date)))
			workLog.put(df.format(date), workLog.get(df.format(date)) + workMinutes);
		else 
			workLog.put(df.format(date), workMinutes);
		
	}
	
	public int getWorkLogValue(Date date){
		return workLog.get(df.format(date));
	}

	
	public void startWorkingOnTask(Task task) {
		taskIn = schedule.getDate().get(Calendar.HOUR_OF_DAY)*60+schedule.getDate().get(Calendar.MINUTE);	// get the current time in minutes

	}
	
	public void stopWorkingOnTask(Task task) {
		taskOut = schedule.getDate().get(Calendar.HOUR_OF_DAY)*60+schedule.getDate().get(Calendar.MINUTE);	// get the current time in minutes
		
		int timeWorkedOnTask = taskOut - taskIn;
		
		if (taskLog.containsKey(task))
			taskLog.put(task, taskLog.get(task) + timeWorkedOnTask);
		else 
			taskLog.put(task, timeWorkedOnTask);
	
		task.setTaskLog(this, timeWorkedOnTask);
		
	}

	public int getTaskLogValue(Task task) {
		return taskLog.get(task);
	}

	public void reportAbsence(Employee employee, Enum<Status> reason) {
		employee.setAbsent(reason);
		
	}
	
	public void setAbsent(Enum<Status> status){
		// add the employee to the project defined by status (Sickness, Vacation or Course)
		this.absence = true;
		this.status = status;
	}

	public boolean isAbsent() {
		return absence;
	}

	public void addProject(Project project) {
		this.projects.add(project);
		
	}
	
	public void returnFromAbsence(){
		
	}

	public Enum<Status> getStatus() {
		return status;
	}

	
}

