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
import controller.WorkingController;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Timer;

public class WorkingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	private Task task;
	private Timer timer;
	
	private JLabel titleLabel, timeSpentLabel, timeSpentTitleLabel, changeTimeSpentLabel, errorLabel, freeEmployeesLabel, findEmployeesLabel, startDateLabel, hourInDayLabel, timeLabel, workedToMuch;
	private JButton back, startWork, stopWork, changeTimeSpent, addAssistence, findEmployeesButton;
	private JTextField changeTimeSpentTextField, hourInDayText, timeText;
	
	private JList findEmployeesList;
	private JScrollPane scrollPaneFindEmployees;
	
	private DefaultListModel<Employee> findEmployees = new DefaultListModel<Employee>();
	
	private UtilDateModel findEmployeesDateModel;
	private JDatePanelImpl findEmployeesDatePanel;
	private JDatePickerImpl findEmployeesDatePicker;
	
	public WorkingPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("");
		this.timeSpentLabel = new JLabel("0");
		this.timeSpentTitleLabel = new JLabel("Time spent on this task");
		this.startWork = new JButton("Start timer");
		this.stopWork = new JButton("Stop timer");
		this.changeTimeSpentLabel = new JLabel("Change time worked on this task");
		this.changeTimeSpent = new JButton("Change time");
		this.changeTimeSpentTextField = new JTextField();
		this.errorLabel = new JLabel("");
		this.back = new JButton("Back");
		this.addAssistence = new JButton("Add assistence");
		this.findEmployeesLabel = new JLabel("Find employees for a task");
		this.findEmployeesButton = new JButton("Find employees");
		this.hourInDayLabel = new JLabel("At what time");
		this.hourInDayText = new JTextField();
		this.timeLabel = new JLabel("Time for subtask");
		this.timeText = new JTextField();
		this.startDateLabel = new JLabel("Startdate");
		this.workedToMuch = new JLabel("");
		
		this.freeEmployeesLabel = new JLabel("Free employees");
		this.findEmployeesList = new JList(findEmployees);
		this.scrollPaneFindEmployees = new JScrollPane();
		this.scrollPaneFindEmployees.setViewportView(findEmployeesList);
		
		this.findEmployeesDateModel = new UtilDateModel();
	    this.findEmployeesDatePanel = new JDatePanelImpl(findEmployeesDateModel);
	    this.findEmployeesDatePicker = new JDatePickerImpl(findEmployeesDatePanel);
		
		this.setLayout(null);
		
		titleLabel.setBounds(20, 20, 460, 40);
		timeSpentTitleLabel.setBounds(20,80,200,40);
		timeSpentLabel.setBounds(20,120,200,40);
		workedToMuch.setBounds(20,160,200,40);
		changeTimeSpentLabel.setBounds(20, 200, 200, 40);
		changeTimeSpentTextField.setBounds(20, 240, 200, 40);
		changeTimeSpent.setBounds(20, 280, 120, 40);
		startWork.setBounds(20, 360, 120, 40);
		stopWork.setBounds(20, 420, 120, 40);
		errorLabel.setBounds(20,315,200,40);
		back.setBounds(370, 430, 120, 40);
		
		addAssistence.setBounds(370, 380, 120, 40);
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
		
		errorLabel.setForeground(Color.RED);
		workedToMuch.setForeground(Color.RED);
		
		this.setLayout(null);
		
		this.add(titleLabel);
		this.add(timeSpentTitleLabel);
		this.add(timeSpentLabel);
		this.add(startWork);
		this.add(stopWork);
		this.add(changeTimeSpentLabel);
		this.add(changeTimeSpentTextField);
		this.add(changeTimeSpent);
		this.add(errorLabel);
		this.add(back);
		this.add(workedToMuch);
		
		
		stopWork.setEnabled(false);
	}

	public void setAddAssistence() {
		this.add(addAssistence);
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
	}
	
	public void registerListener(WorkingController controller) {
		back.addActionListener(controller);
		startWork.addActionListener(controller);
		stopWork.addActionListener(controller);
		changeTimeSpent.addActionListener(controller);
		addAssistence.addActionListener(controller);
		findEmployeesButton.addActionListener(controller);
	}
	
	public String getChangeTimeSpent() {
		return changeTimeSpentTextField.getText();
	}
	
	public void setTitleLabel(String title) {
		titleLabel.setText(title);
	}
	
	public void setTimeSpentLabel(String timeSpent) {
		this.timeSpentLabel.setText(timeSpent + " minutes");
	}
	
	public String getTimeSpentLabel() {
		return timeSpentLabel.getText();
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void setStartWork(boolean b) {
		startWork.setEnabled(b);
	}

	public void setStopWork(boolean b) {
		stopWork.setEnabled(b);
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
	
	public void updateFindEmployeesList(List<Employee> list) {
		
		findEmployees.clear();
		
		for (Employee employee : list)
			findEmployees.addElement(employee);
		
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
	
	public void setWorkedToMuch(String message) {
		workedToMuch.setText(message);
	}
	
	
}
