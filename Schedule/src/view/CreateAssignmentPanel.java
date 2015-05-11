package view;

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

import controller.CreateAssignmentController;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.SubTitleLabel;
import jcomponents.TitleLabel;
import model.Employee;
import model.Schedule;
import model.Task;

public class CreateAssignmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	private Task task;
	
	private TitleLabel titleLabel;
	private SubTitleLabel findEmployeesLabel, optionalTitle;
	private Label startDateLabel, hourInDayLabel, timeLabel, freeEmployeesLabel, assignmentDescriptionLabel;
	private ErrorLabel errorLabel;
	private JButton createAsisgnment, findFreeEmployees, back;
	private JTextField hourInDayText, timeText;
	private JTextArea assignmentDescription;
	
	private JList findEmployeesList;
	private JScrollPane scrollPaneFindEmployees, descriptionScrollPane;
	
	private DefaultListModel<Employee> findEmployees = new DefaultListModel<Employee>();
	
	private UtilDateModel findEmployeesDateModel;
	private JDatePanelImpl findEmployeesDatePanel;
	private JDatePickerImpl findEmployeesDatePicker;
	
	
	public CreateAssignmentPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Create Assignment");
		this.optionalTitle = new SubTitleLabel("Optional options");
		this.back = new JButton("Back");
		this.createAsisgnment = new JButton("Create Assignment");
		this.findEmployeesLabel = new SubTitleLabel("Find employees for the assignment");
		this.findFreeEmployees = new JButton("Find employees");
		this.hourInDayLabel = new Label("Hour of day");
		this.hourInDayText = new JTextField();
		this.timeLabel = new Label("Time for assignment (hours)");
		this.timeText = new JTextField();
		this.startDateLabel = new Label("Startdate");
		this.errorLabel = new ErrorLabel("");
		
		this.findEmployeesDateModel = new UtilDateModel();
	    this.findEmployeesDatePanel = new JDatePanelImpl(findEmployeesDateModel);
	    this.findEmployeesDatePicker = new JDatePickerImpl(findEmployeesDatePanel);
	    
	    this.freeEmployeesLabel = new Label("Free employees");
		this.findEmployeesList = new JList(findEmployees);
		this.scrollPaneFindEmployees = new JScrollPane();
		this.scrollPaneFindEmployees.setViewportView(findEmployeesList);
	    
		this.assignmentDescriptionLabel = new Label("Assignment description");
	    this.assignmentDescription = new JTextArea();
	    this.descriptionScrollPane = new JScrollPane(assignmentDescription);
		
		titleLabel.setLocation(20, 20);
		optionalTitle.setBounds(20, 80, 200, 20);
	    findEmployeesLabel.setBounds(470, 80, 450, 20);
		
		startDateLabel.setBounds(470, 110, 200, 20);
		findEmployeesDatePicker.setBounds(470,130,200,40);
		
		hourInDayLabel.setBounds(470, 190, 200, 20);
		hourInDayText.setBounds(470,210, 200, 40);
		
		timeLabel.setBounds(470, 270, 200, 20);
		timeText.setBounds(470, 290, 200, 40);
		
		errorLabel.setBounds(470,350,200,20);
		
		findFreeEmployees.setBounds(470, 390, 150, 40);
		
		freeEmployeesLabel.setBounds(690, 110, 200, 20);
		scrollPaneFindEmployees.setBounds(690, 130, 200, 240);
		
		createAsisgnment.setBounds(690, 390, 150, 40);
		
		assignmentDescriptionLabel.setBounds(20, 110, 200, 20);
		descriptionScrollPane.setBounds(20, 130, 200, 200);
		
		back.setBounds(820,520,120,40);
		
		this.setLayout(null);
		this.add(optionalTitle);
		this.add(findEmployeesDatePicker);
		this.add(titleLabel);;
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
		this.add(assignmentDescriptionLabel);
		this.add(descriptionScrollPane);
		
	}
	
	public String getAssignmentDescription() {
		return assignmentDescription.getText();
	}
	
	public void registerListener(CreateAssignmentController controller) {
		back.addActionListener(controller);
		createAsisgnment.addActionListener(controller);
		findFreeEmployees.addActionListener(controller);
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
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTitleLabel(String title) {
		this.titleLabel.setText(title);
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
