package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.SubTitleLabel;
import jcomponents.TitleLabel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import controller.WorkingController;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Assignment;

public class WorkingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	private Task task;
	private Assignment assignment;
	
	private TitleLabel titleLabel;
	private SubTitleLabel descriptionTaskTitle, timeSpentLabel, timeSpentTitleLabel, descriptionAssignmentTitle, findEmployeesLabel;
	private Label changeTimeSpentLabel, freeEmployeesLabel, startDateLabel, hourInDayLabel, timeLabel, descriptionTask, descriptionAssignment, seperator1, seperator2;
	private ErrorLabel errorLabel, errorLabelTime;
	private JButton back, startWork, stopWork, saveTimeSpent, changeTimeSpent, addAssistence, findFreeEmployees;
	private JTextField changeHoursSpentTextField, changeMinutesSpentTextField, changeSecondsSpentTextField, hourInDayText, timeText;
	
	private JList findEmployeesList;
	private JScrollPane scrollPaneFindEmployees;
	
	private DefaultListModel<Employee> findEmployees = new DefaultListModel<Employee>();
	
	private UtilDateModel findEmployeesDateModel;
	private JDatePanelImpl findEmployeesDatePanel;
	private JDatePickerImpl findEmployeesDatePicker;
	
	public WorkingPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("");
		this.timeSpentLabel = new SubTitleLabel("00:00:00");
		this.timeSpentTitleLabel = new SubTitleLabel("Time spent on this assignment");
		this.startWork = new JButton("Start timer");
		this.stopWork = new JButton("Stop timer");
//		this.changeTimeSpentLabel = new Label("Change time worked on this task");
		this.changeTimeSpent = new JButton("Change time");
		this.saveTimeSpent = new JButton("Save time");
		this.changeHoursSpentTextField = new JTextField();
		this.changeMinutesSpentTextField = new JTextField();
		this.changeSecondsSpentTextField = new JTextField();
		this.errorLabel = new ErrorLabel("");
		this.errorLabelTime = new ErrorLabel("");
		this.back = new JButton("Back");
		this.addAssistence = new JButton("Add assistence");
		this.findEmployeesLabel = new SubTitleLabel("Find an employee to help you");
		this.findFreeEmployees = new JButton("Find employees");
		this.hourInDayLabel = new Label("Hour of day");
		this.hourInDayText = new JTextField();
		this.timeLabel = new Label("Time for assignment (hours)");
		this.timeText = new JTextField();
		this.startDateLabel = new Label("Startdate");
		this.descriptionTaskTitle = new SubTitleLabel("Description for task:");
		this.descriptionTask = new Label("");
		this.descriptionAssignmentTitle = new SubTitleLabel("Description for assignment:");
		this.descriptionAssignment = new Label("");
		this.seperator1 = new Label(":");
		this.seperator2 = new Label(":");
		this.changeTimeSpentLabel = new Label("");
		this.freeEmployeesLabel = new Label("Free employees");
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
//		workedToMuch.setBounds(20,160,200,40);
		changeTimeSpentLabel.setBounds(20, 200, 200, 40);
//		changeTimeSpentTextField.setBounds(20, 240, 200, 40);
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
//		findEmployeesButton.setBounds(250, 300, 120, 40);
		
		errorLabel.setForeground(Color.RED);
//		workedToMuch.setForeground(Color.RED);
		titleLabel.setLocation(20, 20);
	
//		changeTimeSpentLabel.setBounds(20, 200, 200, 40);
		
		descriptionTaskTitle.setBounds(20, 80, 400, 20);
		descriptionTask.setBounds(20, 100, 400, 60);
		
		descriptionAssignmentTitle.setBounds(20, 180, 400, 20);
		descriptionAssignment.setBounds(20, 200, 400, 60);
		
		timeSpentTitleLabel.setBounds(20,380,300,20);
		timeSpentLabel.setBounds(20,400,200,40);
		
		changeHoursSpentTextField.setBounds(20, 405, 68, 30);
		changeHoursSpentTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		seperator1.setBounds(88, 405, 10, 30);
		seperator1.setHorizontalAlignment(SwingConstants.CENTER);
		seperator1.setVerticalAlignment(SwingConstants.CENTER);
		changeMinutesSpentTextField.setBounds(98, 405, 34, 30);
		seperator2.setBounds(132, 405, 10, 30);
		seperator2.setHorizontalAlignment(SwingConstants.CENTER);
		seperator2.setVerticalAlignment(SwingConstants.CENTER);
		changeSecondsSpentTextField.setBounds(142, 405, 34, 30);
		
		errorLabelTime.setBounds(20,430,300,30);
		
		startWork.setBounds(20, 460, 120, 40);
		stopWork.setBounds(160, 460, 120, 40);
		changeTimeSpent.setBounds(20, 520, 120, 40);
		saveTimeSpent.setBounds(20, 520, 120, 40);
		
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
		
		addAssistence.setBounds(690, 390, 120, 40);
		
		back.setBounds(820,520,120,40);
		
		this.setLayout(null);
		
		this.add(titleLabel);
		this.add(timeSpentTitleLabel);
		this.add(timeSpentLabel);
		this.add(startWork);
		this.add(stopWork);
