package view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import controller.AbsenceController;
import model.Employee;
import model.Project;
import model.Schedule;

public class AbsencePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Schedule schedule;
	private JLabel titleLabel, employeeListTitleLabel;
	private JButton addEmployee, back;
	private JTextField time;
	private JCalendar startDate;
	private JList absenceList;
	private JScrollPane scrollPane;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();
	
	public AbsencePanel(Schedule schedule) {
		this.schedule = schedule;
		
		this.titleLabel = new JLabel("Absence");
		this.employeeListTitleLabel = new JLabel("Employees");
		this.addEmployee = new JButton("Add employee");
		this.back = new JButton("Back");
		this.absenceList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(absenceList);
		this.startDate = new JCalendar();
		
		for (Employee employee : schedule.getEmployees())
			if (employee.isAbsent())
				employees.addElement(employee);
		
		titleLabel.setBounds(20, 20, 460, 40);
		employeeListTitleLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 300);
		
		addEmployee.setBounds(250, 120, 120, 40);
		back.setBounds(250, 180, 120, 40);
		
		startDate.setBounds(250,250,200,200);
		
		this.setLayout(null);
		
		this.add(startDate);
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(scrollPane);
		this.add(addEmployee);
		this.add(back);
	}

	public void registerListener(AbsenceController controller) {
		addEmployee.addActionListener(controller);
		back.addActionListener(controller);
	}
	
}
