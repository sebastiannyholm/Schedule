package view;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.SubTitleLabel;
import jcomponents.TitleLabel;
import controller.ManageProjectController;
import model.Project;
import model.Schedule;
import model.Task;

public class ManageProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	private Project project;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private TitleLabel titleLabel;
	private SubTitleLabel taskListTitleLabel, taskInformation;
	private Label projectLeaderLabel, projectLeaderLabelLabel, descriptionLabel, descriptionLabelLabel, startDateLabel, startDateLabelLabel, endDateLabel, endDateLabelLabel;
	private JTextField projectLeaderText;
	private JTextArea descriptionText;
	private ErrorLabel errorLabel;
	private JButton createTask, deleteTask, manageTask, changeProjectLeader, saveProjectLeader, back, changeDescription, saveDescription;
	private JList taskList;
	private JScrollPane scrollPane, descriptionTextScrollPane;
	private DefaultListModel<Task> tasks = new DefaultListModel<Task>();
	
	public ManageProjectPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("");
		this.taskListTitleLabel = new SubTitleLabel("The projects tasks");
		this.taskList = new JList(tasks);
		this.createTask = new JButton("Create task");
		this.deleteTask = new JButton("Delete task");
		this.manageTask = new JButton("Manage task");
		this.back = new JButton("Back");
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(taskList);
		this.errorLabel = new ErrorLabel("");
		this.taskInformation = new SubTitleLabel("Information about the project");
		
		this.projectLeaderLabel = new Label("");
		this.projectLeaderLabelLabel = new Label("Project leader");
		this.projectLeaderText = new JTextField("");
		this.changeProjectLeader = new JButton("Change project leader");
		this.saveProjectLeader = new JButton("Save project leader");
		
		this.descriptionLabel = new Label("");
		this.descriptionLabelLabel = new Label("Project description");
		this.descriptionText = new JTextArea("");
		this.changeDescription = new JButton("Change description");
		this.saveDescription = new JButton("Save description");
		this.descriptionTextScrollPane = new JScrollPane(descriptionText);
		
		this.startDateLabel = new Label("");
		this.startDateLabelLabel = new Label("Start date");
		
		this.endDateLabel = new Label("");
		this.endDateLabelLabel = new Label("End date");
		
		this.setLayout(null);
		
		titleLabel.setLocation(20, 20);
		taskListTitleLabel.setBounds(20, 80, 200, 20);
		scrollPane.setBounds(20, 120, 500, 380);
		
		createTask.setBounds(20, 520, 120, 40);
		deleteTask.setBounds(160, 520, 120, 40);
		manageTask.setBounds(300, 520, 120, 40);
		
		errorLabel.setBounds(440, 520, 300, 40);
		back.setBounds(820,520,120,40);
		
		taskInformation.setBounds(540, 80, 400, 20);
		
		projectLeaderLabelLabel.setBounds(540, 120, 200, 20);
		projectLeaderLabel.setBounds(540, 140, 220, 40);
		projectLeaderText.setBounds(540, 140, 220, 40);
		changeProjectLeader.setBounds(780, 140, 160, 40);
		saveProjectLeader.setBounds(780, 140, 160, 40);
		
		descriptionLabelLabel.setBounds(540, 200, 220, 20);
		descriptionLabel.setBounds(540, 220, 220, 100);
		descriptionTextScrollPane.setBounds(540, 220, 220, 100);
		changeDescription.setBounds(780, 220, 160, 40);
		saveDescription.setBounds(780, 220, 160, 40);

		startDateLabelLabel.setBounds(540, 340, 100, 20);
		startDateLabel.setBounds(540, 360, 100, 40);
		
		endDateLabelLabel.setBounds(660, 340, 100, 20);
		endDateLabel.setBounds(660, 360, 100, 40);
		
		this.add(titleLabel);
		this.add(taskListTitleLabel);
		this.add(scrollPane);
		this.add(createTask);
		this.add(deleteTask);
		this.add(manageTask);
		this.add(back);
		this.add(errorLabel);
		this.add(taskInformation);
		this.add(projectLeaderLabelLabel);
		this.add(projectLeaderLabel);
		this.add(changeProjectLeader);
		this.add(descriptionLabelLabel);
		this.add(descriptionLabel);
		this.add(changeDescription);
		this.add(startDateLabelLabel);
		this.add(startDateLabel);
		this.add(endDateLabelLabel);
		this.add(endDateLabel);
		
	}
	
	public void setStartDate(Calendar startDate) {
		startDateLabel.setText(df.format(startDate.getTime()));
	}
	
	public void setEndDate(Calendar endDate) {
		endDateLabel.setText(df.format(endDate.getTime()));
	}
	
	public void setProjectLeaderLabel(String projectLeader) {
		projectLeaderLabel.setText(projectLeader);
	}
	
	public void setProjectLeaderText(String projectLeader) {
		projectLeaderText.setText(projectLeader);
	}

	public Label getProjectLeaderLabelComp() {
		return projectLeaderLabel;
	}
	
	public JTextField getProjectLeaderTextComp() {
		return projectLeaderText;
	}
	
	public JButton getChangeProjectLeaderComp() {
		return changeProjectLeader;
	}
	
	public JButton getSaveProjectLeaderComp() {
		return saveProjectLeader;
	}
	
	public String getProjectLeaderText() {
		return projectLeaderText.getText();
	}
	
	public void setDescriptionLabel(String description) {
		this.descriptionLabel.setText("<html><p style=\"width:100%;\">"+description+"</p></html>");		// for line breaks
	}
	
	public void setDescriptionText(String description) {
		descriptionText.setText(description);
	}

	public Label getDescriptionLabelComp() {
		return descriptionLabel;
	}
	
	public JScrollPane getDescriptionTextComp() {
		return descriptionTextScrollPane;
	}
	
	public JButton getChangeDescriptionComp() {
		return changeDescription;
	}
	
	public JButton getSaveDescriptionComp() {
		return saveDescription;
	}
	
	public String getDescriptionText() {
		return descriptionText.getText();
	}
	
	public void setTitleLabel(String title) {
		this.titleLabel.setText(title);
	}
	
	public void registerListener(ManageProjectController controller) {
		createTask.addActionListener(controller);
		deleteTask.addActionListener(controller);
		manageTask.addActionListener(controller);
		back.addActionListener(controller);
		changeProjectLeader.addActionListener(controller);
		saveProjectLeader.addActionListener(controller);
		changeDescription.addActionListener(controller);
		saveDescription.addActionListener(controller);
	}
	
	public int getSelectedIndex() {
		return taskList.getSelectedIndex();
	}
	
	public Task getSelected() {
		int index = taskList.getSelectedIndex();
		
		return tasks.get(index);
	}	
	
	public void updateList(Project project) {
		
		tasks.clear();
		
		for (Task task : project.getTasks())
			tasks.addElement(task);
		
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setErrorLabel(String error){
		errorLabel.setText(error);
	}

	public void disableAllButtons() {
		createTask.setEnabled(false);
		deleteTask.setEnabled(false);
		manageTask.setEnabled(false);
		changeProjectLeader.setEnabled(false);
		saveProjectLeader.setEnabled(false);
		changeDescription.setEnabled(false);
		saveDescription.setEnabled(false);
	}

	public void enableAllButtons() {
		createTask.setEnabled(true);
		deleteTask.setEnabled(true);
		manageTask.setEnabled(true);
		changeProjectLeader.setEnabled(true);
		saveProjectLeader.setEnabled(true);
		changeDescription.setEnabled(true);
		saveDescription.setEnabled(true);
	}

}
