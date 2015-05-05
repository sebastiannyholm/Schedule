package view;

import java.util.Date;

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
import controller.AbsenceController;
import model.Employee;
import model.Schedule;

public class AbsencePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Schedule schedule;
	private JLabel titleLabel, employeeListTitleLabel, errorLabel;
	private JButton addEmployeeForToday, addEmployee, back;
	private JTextField employeeForToday, employee, time;
	private JList absenceList;
	private JScrollPane scrollPane;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();

	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public AbsencePanel(Schedule schedule) {
		this.schedule = schedule;
		
		this.titleLabel = new JLabel("Absence");
		this.employeeListTitleLabel = new JLabel("Employees");
		this.employeeForToday = new JTextField("Vælg employee");
		this.addEmployeeForToday = new JButton("Is sick");
		this.employee = new JTextField("Vælg employee");
		this.time = new JTextField("Time");
		this.addEmployee = new JButton("Add employee");
		this.back = new JButton("Back");
		this.absenceList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(absenceList);
		this.errorLabel = new JLabel("");
		
		this.dateModel = new UtilDateModel();
	    this.datePanel = new JDatePanelImpl(dateModel);
	    this.datePicker = new JDatePickerImpl(datePanel);
		
		for (Employee employee : schedule.getEmployees())
			if (employee.isAbsent())
				employees.addElement(employee);
		
		titleLabel.setBounds(20, 20, 460, 40);
		employeeListTitleLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 300);
		
		employeeForToday.setBounds(250, 120, 100, 40);
		addEmployeeForToday.setBounds(370, 120, 100, 40);
		employee.setBounds(250, 180, 100, 40);
		time.setBounds(370, 180, 100, 40);
		addEmployee.setBounds(250, 300, 120, 40);
		back.setBounds(250, 360, 120, 40);
		errorLabel.setBounds(250, 80, 200, 40);
		
		datePicker.setBounds(250,240,200,40);
		
		this.setLayout(null);
		
		if (schedule.getUser().getProjects().size() > 0)
			if (schedule.getUser().getProjects().get(0).equals(schedule.getAllProjects().get(0))) {
				this.add(datePicker);
				this.add(employeeForToday);
				this.add(addEmployeeForToday);
				this.add(employee);
				this.add(time);
				this.add(addEmployee);
				this.add(errorLabel);
			}
		
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(scrollPane);
		this.add(back);
	}

	public void registerListener(AbsenceController controller) {
		addEmployeeForToday.addActionListener(controller);
		addEmployee.addActionListener(controller);
		back.addActionListener(controller);
	}
	
	public Date getStartDate() {
        return (Date) datePicker.getModel().getValue();
	}

	public String getSickEmployee() {
		return employeeForToday.getText();
	}
	
	public String getEmployee() {
		return employee.getText();
	}

	public String getTime() {
		return time.getText();
	}
	
	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
	
	public void updateList() {
		
		employees.clear();
		
		for (Employee employee : schedule.getEmployees())
			if (employee.isAbsent())
				employees.addElement(employee);
		
	}
	
}
