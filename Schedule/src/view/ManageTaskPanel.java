package view;

import java.awt.Color;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import controller.ManageTaskController;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Timer;

public class ManageTaskPanel extends JPanel {

	private Schedule schedule;
	private Task task;
	
	private JLabel titleLabel, employeeListTitleLabel, employeeAssistenceListTitleLabel, freeEmployeesLabel, findEmployeesLabel, startDateLabel, hourInDayLabel, timeLabel, errorLabel;
	private JButton createSubTask, findEmployeesButton, back;
	private JTextField hourInDayText, timeText;
	
	private JList employeeList, employeeAssistenceList, findEmployeesList;
	private JScrollPane scrollPane, scrollPaneAssistence, scrollPaneFindEmployees;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> employeesAssistence = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> findEmployees = new DefaultListModel<Employee>();
	
	private UtilDateModel findEmployeesDateModel;
	private JDatePanelImpl findEmployeesDatePanel;
	private JDatePickerImpl findEmployeesDatePicker;
	
	public ManageTaskPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Manage task");
		this.back = new JButton("Back");
		this.employeeListTitleLabel = new JLabel("All employees on task");
		this.employeeList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(employeeList);
		this.createSubTask = new JButton("Create sub task");
		this.findEmployeesLabel = new JLabel("Find employees for a task");
		this.findEmployeesButton = new JButton("Find employees");
		this.hourInDayLabel = new JLabel("At what time");
		this.hourInDayText = new JTextField();
		this.timeLabel = new JLabel("Time for subtask");
		this.timeText = new JTextField();
		this.startDateLabel = new JLabel("Startdate");
		this.errorLabel = new JLabel("");
		
		this.employeeAssistenceListTitleLabel = new JLabel("All assistance on task");
		this.employeeAssistenceList = new JList(employeesAssistence);
		this.scrollPaneAssistence = new JScrollPane();
		this.scrollPaneAssistence.setViewportView(employeeAssistenceList);
		
		this.freeEmployeesLabel = new JLabel("Free employees");
		this.findEmployeesList = new JList(findEmployees);
		this.scrollPaneFindEmployees = new JScrollPane();
		this.scrollPaneFindEmployees.setViewportView(findEmployeesList);
		
		this.findEmployeesDateModel = new UtilDateModel();
	    this.findEmployeesDatePanel = new JDatePanelImpl(findEmployeesDateModel);
	    this.findEmployeesDatePicker = new JDatePickerImpl(findEmployeesDatePanel);
		
		titleLabel.setBounds(20, 20, 460, 40);
		employeeListTitleLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 200);
		employeeAssistenceListTitleLabel.setBounds(20, 320, 200, 40);
		scrollPaneAssistence.setBounds(20, 360, 200, 100);
		back.setBounds(370, 430, 120, 40);
		createSubTask.setBounds(370, 380, 120, 40);
		findEmployeesLabel.setBounds(250, 40, 200, 40);
		startDateLabel.setBounds(250, 80, 200, 30);
		findEmployeesDatePicker.setBounds(250,110,200,40);
		freeEmployeesLabel.setBounds(380, 150, 100, 30);
		scrollPaneFindEmployees.setBounds(380, 180, 100, 100);
		hourInDayLabel.setBounds(250, 150, 120, 30);
		hourInDayText.setBounds(250,180, 120, 40);
		timeLabel.setBounds(250, 220, 120, 30);
		timeText.setBounds(250, 250, 120, 40);
		findEmployeesButton.setBounds(250, 300, 120, 40);
		errorLabel.setBounds(250, 340, 200, 30);
		errorLabel.setForeground(Color.RED);
		
		this.setLayout(null);
		
		this.add(findEmployeesDatePicker);
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(scrollPane);
		this.add(employeeAssistenceListTitleLabel);
		this.add(scrollPaneAssistence);
		this.add(createSubTask);
		this.add(findEmployeesLabel);
		this.add(startDateLabel);
		this.add(findEmployeesDatePicker);
		this.add(freeEmployeesLabel);
		this.add(scrollPaneFindEmployees);
		this.add(hourInDayLabel);
		this.add(hourInDayText);
		this.add(timeLabel);
		this.add(timeText);
		this.add(findEmployeesButton);
		this.add(errorLabel);
		this.add(back);
	}

	public void registerListener(ManageTaskController controller) {
		back.addActionListener(controller);
		createSubTask.addActionListener(controller);
		findEmployeesButton.addActionListener(controller);
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