//		this.add(changeTimeSpentLabel);
		this.add(changeTimeSpent);
		this.add(errorLabel);
		this.add(errorLabelTime);
		this.add(back);

		this.add(descriptionTask);
		this.add(descriptionTaskTitle);
		this.add(descriptionAssignment);
		this.add(descriptionAssignmentTitle);
		
		stopWork.setEnabled(false);
	}

	public void addChangeTime() {
		remove(timeSpentLabel);
		remove(changeTimeSpent);
		add(changeHoursSpentTextField);
		add(seperator1);
		add(changeMinutesSpentTextField);
		add(seperator2);
		add(changeSecondsSpentTextField);
		add(saveTimeSpent);
	}
	
	public void removeChangeTime() {
		remove(saveTimeSpent);
		remove(changeHoursSpentTextField);
		remove(seperator1);
		remove(changeMinutesSpentTextField);
		remove(seperator2);
		remove(changeSecondsSpentTextField);
		add(timeSpentLabel);
		add(changeTimeSpent);
	}
	
	public void setDescriptionTask(String description) {
		this.descriptionTask.setText("<html><p style=\"width:100%\">"+description+"</p></html>");
	}
	
	public void setDescriptionAssignment(String description) {
		this.descriptionAssignment.setText("<html><p style=\"width:100%\">"+description+"</p></html>");
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
		this.add(findFreeEmployees);
	}
	
	public void registerListener(WorkingController controller) {
		back.addActionListener(controller);
		startWork.addActionListener(controller);
		stopWork.addActionListener(controller);
		changeTimeSpent.addActionListener(controller);
		saveTimeSpent.addActionListener(controller);
		addAssistence.addActionListener(controller);
		findFreeEmployees.addActionListener(controller);
	}
	
	public String getChangeTimeSpent() {
		return changeHoursSpentTextField.getText();
	}
	
	public void setTitleLabel(String title) {
		titleLabel.setText(title);
	}
	
	public void setTimeSpentLabelText(String timeSpent) {
		this.timeSpentLabel.setText(timeSpent);
	}
	
	public String getTimeSpentLabelText() {
		return timeSpentLabel.getText();
	}
	
	public void setTimeSpent() {
		setTimeSpentLabelText(assignment.getTimeSpentString());
		setChangeHoursSpentText(assignment.getHoursSpentString());
		setChangeMinutesSpentText(assignment.getMinutesSpentString());
		setChangeSecondsSpentText(assignment.getSecondsSpentString());
	}
	
	public SubTitleLabel getTimeSpentLabel() {
		return timeSpentLabel;
	}
	
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	public Assignment getAssignment() {
		return assignment;
	}
	
	public void setStartWork(boolean b) {
		startWork.setEnabled(b);
	}

	public void setStopWork(boolean b) {
		stopWork.setEnabled(b);
	}
	
	public void setChangeTimeSpent(boolean b) {
		changeTimeSpent.setEnabled(b);
	}
	
	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
	
	public void setErrorLabelTime(String error) {
		errorLabelTime.setText(error);
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

//	public void setTimeInCorrectFormat() {
//		
//		String hoursString = "";
//		String minutesString = "";
//		String secondsString = "";
//		
//		int hours = assignment.getHourSpent(), minutes = assignment.getMinutesSpent(), seconds = assignment.getSecondsSpent();
//		
//		try {
//			hours = Integer.parseInt(hoursString);
//			minutes = Integer.parseInt(minutesString);
//			seconds = Integer.parseInt(secondsString);
//		} catch(NumberFormatException error) {
//			
//		}
//		
//		if (seconds == 60) {
//			seconds = 0;
//			minutes++;
//			if (minutes == 60) {
//				minutes = 0;
//				hours++;
//			}
//		}
//		
//		if (hours < 10)
//			hoursString = "0" + hours;
//		else 
//			hoursString = Integer.toString(hours);
//		 
//		if (minutes < 10)
//			minutesString = "0" + minutes;
//		else 
//			minutesString = Integer.toString(minutes);
//		
//		if (seconds < 10)
//			secondsString = "0" + seconds;
//		else 
//			secondsString = Integer.toString(seconds);
//		
//		setChangeHoursSpentText(hoursString);
//		setChangeMinutesSpentText(minutesString);
//		setChangeSecondsSpentText(secondsString);
//		timeSpentLabel.setText(hoursString + ":" + minutesString + ":" + secondsString);
//	}
	
	public String getChangeHoursSpentText() {
		return changeHoursSpentTextField.getText();
	}
	
	public void setChangeHoursSpentText(String hour) {
		changeHoursSpentTextField.setText(hour);
	}
	
	public JTextField getChangeHoursSpent() {
		return changeHoursSpentTextField;
	}
	
	public String getChangeMinutesSpentText() {
		return changeMinutesSpentTextField.getText();
	}
	
	public void setChangeMinutesSpentText(String hour) {
		changeMinutesSpentTextField.setText(hour);
	}
	
	public JTextField getChangeMinutesSpent() {
		return changeMinutesSpentTextField;
	}
	
	public String getChangeSecondsSpentText() {
		return changeSecondsSpentTextField.getText();
	}
	
	public void setChangeSecondsSpentText(String hour) {
		changeSecondsSpentTextField.setText(hour);
	}
	
	public JTextField getChangeSecondsSpent() {
		return changeSecondsSpentTextField;
	}

	public void checkTimeLimit() {
		if (assignment.limitExceeded())
			setErrorLabelTime("You have exceeded your time limit!");
		else 
			setErrorLabelTime("");
	}
		
}
