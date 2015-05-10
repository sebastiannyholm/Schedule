package view;

import java.awt.Color;

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
import model.Employee;
import model.Schedule;
import controller.ManageEmployeesController;

public class ManageEmployeesPanel extends JPanel {

	private Schedule schedule;
	
	private TitleLabel titleLabel;
	private SubTitleLabel employeeListTitleLabel, addEmployeeTitleLabel;
	private Label addEmployeeLabel, nameLabel, initialLabel, ageLabel, addressLabel, streetLabel, streetNumberLabel, zipCodeLabel, cityLabel;
	private ErrorLabel errorLabel;
	private JButton addEmployee, removeEmployee, back;
	private JTextField nameText, initialText, ageText, streetText, streetNumberText, zipCodeText, cityText;
	
	private JList employeeList;
	private JScrollPane scrollPane;
	
	private DefaultListModel<Employee> employees = new DefaultListModel<Employee>();

	
	public ManageEmployeesPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Manage employees");
		this.employeeListTitleLabel = new SubTitleLabel("All employees");
		this.addEmployeeTitleLabel = new SubTitleLabel("Add employee");
		this.addEmployee = new JButton("Add employee");
		this.removeEmployee = new JButton("Remove employee");
		this.back = new JButton("Back");
		this.employeeList = new JList(employees);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(employeeList);
		this.addEmployeeLabel = new Label("Add new employee");
		this.nameLabel = new Label("Name");
		this.errorLabel = new ErrorLabel("");
		this.nameText = new JTextField("");
		this.initialLabel = new Label("Initials");
		this.initialText = new JTextField("");
		this.ageLabel = new Label("Age");
		this.ageText = new JTextField("");
		this.addressLabel = new Label("Address");
		this.streetLabel = new Label("Street");
		this.streetText = new JTextField("");
		this.streetNumberLabel = new Label("Street number");
		this.streetNumberText = new JTextField("");
		this.zipCodeLabel = new Label("Zip code");
		this.zipCodeText = new JTextField("");
		this.cityLabel = new Label("City");
		this.cityText = new JTextField("");
	
		titleLabel.setLocation(20, 20);
		employeeListTitleLabel.setBounds(20, 80, 200, 20);
		scrollPane.setBounds(20, 120, 250, 380);
		removeEmployee.setBounds(20, 520, 140, 40);
		
		addEmployeeTitleLabel.setBounds(310, 80, 200, 20);
		
		nameLabel.setBounds(310, 120, 160, 20);
		nameText.setBounds(310,140, 160,40);
		
		initialLabel.setBounds(500, 120, 160, 20);
		initialText.setBounds(500, 140, 160, 40);
		
		ageLabel.setBounds(310, 180, 160, 20);
		ageText.setBounds(310, 200, 160, 40);
		
		streetLabel.setBounds(310, 240, 160, 20);
		streetText.setBounds(310, 260, 160, 40);
		
		streetNumberLabel.setBounds(500, 240, 160, 20);
		streetNumberText.setBounds(500, 260, 160, 40);

		zipCodeLabel.setBounds(310, 300, 160, 20);
		zipCodeText.setBounds(310, 320, 160, 40);
		
		cityLabel.setBounds(500, 300, 160, 20);
		cityText.setBounds(500, 320, 160, 40);
		
		addEmployee.setBounds(310, 380, 140, 40);
		
		back.setBounds(820,520,120,40);
		
		errorLabel.setBounds(310, 440, 300, 20);
		
		this.setLayout(null);
		
		this.add(titleLabel);
		this.add(employeeListTitleLabel);
		this.add(addEmployeeTitleLabel);
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
		this.add(errorLabel);
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
	
	public void setErrorLabel(String error){
		errorLabel.setText(error);
	}

	public void updateList() {
		
		employees.clear();
		
		for (Employee employee : schedule.getEmployees())
			employees.addElement(employee);
		
	}
	
}
