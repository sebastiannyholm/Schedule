package view;

import java.awt.Color;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.TitleLabel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import controller.CreateTaskController;
import model.Project;
import model.Schedule;

public class CreateTaskPanel extends JPanel {

	private Schedule schedule;
	private Project project;
	
	private TitleLabel titleLabel;
	private Label taskNameLabel, startDateLabel, endDateLabel, budgetTimeLabel, taskDescriptionLabel;
	private ErrorLabel errorLabel;
	private JButton createTask, back;
	private JTextField taskName, budgetTime;
	private JTextArea taskDescription;
	private JScrollPane descriptionScrollPane;
	
	private UtilDateModel startDateModel, endDateModel;
	private JDatePanelImpl startDatePanel, endDatePanel;
	private JDatePickerImpl startDatePicker, endDatePicker;
	
	public CreateTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Create task");
		this.back = new JButton("Back");
		this.taskName = new JTextField("Task name");
		this.createTask = new JButton("Create task");
		this.errorLabel = new ErrorLabel("");
		this.taskNameLabel = new Label("Task name");
		this.startDateLabel = new Label("Task start date");
		this.endDateLabel = new Label("Task end date");
		this.budgetTimeLabel = new Label("Budget time (hours)");
		this.budgetTime = new JTextField();
		
		this.startDateModel = new UtilDateModel();
		this.endDateModel = new UtilDateModel();
		this.startDatePanel = new JDatePanelImpl(startDateModel);
	    this.endDatePanel = new JDatePanelImpl(endDateModel);
	    this.startDatePicker = new JDatePickerImpl(startDatePanel);
	    this.endDatePicker = new JDatePickerImpl(endDatePanel);
	    
	    this.taskDescriptionLabel = new Label("Project description");
	    this.taskDescription = new JTextArea();
	    this.descriptionScrollPane = new JScrollPane(taskDescription);
	    
	    titleLabel.setLocation(20, 20);
		
		taskNameLabel.setBounds(20, 80, 200, 20);
		taskName.setBounds(20, 100, 200, 40);
		
		budgetTimeLabel.setBounds(240, 80, 200, 20);
		budgetTime.setBounds(240, 100, 200, 40);
		
		startDateLabel.setBounds(20, 160, 200, 20);
		startDatePicker.setBounds(20, 180, 200, 40);
		
		endDateLabel.setBounds(240, 160, 200, 20);
		endDatePicker.setBounds(240,180,200,40);
		
		taskDescriptionLabel.setBounds(20, 240, 200, 20);
		descriptionScrollPane.setBounds(20, 260, 200, 200);
		
		errorLabel.setBounds(20, 480, 400, 20);
		
		createTask.setBounds(20, 520, 120, 40);
		
		back.setBounds(820,520,120,40);
		
		this.setLayout(null);
		
		this.add(taskNameLabel);
		this.add(startDateLabel);
		this.add(budgetTimeLabel);
		this.add(budgetTime);
		this.add(endDateLabel);
		this.add(errorLabel);
		this.add(back);
		this.add(titleLabel);
		this.add(startDatePicker);
		this.add(endDatePicker);
		this.add(taskName);
		this.add(createTask);
		this.add(taskDescriptionLabel);
		this.add(descriptionScrollPane);
	}

	public String getTaskDescription() {
		return taskDescription.getText();
	}
	
	public void registerListener(CreateTaskController controller) {
		back.addActionListener(controller);
		createTask.addActionListener(controller);
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}
	
	public Date getStartDate() {
        return (Date) startDatePicker.getModel().getValue();
	}
	
	public Date getEndDate() {
        return (Date) endDatePicker.getModel().getValue();
	}
	
	public String getTaskName() {
		return taskName.getText();
	}
	
	public String getbudgetTime() {
		return budgetTime.getText();
	}

	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
	
}
