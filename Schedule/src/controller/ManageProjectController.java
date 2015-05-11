package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Employee;
import model.Project;
import model.Schedule;
import model.Task;
import view.View;

public class ManageProjectController implements ActionListener {

	private Schedule schedule;
	private View view;
	private Project project;
	private Task task;
	
	public ManageProjectController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getManageProjectPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		project = view.getManageProjectPanel().getProject();
		
		switch (e.getActionCommand()) {
			case "Change project leader":
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getProjectLeaderLabelComp());
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getChangeProjectLeaderComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getProjectLeaderTextComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getSaveProjectLeaderComp());
				view.reset();
				break;
				
			case "Save project leader":
				List<Employee> projectLeaders = schedule.searchEmployee(view.getManageProjectPanel().getProjectLeaderText());
				if (projectLeaders.size() != 1 || view.getManageProjectPanel().getProjectLeaderText().length() != 4) {
					view.getManageProjectPanel().setErrorLabel("Wrong initials");
					break;
				}
				Employee projectLeader = projectLeaders.get(0);
				
				try {
					schedule.getUser().changeProjectLeader(projectLeader, project);
				} catch (Exception error) {
					view.getManageProjectPanel().setErrorLabel(error.getMessage());
				}
				
				view.getManageProjectPanel().setProjectLeaderLabel(projectLeader.getName());
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getProjectLeaderTextComp());
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getSaveProjectLeaderComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getProjectLeaderLabelComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getChangeProjectLeaderComp());
				if (!schedule.getUser().equals(projectLeader))
					view.getManageProjectPanel().disableAllButtons();
				view.reset();
				break;
				
			case "Change description":
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getDescriptionLabelComp());
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getChangeDescriptionComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getDescriptionTextComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getSaveDescriptionComp());
				view.reset();
				break;
				
			case "Save description":
				project.setDescription(view.getManageProjectPanel().getDescriptionText());
				
				view.getManageProjectPanel().setDescriptionLabel(project.getDescription());
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getDescriptionTextComp());
				view.getManageProjectPanel().remove(view.getManageProjectPanel().getSaveDescriptionComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getDescriptionLabelComp());
				view.getManageProjectPanel().add(view.getManageProjectPanel().getChangeDescriptionComp());
				
				view.reset();
				break;	
				
			case "Create task":
				view.resetErrorLabels();
				view.getCreateTaskPanel().setProject(project);
				view.remove(view.getManageProjectPanel());
				view.add(view.getCreateTaskPanel());
				view.reset();
				break;
				
			case "Delete task":
				if ( view.getManageProjectPanel().getSelectedIndex() > -1 ) {
					task = view.getManageProjectPanel().getSelected();
					try {
						schedule.getUser().deleteTask(task, project);
						view.resetErrorLabels();
					} catch (Exception error) {
						view.getManageProjectPanel().setErrorLabel(error.getMessage());
					}
					
					view.getManageProjectPanel().updateList(project);
				}
				else
					view.getManageProjectPanel().setErrorLabel("Please select a task");
				break;

			case "Manage task":
				if ( view.getManageProjectPanel().getSelectedIndex() > -1 ) {
					view.resetErrorLabels();					
					task = view.getManageProjectPanel().getSelected();
					view.getManageTaskPanel().setTask(task);
					view.getManageTaskPanel().setTitleLabel(project.getName() + " - " + task.getName());
					view.getManageTaskPanel().updateList();
					view.getManageTaskPanel().updateAssistenceList();
					view.getManageTaskPanel().updateAssignmentsList();
					view.getManageTaskPanel().setTimeTaskTLabel();
					view.getManageTaskPanel().setSpentTimeTaskTLabel();
					view.getManageTaskPanel().setRegisteredSpentTimeTaskTLabel();
					view.getManageTaskPanel().setDescriptionLabel(task.getDescription());
					view.getManageTaskPanel().setDescriptionText(task.getDescription());
					view.getManageTaskPanel().setStartDate(task.getStartDate());
					view.getManageTaskPanel().setEndDate(task.getEndDate());
					if (schedule.getAllProjects().get(0).getTasks().contains(task))
						view.getManageTaskPanel().removeAssistenceList();
					else
						view.getManageTaskPanel().addAssistenceList();
					
				} else {
					view.getManageProjectPanel().setErrorLabel("Please select a task");
					break;
				}
				
				view.remove(view.getManageProjectPanel());
				view.add(view.getManageTaskPanel());
				view.reset();
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.getManageProjectPanel().enableAllButtons();
				view.getProjectPanel().updateList();
				view.remove(view.getManageProjectPanel());
				view.add(view.getProjectPanel());
				view.reset();
				break;

		}
	}
	
}
