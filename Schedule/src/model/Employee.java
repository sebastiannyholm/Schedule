package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Employee {

	private Schedule schedule;
	private String initials, name;
	private int age;
	private Address address;
	private boolean admin = false;
	
	private LinkedList<Project> projects;
	private LinkedList<Task> tasks;
	private Map<Task,LinkedList<Assignment>> tasksAndTime;
	private boolean superWorker;
	private int punchIn, punchOut;
	private Map<String, Integer> workLog = new HashMap<String, Integer>();

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public Employee(String name, String initials, int age, Address address, Schedule schedule) {
		this.name = name;
		this.initials = initials;
		this.age = age;
		this.address = address;
		this.schedule = schedule;
		this.superWorker = false;
		this.projects = new LinkedList<Project>();
		this.tasks = new LinkedList<Task>();
		this.tasksAndTime = new HashMap<Task, LinkedList<Assignment>>();
	}
	
	public void createProject(Project newProject) throws Exception {
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You need to be logged in to create a project", "Create project");
		else if (newProject.getStartDate().after(newProject.getEndDate()))
			throw new OperationNotAllowedException("Bad project bounds - Project ends before it begins", "Create project");
		
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
		
	public void deleteProject(Project project) throws Exception {
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You need to be logged in to delete a project", "Delete project");
		else if (!this.equals(project.getProjectLeader())) 
			throw new OperationNotAllowedException("Cannot remove a project if not its leader", "Delete project");
		
		/*
		 *  remove reference to the object entirely --> removed by garbage collector.
		 *  Clean up all the tasks connected to a project being removed
		 */
		project.removeTasks();
		projects.remove(project);
		schedule.removeProject(project);
		project = null;	
	}

	public String getInitials() {
		return initials;
	}
	
	public boolean matchInitials(String initials) {
		return this.initials.equals(initials);
	}
	
	public boolean hasInitialsOnFourLetters() {
		return initials.length() == 4;
	}
	
	public boolean hasSameInitials(String initials) {
		return this.initials.equals(initials);
	}

	public void createTask(Task task, Project project) throws Exception {
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You need to be logged in to add a task", "Add task");
		else if (!this.equals(project.getProjectLeader()))
			throw new OperationNotAllowedException("Only the project leader may add a task to a project", "Add task");
		else if (task.endsBeforeStart()) 
			throw new OperationNotAllowedException("Task ends before it even begins!", "Add task");
		else if (task.isOutOfBounds(project.getStartDate(), project.getEndDate()))
			throw new OperationNotAllowedException("Task span does not comply with project bounds!", "Add task");
		
		project.addTask(task);
		task.setTaskNumber(schedule.getAllTasks().size() - 1, schedule.getDate().get(GregorianCalendar.YEAR));
	}
	
	public void deleteTask(Task task, Project project) throws Exception {
		task.removeEmployees();
		project.removeTask(task);
	}

	public void addEmployeeToTask(Employee employee, Task task, Calendar startDate, int time) throws Exception { // time is measured in minutes
//		regular employees can't work on more than 10 tasks at once
		
		if (!isProjectLeader(task)) 
			throw new OperationNotAllowedException("Only the project leader may add an employee to the task", "Add employee to task");
		else if ((employee.getTasks().size() >= 10 && !employee.isSuperWorker()) || employee.getTasks().size() == 20 )
			throw new OperationNotAllowedException("The employee " + employee + " is already working on the maximum amount of tasks!", "Add employee to task");
		else if (time + task.getTimeSpent() > task.getBudgetedTime()*60)
			throw new OperationNotAllowedException("You have exceeded the time limit for the task", "Add employee to task");
		else if (employee.checkAgenda(startDate, time))
			throw new OperationNotAllowedException("The employee does not have time in this period", "Add employee to task");
		
		employee.addTask(task, startDate, time);
		task.addEmployee(employee);
		
	}
	
	public boolean isProjectLeader(Task task) {
		for (Project project : projects)
			if (project.hasTask(task))
				return true;
		return false; 
	}

	public void addEmployeeToAbsence(Employee employee, Task task, Calendar startDate, int time) throws Exception { // time is measured in minutes
		employee.addTask(task, startDate, time);
		task.addEmployee(employee);
		
	}
	
	private boolean checkAgenda(Calendar startDate, int time) {
		Calendar endDate = new GregorianCalendar();
		endDate.setTime(startDate.getTime());
		// convert to minutes
		setEndDate(endDate, time + startDate.get(Calendar.HOUR_OF_DAY)*60 + startDate.get(Calendar.MINUTE) - 8*60);
		
		for (Task task : tasksAndTime.keySet())
			for (Assignment assignment : tasksAndTime.get(task)){
				if (assignment.isInPeriod(startDate, endDate))
					return true;
			}
		return false;
	}
	
	/*
	 * Orders the assignments list numerically based on date.
	 * To create a nicer looking overview and improve readability
	 */
	public List<Assignment> getTodaysAgenda() {
		PriorityQueue<Assignment> priorityQueue = new PriorityQueue<Assignment>();
		Calendar date = schedule.getDate();
		Calendar startDate = (GregorianCalendar) date.clone();
		Calendar endDate = (GregorianCalendar) date.clone();
		startDate.set(GregorianCalendar.HOUR_OF_DAY, 8);
		endDate.add(GregorianCalendar.HOUR_OF_DAY, 16);
		
		for (Task task : tasksAndTime.keySet())
			for (Assignment assignment : tasksAndTime.get(task))
				if (assignment.isInPeriod(startDate, endDate))
					priorityQueue.insert(assignment, assignment.getStartDate());
		
		LinkedList<Assignment> assignments = new LinkedList<Assignment>();
		
		while (!priorityQueue.isEmpty())
			assignments.add(priorityQueue.extractMin());
			
		
		return assignments;
	}
	
	public void addTask(Task task, Calendar startDate, int time) {
		Calendar endDate = new GregorianCalendar();
		endDate.setTime(startDate.getTime());
		
		setEndDate(endDate, time + startDate.get(Calendar.HOUR_OF_DAY)*60 + startDate.get(Calendar.MINUTE) - 8*60);
		
		if (!tasksAndTime.containsKey(task)){
			tasks.add(task);
			tasksAndTime.put(task, new LinkedList<Assignment>());
		}
		tasksAndTime.get(task).add(new Assignment(task, startDate, endDate, time));
	}
	
	private void setEndDate(Calendar endDate, int time) {
		
		Calendar fakeStartDate = new GregorianCalendar();
		fakeStartDate.setTime(endDate.getTime());
		
		while (time >= 480) {
			endDate.add(Calendar.DAY_OF_YEAR, 1);
			time-=480;
		}
				
		endDate.set(Calendar.HOUR_OF_DAY, 8);
		while (time >= 60) {
			endDate.add(Calendar.HOUR_OF_DAY, 1);
			time -= 60;
		}
		
		endDate.set(Calendar.MINUTE, time + 0);

		Calendar fakeEndDate = new GregorianCalendar();
		fakeEndDate.setTime(endDate.getTime());
		
		int weekendDays = 0;
		while(fakeEndDate.after(fakeStartDate)) {
		    if(fakeStartDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fakeStartDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
		    	weekendDays++;
		    	fakeEndDate.add(Calendar.DAY_OF_YEAR,1);
		    }
		    
		    fakeStartDate.add(Calendar.DAY_OF_YEAR,1);
		}
		
		endDate.add(Calendar.DAY_OF_YEAR, weekendDays);
		
		if (endDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || endDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			endDate.add(Calendar.DAY_OF_YEAR, 2);
		
	}

	public void removeEmployeeFromTask(Employee employee, Task task) throws Exception {
		employee.tasks.remove(task);
		employee.tasksAndTime.remove(task);
		task.removeEmployee(employee);
	}
	
	public boolean match(String critiria) {
		return initials.contains(critiria) || name.contains(critiria);
	}

	public void changeProjectLeader(Employee newProjectLeader, Project project) throws Exception {
		if (!this.equals(project.getProjectLeader())) 
			throw new OperationNotAllowedException("You can't change the project leader, if you are not the current one", "Change project leader");
		
		project.changeProjectLeader(newProjectLeader);
		newProjectLeader.addProject(project);
		projects.remove(project);
	}
	
	public String toString(){
		return name;
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
		List<Task> tasksInPeriod = new LinkedList<Task>();
		
		for (Task task : tasks)
			if (task.isInPeriod(startDate, endDate))
				tasksInPeriod.add(task);
		return tasksInPeriod;
	}

	public void punchIn() {		
		// get hour of day
		punchIn = schedule.getDate().get(Calendar.HOUR_OF_DAY)*60+schedule.getDate().get(Calendar.MINUTE);		// get the current time in minutes
		
	}
	
	public void punchOut() {
		// get the new time 
		punchOut = schedule.getDate().get(Calendar.HOUR_OF_DAY)*60+schedule.getDate().get(Calendar.MINUTE);		// get the current time in minutes
		
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

	
	public void startWorkingOnAssignment(Assignment assignment) {
		assignment.startTimer();

	}
	
	public void stopWorkingOnAssignment(Assignment assignment) {
		assignment.stopTimer();		
	}
	

	public void changeTimeWorkedOnAnAssignment(Assignment assignment, int time) {
		assignment.setRegisteredTime(time);
	}


	public int getAssignmentTimeSpentInMinutes(Assignment assignment) {
		return (int) (assignment.getRegisteredTime() / (1000*60));

	}

	public void reportAbsence(Employee employee, Enum<Status> reason, Calendar startDate, int time) throws Exception {
		
		/*
		 * Premade project and tasks in schedule:
		 * PROJECT 0 --> ABSENCE project which contains 3 tasks (0-based):
		 * 0) Sickness
		 * 1) Vacation
		 * 2) Course
		 */
		
		startDate.set(Calendar.HOUR_OF_DAY, 8);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		
		if (reason == Status.SICK)
			this.addEmployeeToAbsence(employee, schedule.getAllProjects().get(0).getTasks().get(0), startDate, time);
		else if (reason == Status.VACATION)
			this.addEmployeeToTask(employee, schedule.getAllProjects().get(0).getTasks().get(1), startDate, time);
	
	}

	public boolean isAbsent() {
		
		for (Task absent : schedule.getAllProjects().get(0).getTasks()) {
			if (tasksAndTime.get(absent) == null)
				continue;
			for (Assignment assignment : tasksAndTime.get(absent))
				if(assignment.isToday(schedule.getDate()))
					return true;
		}
		return false;
	}

	public void addProject(Project project) {
		this.projects.add(project);
	}

	public int getTimeForATask(Task task) {
		int time = 0;
		for (Assignment assignment : tasksAndTime.get(task))
			time += assignment.getTime();
		return time; 
	}

	public int getRegisteredTimeForATask(Task task) {
		int time = 0;
		for (Assignment assignment : tasksAndTime.get(task))
			time += assignment.getRegisteredTime();
		return time; 
	}
	
	public Map<Task, LinkedList<Assignment>> getTasksAndTime() {
		return tasksAndTime;
	}

	public List<Employee> getAbsentEmployees() {
		List<Employee> absentEmployees = new LinkedList<Employee>();
		
		for (Project project : projects){
			for (Task task : project.getTasks()){
				for (Employee employee : task.getEmployees()){
					if (employee.isAbsent())
						absentEmployees.add(employee);
				}
			}
		}
		
		return absentEmployees;
	}

	public String getName() {
		return name;
	}

	public List<Employee> getFreeEmployeesInPeriod(Calendar startDate, int time) {
		List<Employee> freeEmployeesInPeriod = new LinkedList<Employee>();
		freeEmployeesInPeriod.addAll(schedule.getEmployees());
		Calendar endDate = new GregorianCalendar();
		endDate.setTime(startDate.getTime());
		setEndDate(endDate, time);
		
		for (Employee employee : schedule.getEmployees())
			for (Task task : employee.tasksAndTime.keySet())
				for (Assignment assignment : employee.tasksAndTime.get(task))
					if (assignment.isInPeriod(startDate, endDate)) 
						freeEmployeesInPeriod.remove(employee);			
		
		return freeEmployeesInPeriod;
	}

	public void requireAssistance(Employee employee, Task task, Calendar startDate, int time) throws Exception {
		if (!task.getEmployees().contains(this))
			throw new OperationNotAllowedException("You can not require assistance if you are not on the task!", "Require assistance");		
		else if ((employee.getTasks().size() >= 10 && !employee.isSuperWorker()) || employee.getTasks().size() == 20 )
			throw new OperationNotAllowedException("The employee " + employee + " is already working on the maximum amount of tasks!", "Add employee to task");
		else if (time + task.getTimeSpent() > task.getBudgetedTime()*60)
			throw new OperationNotAllowedException("You have exceeded the time limit for the task", "Add employee to task");
		else if (employee.checkAgenda(startDate, time))
			throw new OperationNotAllowedException("The employee does not have time in this period", "Add employee to task");
		
		employee.addTask(task, startDate, time);
		task.addEmployeeAsAssistance(employee);
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}
	
	public void addEmployee(Employee employee) throws Exception {
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You can't add an employee if you are not logged in!", "Add employee");
		if (!isAdmin())
			throw new OperationNotAllowedException("Only administrators can add employees!", "Add employee");
		
		schedule.addEmployee(employee);
	}

	public void removeEmployee(Employee employee) throws Exception {
		if (!schedule.isLoggedIn())
			throw new OperationNotAllowedException("You can't remove an employee if you are not logged in!", "Remove employee");
		if (!isAdmin())
			throw new OperationNotAllowedException("Only administrators can remove employees!", "Remove employee");
		if (employee == this)
			throw new OperationNotAllowedException("You cannot remove yourself from the system!", "Remove employee");
		schedule.removeEmployee(employee);
	}

	public boolean workedToMuchOnAnAssignment(Assignment assignment) {
		return assignment.limitExceeded();
	}

	public void setProjectDescription(String description, Project project) {
		project.setDescription(description);
	}

	public void setTaskDescription(String description, Task task) {
		task.setDescription(description);
	}


}
