package view;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	
	private TitleLabel titleLabel;
	private SubTitleLabel findEmployeesLabel, employeeListTitleLabel, employeeAssistenceListTitleLabel, AssignmentListTitleLabel;
	private Label startDateLabel, hourInDayLabel, timeLabel, freeEmployeesLabel, timeTaskLabel, spentTimeTaskLabel, registeredSpentTimeTaskLabel;
	private ErrorLabel errorLabel;
	private JButton createAsisgnment, findFreeEmployees, back;
	private JTextField hourInDayText, timeText;
	
	private JList employeeList, employeeAssistenceList, findEmployeesList, assignmentsList;
	private JScrollPane scrollPane, scrollPaneAssistence, scrollPaneFindEmployees, scrollPaneAssignments;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> employeesAssistence = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> findEmployees = new DefaultListModel<Employee>();
	private DefaultListModel<Assignment> assignments = new DefaultListModel<Assignment>();
	
	private UtilDateModel findEmployeesDateModel;
	private JDatePanelImpl findEmployeesDatePanel;
	private JDatePickerImpl findEmployeesDatePicker;
	
	public ManageTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Manage task");
		this.back = new JButton("Back");
		this.employeeListTitleLabel = new SubTitleLabel("All employees on task");
		this.employeeList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(employeeList);
		this.createAsisgnment = new JButton("Create Assignment");
		this.findEmployeesLabel = new SubTitleLabel("Find employees for the assignment");
		this.findFreeEmployees = new JButton("Find employees");
		this.hourInDayLabel = new Label("Hour of day");
		this.hourInDayText = new JTextField();
		this.timeLabel = new Label("Time for assignment (hours)");
		this.timeText = new JTextField();
		this.startDateLabel = new Label("Startdate");
		this.errorLabel = new ErrorLabel("");
		
		this.employeeAssistenceListTitleLabel = new SubTitleLabel("All assistance on task");
		this.employeeAssistenceList = new JList(employeesAssistence);
		this.scrollPaneAssistence = new JScrollPane();
		this.scrollPaneAssistence.setViewportView(employeeAssistenceList);
		
		this.freeEmployeesLabel = new Label("Free employees");
		this.findEmployeesList = new JList(findEmployees);
		this.scrollPaneFindEmployees = new JScrollPane();
		this.scrollPaneFindEmployees.setViewportView(findEmployeesList);
		
		this.AssignmentListTitleLabel = new SubTitleLabel("Assignments in this task");
		this.assignmentsList = new JList(assignments);
		this.scrollPaneAssignments = new JScrollPane();
		this.scrollPaneAssignments.setViewportView(assignmentsList);
		
		this.findEmployeesDateModel = new UtilDateModel();
	    this.findEmployeesDatePanel = new JDatePanelImpl(findEmployeesDateModel);
	    this.findEmployeesDatePicker = new JDatePickerImpl(findEmployeesDatePanel);
	    
	    this.timeTaskLabel = new Label(""); 
	    this.spentTimeTaskLabel = new Label("");
	    this.registeredSpentTimeTaskLabel = new Label("");
	    
	    titleLabel.setLocation(20, 20);
		
	    AssignmentListTitleLabel.setBounds(20, 80, 200, 20);
		scrollPaneAssignments.setBounds(20, 120, 200, 440);
	    
	    employeeListTitleLabel.setBounds(240, 80, 200, 20);
		scrollPane.setBounds(240, 120, 200, 250);
		
		employeeAssistenceListTitleLabel.setBounds(240, 380, 200, 20);
		scrollPaneAssistence.setBounds(240, 410, 200, 150);
		
		findEmployeesLabel.setBounds(470, 80, 450, 20);
		
		startDateLabel.setBounds(470, 110, 200, 20);
		findEmployeesDatePicker.setBounds(470,130,200,40);
		
		hourInDayLabel.setBounds(470, 190, 200, 20);
		hourInDayText.setBounds(470,210, 200, 40);
		
		timeLabel.setBounds(470, 270, 200, 20);
		timeText.setBounds(470, 290, 200, 40);
		
		errorLabel.setBounds(470,350,200,20);
		
		findFreeEmployees.setBounds(470, 390, 120, 40);
		
		freeEmployeesLabel.setBounds(690, 110, 200, 20);
		scrollPaneFindEmployees.setBounds(690, 130, 200, 240);
		
		createAsisgnment.setBounds(690, 390, 150, 40);
		
		timeTaskLabel.setBounds(470, 450, 300, 20);
		spentTimeTaskLabel.setBounds(470, 480, 300, 20);
		registeredSpentTimeTaskLabel.setBounds(470, 510, 300, 20);
		
		back.setBounds(820,520,120,40);
		
		this.setLayout(null);
		
		this.add(findEmployeesDatePicker);
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(scrollPane);
		this.add(employeeAssistenceListTitleLabel);
		this.add(scrollPaneAssistence);
		this.add(scrollPaneAssignments);
		this.add(AssignmentListTitleLabel);
		this.add(createAsisgnment);
		this.add(findEmployeesLabel);
		this.add(startDateLabel);
		this.add(findEmployeesDatePicker);
		this.add(freeEmployeesLabel);
		this.add(scrollPaneFindEmployees);
		this.add(hourInDayLabel);
		this.add(hourInDayText);
		this.add(timeLabel);
		this.add(timeText);
		this.add(findFreeEmployees);
		this.add(errorLabel);
		this.add(back);
		this.add(timeTaskLabel);
		this.add(spentTimeTaskLabel);
		this.add(registeredSpentTimeTaskLabel);
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
		findFreeEmployees.addActionListener(controller);
	}

	public String getTimeText() {
		return timeText.getText();
	}
	
	public String getHourInDayText() {
		return hourInDayText.getText();
	}
	
	public Date getStartDate() {
		return (Date) findEmployeesDatePicker.getModel().getValue();
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
	
}
