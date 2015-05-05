package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import view.View;
import model.Employee;
import model.Project;
import model.Schedule;
import model.Status;

public class CreateProjectController implements ActionListener {

	private Schedule schedule;
	private View view;

	private List<Employee> projectLeaders;
	private String projectName;
	private Employee projectLeader;
	private Calendar startDate = null, endDate = null;
	
	public CreateProjectController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getCreateProjectPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Create project":
				
				projectName = view.getCreateProjectPanel().getProjectName();
				projectLeaders = schedule.searchEmployee(view.getCreateProjectPanel().getProjectLeader());
				
				if (view.getCreateProjectPanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getCreateProjectPanel().getStartDate());
				}
				
				if (view.getCreateProjectPanel().getEndDate() != null) {
					endDate = new GregorianCalendar();
					endDate.setTime(view.getCreateProjectPanel().getEndDate());
				}
				
				if (projectName.equals("")) {
					view.getCreateProjectPanel().setErrorLabel("Give the project a name");
					break;
				}
				
				if (projectLeaders.size() != 1) {
					view.getCreateProjectPanel().setErrorLabel("Wrong initials");
					break;
				}
				
				if (startDate == null) {
					view.getCreateProjectPanel().setErrorLabel("Set a start date");
					break;
				}
				
				if (endDate == null) {
					view.getCreateProjectPanel().setErrorLabel("Set a end date");
					break;
				}
				
				projectLeader = projectLeaders.get(0);
				Project project = new Project(projectName, startDate, endDate, projectLeader);
				try {
					schedule.getUser().createProject(project);
				} catch (Exception error) {
					System.err.println(error);
				}
				view.getProjectPanel().updateList();
				
				view.remove(view.getCreateProjectPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;
				
			case "Back":
				view.remove(view.getCreateProjectPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;

		}
	}
	
}
