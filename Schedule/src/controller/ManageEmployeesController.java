package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import view.View;
import model.Address;
import model.Employee;
import model.Project;
import model.Schedule;

public class ManageEmployeesController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	public ManageEmployeesController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getManageEmployeesPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Add employee":
				
				String name = view.getManageEmployeesPanel().getNameLabel();
				String initials = view.getManageEmployeesPanel().getInitialLabel();
				String ageString = view.getManageEmployeesPanel().getAgeLabel();
				int age = 0;
				String street = view.getManageEmployeesPanel().getStreetLabel();
				String streetNumberString = view.getManageEmployeesPanel().getStreetNumberLabel();
				int streetNumber = 0;
				String zipCodeString = view.getManageEmployeesPanel().getZipCodeLabel();
				int zipCode = 0;
				String city = view.getManageEmployeesPanel().getCityLabel();
				
				if (name.equals("")) {
					view.getManageEmployeesPanel().setErrorLabel("Give the employee a name");
					break;
				}
				
				else if (initials.equals("")) {
					view.getManageEmployeesPanel().setErrorLabel("Enter the initials right");
					break;
				}
				
				try {  
					age = Integer.parseInt(ageString);
				} catch(NumberFormatException error) {
					view.getManageEmployeesPanel().setErrorLabel("Set an age");
					break;
				}
				
				if (street.equals("")) {
					view.getManageEmployeesPanel().setErrorLabel("Set a street");
					break;
				}
				
				try {  
					streetNumber = Integer.parseInt(streetNumberString);
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getManageEmployeesPanel().setErrorLabel("Set a street number");
					break;
				}
				
				try {  
					zipCode = Integer.parseInt(zipCodeString);
				} catch(NumberFormatException error) {
					view.getManageEmployeesPanel().setErrorLabel("Set a zip code");
					break;
				}
				
				if (city.equals("")) {
					view.getCreateProjectPanel().setErrorLabel("Set a city");
					break;
				}
				
				Address address = new Address(street, streetNumber, zipCode, city);
				Employee employee = new Employee(name, initials, age, address, schedule);
				try {
					schedule.getUser().addEmployee(employee);
				} catch (Exception error) {
					System.err.println(error);
				}
				view.getManageEmployeesPanel().updateList();
				break;
				
			case "Remove employee":
				if ( view.getManageEmployeesPanel().getSelectedIndex() > -1 ) {
					try {
						schedule.getUser().removeEmployee(view.getManageEmployeesPanel().getSelected());
						view.getManageEmployeesPanel().setErrorLabel("");
					} catch (Exception error) {
						view.getManageEmployeesPanel().setErrorLabel(error.getMessage());
					}
					
					view.getManageEmployeesPanel().updateList();
				}
				else 
					view.getManageEmployeesPanel().setErrorLabel("No employee has been selected");
				
				break;
				
			case "Back":
				view.remove(view.getManageEmployeesPanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
