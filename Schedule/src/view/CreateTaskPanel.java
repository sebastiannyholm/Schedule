package view;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import controller.CreateTaskController;
import model.Project;
import model.Schedule;

public class CreateTaskPanel extends JPanel {

	private Schedule schedule;
	private Project project;
	
	private JLabel titleLabel, errorLabel, taskLeaderLabel, taskNameLabel, startDateLabel, endDateLabel, budgetTimeLabel;
	private JButton createTask, back;
	private JTextField taskName, budgetTime;
	
	private UtilDateModel startDateModel, endDateModel;
	private JDatePanelImpl startDatePanel, endDatePanel;
	private JDatePickerImpl startDatePicker, endDatePicker;
	
	public CreateTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Create project");
		this.back = new JButton("Back");
		this.taskName = new JTextField("Project name");
		this.createTask = new JButton("Create project");
		this.errorLabel = new JLabel("");
		this.taskLeaderLabel = new JLabel("Project leader");
		this.taskNameLabel = new JLabel("Project name");
		this.startDateLabel = new JLabel("Project start date");
		this.endDateLabel = new JLabel("Project end date");
		this.budgetTimeLabel = new JLabel("Budget time");
		this.budgetTime = new JTextField();
		
		this.startDateModel = new UtilDateModel();
		this.endDateModel = new UtilDateModel();
		this.startDatePanel = new JDatePanelImpl(startDateModel);
	    this.endDatePanel = new JDatePanelImpl(endDateModel);
	    this.startDatePicker = new JDatePickerImpl(startDatePanel);
	    this.endDatePicker = new JDatePickerImpl(endDatePanel);
		
		titleLabel.setBounds(20, 20, 460, 40);
		
		taskNameLabel.setBounds(20, 80, 200, 40);
		taskName.setBounds(20, 120, 200, 40);
		
		budgetTimeLabel.setBounds(240, 80, 200, 40);
		budgetTime.setBounds(240, 120, 200, 40);
		
		startDateLabel.setBounds(20, 160, 200, 40);
		startDatePicker.setBounds(20, 200, 200, 40);
		
		endDateLabel.setBounds(240, 160, 200, 40);
		endDatePicker.setBounds(240,200,200,40);
		
		errorLabel.setBounds(20, 260, 400, 40);
		
		createTask.setBounds(20, 420, 150, 40);
		back.setBounds(380, 420, 100, 40);
		
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
