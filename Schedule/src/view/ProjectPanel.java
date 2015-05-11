package view;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.SubTitleLabel;
import jcomponents.TitleLabel;
import controller.ProjectController;
import model.Employee;
import model.Project;
import model.Schedule;

public class ProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	
	private TitleLabel titleLabel;
	private SubTitleLabel projectListTitleLabel;
	private ErrorLabel errorLabel;
	private JButton createProject, deleteProject, manageProject, back;
	private JList projectList;
	private JScrollPane scrollPane;
	private DefaultListModel<Project> projects = new DefaultListModel<Project>();
	
	public ProjectPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("My projects");
		this.projectListTitleLabel = new SubTitleLabel("All projects");
		this.projectList = new JList(projects);
		this.createProject = new JButton("Create project");
		this.deleteProject = new JButton("Delete project");
		this.manageProject = new JButton("Manage project");
		this.back = new JButton("Back");
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(projectList);
		this.errorLabel = new ErrorLabel("");
		
		this.setLayout(null);
		
		titleLabel.setLocation(20, 20);
		projectListTitleLabel.setBounds(20, 80, 200, 20);
		scrollPane.setBounds(20, 120, 500, 380);
		
		createProject.setBounds(20, 520, 120, 40);
		deleteProject.setBounds(160, 520, 120, 40);
		manageProject.setBounds(300, 520, 150, 40);
		errorLabel.setBounds(470, 520, 300, 40);
		back.setBounds(820,520,120,40);
		
		this.add(titleLabel);
		this.add(projectListTitleLabel);
		this.add(scrollPane);
		this.add(createProject);
		this.add(deleteProject);
		this.add(manageProject);
		this.add(back);
		this.add(errorLabel);
		
	}

	public void registerListener(ProjectController controller) {
		createProject.addActionListener(controller);
		deleteProject.addActionListener(controller);
		manageProject.addActionListener(controller);
		back.addActionListener(controller);
	}
	
	public int getSelectedIndex() {
		return projectList.getSelectedIndex();
	}
	
	public Project getSelected() {
		int index = projectList.getSelectedIndex();
		
		return projects.get(index);
	}	
	
	public void updateList() {
		
		projects.clear();
		
		for (Project project : schedule.getUser().getProjects())
			projects.addElement(project);
		
	}
	
	public void setErrorLabel(String error){
		errorLabel.setText(error);
	}
}
