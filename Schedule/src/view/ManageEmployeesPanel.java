package view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.ManageEmployeesController;
import model.Address;
import model.Employee;
import model.Project;
import model.Schedule;

public class ManageEmployeesPanel extends JPanel {

	private Schedule schedule;
	
	private JLabel titleLabel, employeeListTitleLabel, addEmployeeLabel, nameLabel, initialLabel, ageLabel, addressLabel, streetLabel, streetNumberLabel, zipCodeLabel, cityLabel;;
	private JButton addEmployee, removeEmployee, back;
	private JTextField nameText, initialText, ageText, streetText, streetNumberText, zipCodeText, cityText;
	
	private JList employeeList;
	private JScrollPane scrollPane;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();

	
	public ManageEmployeesPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Manage employees");
		this.employeeListTitleLabel = new JLabel("All employees");
		this.addEmployee = new JButton("Add employee");
		this.removeEmployee = new JButton("Remove employee");
		this.back = new JButton("Back");
		this.employeeList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(employeeList);
		this.addEmployeeLabel = new JLabel("Add new employee");
		this.nameLabel = new JLabel("Name");
		this.nameText = new JTextField("");
		this.initialLabel = new JLabel("Initials");
		this.initialText = new JTextField("");
		this.ageLabel = new JLabel("Age");
		this.ageText = new JTextField("");
		this.addressLabel = new JLabel("Address");
		this.streetLabel = new JLabel("Street");
		this.streetText = new JTextField("");
		this.streetNumberLabel = new JLabel("Street number");
		this.streetNumberText = new JTextField("");
		this.zipCodeLabel = new JLabel("Zip code");
		this.zipCodeText = new JTextField("");
		this.cityLabel = new JLabel("City");
		this.cityText = new JTextField("");
	
		titleLabel.setBounds(20, 20, 460, 40);
		employeeListTitleLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 300);
		
		nameLabel.setBounds(240, 80, 120, 30);
		nameText.setBounds(240,110,120,40);
		
		initialLabel.setBounds(370, 80, 120, 30);
		initialText.setBounds(370, 110, 120, 40);
		
		ageLabel.setBounds(240, 150, 120, 30);
		ageText.setBounds(240, 180, 120, 40);
		
		streetLabel.setBounds(240, 220, 120, 30);
		streetText.setBounds(240, 250, 120, 40);
		
		streetNumberLabel.setBounds(370, 220, 120, 30);
		streetNumberText.setBounds(370, 250, 120, 40);

		zipCodeLabel.setBounds(240, 290, 120, 30);
		zipCodeText.setBounds(240, 320, 120, 40);
		
		cityLabel.setBounds(370, 290, 120, 30);
		cityText.setBounds(370, 320, 120, 40);
		
		addEmployee.setBounds(240, 380, 120, 40);
		removeEmployee.setBounds(370, 380, 120, 40);
		
		back.setBounds(370, 430, 120, 40);

		this.setLayout(null);
		
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(scrollPane);
		this.add(addEmployeeLabel);
		this.add(nameLabel);
		this.add(nameText);
		this.add(initialLabel);
		this.add(initialText);
		this.add(ageLabel);
		this.add(ageText);
		this.add(streetLabel);
		this.add(streetText);
		this.add(streetNumberLabel);
		this.add(streetNumberText);
		this.add(zipCodeLabel);
		this.add(zipCodeText);
		this.add(cityLabel);
		this.add(cityText);
		this.add(addEmployee);
		this.add(removeEmployee);
		this.add(back);
	}

	public void registerListener(ManageEmployeesController controller) {
		back.addActionListener(controller);
		addEmployee.addActionListener(controller);
		removeEmployee.addActionListener(controller);
	}
	
	public int getSelectedIndex() {
		return employeeList.getSelectedIndex();
	}
	
	public Employee getSelected() {
		int index = employeeList.getSelectedIndex();
		
		return employees.get(index);
	}
	
	public String getNameLabel() {
		return nameText.getText();
	}

	public String getInitialLabel() {
		return initialText.getText();
	}
	
	public String getAgeLabel() {
		return ageText.getText();
	}
	
	public String getStreetLabel() {
		return streetText.getText();
	}
	
	public String getStreetNumberLabel() {
		return streetNumberText.getText();
	}
	
	public String getZipCodeLabel() {
		return zipCodeText.getText();
	}
	
	public String getCityLabel() {
		return cityText.getText();
	}
	
	public void updateList() {
		
		employees.clear();
		
		for (Employee employee : schedule.getEmployees())
			employees.addElement(employee);
		
	}
	
}
