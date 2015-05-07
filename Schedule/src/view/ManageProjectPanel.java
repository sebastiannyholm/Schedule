package view;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	
	private JLabel titleLabel, taskListTitleLabel, errorLabel;
	private JButton createTask, deleteTask, manageTask, back;
	private JList taskList;
	private JScrollPane scrollPane;
	
	private DefaultListModel<Task> tasks = new DefaultListModel<Task>();
	
	public ManageProjectPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("My tasks");
		this.taskListTitleLabel = new JLabel("All tasks");
		this.taskList = new JList(tasks);
		this.createTask = new JButton("Create task");
		this.deleteTask = new JButton("Delete task");
		this.manageTask = new JButton("Manage task");
		this.back = new JButton("Back");
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(taskList);
		this.errorLabel = new JLabel("");
		
		this.setLayout(null);
		
		titleLabel.setBounds(20, 20, 460, 40);
		taskListTitleLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 300);
		
		createTask.setBounds(250, 120, 120, 40);
		deleteTask.setBounds(250, 180, 120, 40);
		manageTask.setBounds(250, 240, 120, 40);
		back.setBounds(250, 300, 120, 40);
		
		errorLabel.setBounds(20, 430, 300, 40);
		errorLabel.setForeground(Color.RED);
		
		this.add(titleLabel);
		this.add(taskListTitleLabel);
		this.add(scrollPane);
		this.add(createTask);
		this.add(deleteTask);
		this.add(manageTask);
		this.add(back);
		this.add(errorLabel);
		
	}

	public void registerListener(ManageProjectController controller) {
		createTask.addActionListener(controller);
		deleteTask.addActionListener(controller);
		manageTask.addActionListener(controller);
		back.addActionListener(controller);
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
		
		for (Task task : schedule.getUser().getProjects().get(schedule.getUser().getProjects().indexOf(project)).getTasks())
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
}
