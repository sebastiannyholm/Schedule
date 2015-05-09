package view;

import java.util.Date;

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
import controller.AbsenceController;
import model.Employee;
import model.Schedule;

public class AbsencePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TitleLabel titleLabel;
	private Schedule schedule;
	private SubTitleLabel sickEmployeeListTitleLabel, vacationEmployeeListTitleLabel, employeeForTodayTitle, employeeFutureTitle;
	private Label employeeForTodayLabel, employeeFutureLabel, timeLabel, datePickerLabel;
	private JButton employeeForToday, employeeFuture, back;
	private JTextField employeeForTodayText, employeeFutureText, time;
	private JList sickList, vacationList;
	private JScrollPane scrollPaneSick, scrollPaneVacation;
	private ErrorLabel errorLabel;
	
	private DefaultListModel<Employee> sickEmployees = new DefaultListModel<Employee>();
	private DefaultListModel<Employee> vacationEmployees = new DefaultListModel<Employee>();

	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public AbsencePanel(Schedule schedule) {
		this.schedule = schedule;
		
		this.titleLabel = new TitleLabel("Absence");
		
		this.sickEmployeeListTitleLabel = new SubTitleLabel("Todays missing employees");
		this.sickList = new JList(sickEmployees);
		this.scrollPaneSick = new JScrollPane();
		this.scrollPaneSick.setViewportView(sickList);
		
		this.employeeForTodayTitle = new SubTitleLabel("Add employee as sick today");
		this.employeeForTodayLabel = new Label("Employee (initials)");
		this.employeeForTodayText = new JTextField("");
		this.employeeForToday = new JButton("Is sick");
		
		this.employeeFutureTitle = new SubTitleLabel("Send an employee on holyday");
		this.employeeFutureLabel = new Label("Employee (initials)");
		this.employeeFutureText = new JTextField();
		this.datePickerLabel = new Label("Start date of vacation");
		this.dateModel = new UtilDateModel();
	    this.datePanel = new JDatePanelImpl(dateModel);
	    this.datePicker = new JDatePickerImpl(datePanel);
	    this.timeLabel = new Label("Time in hours (8 hours pr. day");
		this.time = new JTextField();
		this.employeeFuture = new JButton("Add employee");
		
		
		
		
		
		
				
		this.errorLabel = new ErrorLabel("");
		
//		this.vacationEmployeeListTitleLabel = new SubTitleLabel("Employees planned vacation");
//		this.vacationList = new JList(vacationEmployees);
//		this.scrollPaneVacation = new JScrollPane();
//		this.scrollPaneVacation.setViewportView(vacationList);
		
	    this.back = new JButton("Back");
	    
	    titleLabel.setLocation(20, 20);
		
		sickEmployeeListTitleLabel.setBounds(20, 80, 250, 20);
		scrollPaneSick.setBounds(20, 120, 250, 440);
		
//		vacationEmployeeListTitleLabel.setBounds(500, 80, 200, 40);
//		scrollPaneVacation.setBounds(500, 120, 200, 440);
		
		employeeForTodayTitle.setBounds(310, 80, 250, 20);
		employeeForTodayLabel.setBounds(310, 120, 250, 20);
		employeeForTodayText.setBounds(310, 140, 250, 40);
		employeeForToday.setBounds(310, 200, 140, 40);
		
		employeeFutureTitle.setBounds(600, 80, 250, 20);
		employeeFutureLabel.setBounds(600, 120, 250, 20);
		employeeFutureText.setBounds(600, 140, 250, 40);
		
		datePickerLabel.setBounds(600, 200, 250, 20);
		datePicker.setBounds(600,220,250,40);
		
		timeLabel.setBounds(600, 280, 250, 20);
		time.setBounds(600, 300, 250, 40);
		
		employeeFuture.setBounds(600, 360, 120, 40);
		
		errorLabel.setBounds(310, 420, 500, 20);
		
		back.setBounds(820,520,120,40);
		
		this.setLayout(null);
		
		if (schedule.getUser().getProjects().size() > 0)
			if (schedule.getUser().getProjects().get(0).equals(schedule.getAllProjects().get(0))) {
				
				this.add(employeeForTodayTitle);
				this.add(employeeForTodayLabel);
				this.add(employeeForTodayText);
				this.add(employeeForToday);
				this.add(employeeFutureTitle);
				this.add(employeeFutureLabel);
				this.add(employeeFutureText);
				this.add(datePickerLabel);
				this.add(datePicker);
				this.add(timeLabel);
				this.add(time);
				this.add(employeeFuture);
				this.add(errorLabel);
			}
		
		this.add(titleLabel);
		this.add(sickEmployeeListTitleLabel);
		this.add(scrollPaneSick);
//		this.add(vacationEmployeeListTitleLabel);
//		this.add(scrollPaneVacation);
		this.add(back);
	}

	public void registerListener(AbsenceController controller) {
		employeeForToday.addActionListener(controller);
		employeeFuture.addActionListener(controller);
		back.addActionListener(controller);
	}
	
	public Date getStartDate() {
        return (Date) datePicker.getModel().getValue();
	}

	public String getSickEmployee() {
		return employeeForTodayText.getText();
	}
	
	public String getEmployee() {
		return employeeFutureText.getText();
	}

	public String getTime() {
		return time.getText();
	}
	
	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
	
	public void updateListSick() {
		
		sickEmployees.clear();
		
		for (Employee employee : schedule.getEmployees())
			if (employee.isAbsent())
				sickEmployees.addElement(employee);
		
	}
	
	public void updateListVacation() {
		
		vacationEmployees.clear();
		
		for (Employee employee : schedule.getEmployees())
			if (employee.isAbsent())
				vacationEmployees.addElement(employee);
		
	}

}
