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
				
				if (projectLeaders.size() != 1 || view.getCreateProjectPanel().getProjectLeader().length() != 4) {
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
					view.resetErrorLabels();
				} catch (Exception error) {
					view.getCreateProjectPanel().setErrorLabel(error.getMessage());
					break;
				}
				
				project.setDescription(view.getCreateProjectPanel().getProjectDescription());
				
				view.getManageProjectPanel().setProject(project);
				view.getManageProjectPanel().updateList(project);
				view.getManageProjectPanel().setTitleLabel(project.getName());
				view.getManageProjectPanel().setProjectLeaderLabel(project.getProjectLeader().getName());
				view.getManageProjectPanel().setProjectLeaderText(project.getProjectLeader().getInitials());
				view.getManageProjectPanel().setDescriptionLabel(project.getDescription());
				view.getManageProjectPanel().setDescriptionText(project.getDescription());
				view.getManageProjectPanel().setStartDate(startDate);
				view.getManageProjectPanel().setEndDate(endDate);
				view.getProjectPanel().updateList();
				
				if (!schedule.getUser().equals(projectLeader))
					view.getManageProjectPanel().disableAllButtons();
				
				view.add(view.getManageProjectPanel());
				view.remove(view.getCreateProjectPanel());
				
				view.reset();
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.remove(view.getCreateProjectPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;

		}
	}
	
}
