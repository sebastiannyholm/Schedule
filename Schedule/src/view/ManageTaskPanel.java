package view;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import controller.ManageTaskController;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Assignment;

public class ManageTaskPanel extends JPanel {

	private Schedule schedule;
	private Task task;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	private TitleLabel titleLabel;
	private SubTitleLabel employeeListTitleLabel, employeeAssistenceListTitleLabel, AssignmentListTitleLabel, taskInformation;
	private Label startDateLabel, startDateLabelLabel, endDateLabel, endDateLabelLabel, timeTaskLabel, spentTimeTaskLabel, registeredSpentTimeTaskLabel, descriptionLabel, descriptionLabelLabel;
	private ErrorLabel errorLabel;
	private JTextArea descriptionText;
	private JButton createAsisgnment, back, changeDescription, saveDescription;
	private JTextField hourInDayText, timeText;
	
	private JList employeeList, employeeAssistenceList, findEmployeesList, assignmentsList;
	private JScrollPane scrollPane, scrollPaneAssistence, scrollPaneAssignments, descriptionTextScrollPane;;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> employeesAssistence = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> findEmployees = new DefaultListModel<Employee>();
	private DefaultListModel<Assignment> assignments = new DefaultListModel<Assignment>();
	
	public ManageTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Manage task");
		this.back = new JButton("Back");
		this.employeeListTitleLabel = new SubTitleLabel("All employees on task");
		this.employeeList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(employeeList);
		this.createAsisgnment = new JButton("Create Assignment");
//		this.findEmployeesLabel = new SubTitleLabel("Find employees for the assignment");
//		this.findFreeEmployees = new JButton("Find employees");
//		this.hourInDayLabel = new Label("Hour of day");
//		this.hourInDayText = new JTextField();
//		this.timeLabel = new Label("Time for assignment (hours)");
//		this.timeText = new JTextField();
//		this.startDateLabel = new Label("Startdate");
		this.errorLabel = new ErrorLabel("");
		this.taskInformation = new SubTitleLabel("Information about the task");
		
		this.employeeAssistenceListTitleLabel = new SubTitleLabel("All assistance on task");
		this.employeeAssistenceList = new JList(employeesAssistence);
		this.scrollPaneAssistence = new JScrollPane();
		this.scrollPaneAssistence.setViewportView(employeeAssistenceList);
		
//		this.freeEmployeesLabel = new Label("Free employees");
//		this.findEmployeesList = new JList(findEmployees);
//		this.scrollPaneFindEmployees = new JScrollPane();
//		this.scrollPaneFindEmployees.setViewportView(findEmployeesList);
		
		this.AssignmentListTitleLabel = new SubTitleLabel("Assignments in this task");
		this.assignmentsList = new JList(assignments);
		this.scrollPaneAssignments = new JScrollPane();
		this.scrollPaneAssignments.setViewportView(assignmentsList);
		
//		this.findEmployeesDateModel = new UtilDateModel();
//	    this.findEmployeesDatePanel = new JDatePanelImpl(findEmployeesDateModel);
//	    this.findEmployeesDatePicker = new JDatePickerImpl(findEmployeesDatePanel);
	    
	    this.timeTaskLabel = new Label(""); 
	    this.spentTimeTaskLabel = new Label("");
	    this.registeredSpentTimeTaskLabel = new Label("");
	    
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
		
	    titleLabel.setLocation(20, 20);
	    taskInformation.setBounds(540, 80, 400, 20);
	    AssignmentListTitleLabel.setBounds(20, 80, 200, 20);
		scrollPaneAssignments.setBounds(20, 120, 280, 380);
		
	    employeeListTitleLabel.setBounds(320, 80, 200, 20);
		scrollPane.setBounds(320, 120, 200, 200);
		
		employeeAssistenceListTitleLabel.setBounds(320, 330, 200, 20);
		scrollPaneAssistence.setBounds(320, 360, 200, 140);
		
		descriptionLabelLabel.setBounds(540, 200, 220, 20);
		descriptionLabel.setBounds(540, 220, 220, 100);
		descriptionTextScrollPane.setBounds(540, 220, 220, 100);
		changeDescription.setBounds(780, 220, 160, 40);
		saveDescription.setBounds(780, 220, 160, 40);
		
		errorLabel.setBounds(470,350,200,20);
		
		createAsisgnment.setBounds(20, 520, 150, 40);
		
		timeTaskLabel.setBounds(540, 120, 300, 20);
		spentTimeTaskLabel.setBounds(540, 140, 300, 20);
		registeredSpentTimeTaskLabel.setBounds(540, 160, 300, 20);
		
		back.setBounds(820,520,120,40);
		
		startDateLabelLabel.setBounds(540, 340, 100, 20);
		startDateLabel.setBounds(540, 360, 100, 40);
		
		endDateLabelLabel.setBounds(660, 340, 100, 20);
		endDateLabel.setBounds(660, 360, 100, 40);
		
		this.setLayout(null);
		
		this.add(taskInformation);
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(scrollPane);
		this.add(employeeAssistenceListTitleLabel);
		this.add(scrollPaneAssistence);
		this.add(scrollPaneAssignments);
		this.add(AssignmentListTitleLabel);
		this.add(createAsisgnment);
//		this.add(findEmployeesLabel);
//		this.add(startDateLabel);
//		this.add(findEmployeesDatePicker);
//		this.add(freeEmployeesLabel);
//		this.add(scrollPaneFindEmployees);
//		this.add(hourInDayLabel);
//		this.add(hourInDayText);
//		this.add(timeLabel);
//		this.add(timeText);
//		this.add(findFreeEmployees);
		this.add(errorLabel);
		this.add(back);
		this.add(timeTaskLabel);
		this.add(spentTimeTaskLabel);
		this.add(registeredSpentTimeTaskLabel);
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

	public void setTimeTaskTLabel() {
		String time = Integer.toString(task.getBudgetedTime());
		timeTaskLabel.setText("The given time for the task: " + time + " hour(s)");
	}
	
	public void setSpentTimeTaskTLabel() {
		int hours = task.getTimeSpent() / 60;
		int minutes = task.getTimeSpent() % 60;
		String time = hours + " hour(s) " + minutes + " min";
		spentTimeTaskLabel.setText("The total time on assignments: " + time);
	}
	
	public void setRegisteredSpentTimeTaskTLabel() {
		int hours = task.getRegisteredTime() / 60 / 60;
		int minutes = (task.getRegisteredTime() / 60) % 60;
		String time = hours + " hour(s) " + minutes + " min";
		registeredSpentTimeTaskLabel.setText("Registered time: " + time);
	}
	
	public void removeAssistenceList() {
		this.remove(employeeAssistenceListTitleLabel);
		this.remove(scrollPaneAssistence);
	}
	
	public void addAssistenceList() {
		this.add(employeeAssistenceListTitleLabel);
		this.add(scrollPaneAssistence);
	}
	
	public void registerListener(ManageTaskController controller) {
		back.addActionListener(controller);
		createAsisgnment.addActionListener(controller);
		changeDescription.addActionListener(controller);
		saveDescription.addActionListener(controller);
//		findFreeEmployees.addActionListener(controller);
	}

	public String getTimeText() {
		return timeText.getText();
	}
	
	public String getHourInDayText() {
		return hourInDayText.getText();
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}

	public void updateList() {
		
		employees.clear();
		
		for (Employee employee : task.getEmployees())
			employees.addElement(employee);
		
	}
	
	public void setTitleLabel(String title) {
		this.titleLabel.setText(title);
	}
	
	public void updateAssistenceList() {
		
		employeesAssistence.clear();
		
		for (Employee employee : task.getEmployeesAsAssistance())
			employeesAssistence.addElement(employee);
		
	}
	
	public void updateFindEmployeesList(List<Employee> list) {
		
		findEmployees.clear();
		
		for (Employee employee : list)
			findEmployees.addElement(employee);
		
	}

	public void updateAssignmentsList() {
		
		assignments.clear();
		
		List<Employee> list = new LinkedList<Employee>();
		for (Employee employee : task.getEmployees())
			list.add(employee);
		for (Employee employee : task.getEmployeesAsAssistance())
			if (!list.contains(employee))
				list.add(employee);
		
		for (Employee employee : list)
			for (Assignment assignment : employee.getTasksAndTime().get(task))
				assignments.addElement(assignment);
		
	}
	
	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
	
	public int getSelectedIndex() {
		return findEmployeesList.getSelectedIndex();
	}
	
	public Employee getSelected() {
		int index = findEmployeesList.getSelectedIndex();
		
		return findEmployees.get(index);
	}
	
	public void setDescriptionLabel(String description) {
		this.descriptionLabel.setText("<html><p style=\"width:100%;\">"+description+"</p></html>");
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
}
