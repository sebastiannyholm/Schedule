package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ControlController;
import model.Schedule;

public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	
	private JLabel titleLabel, errorLabel;
	private JButton absence, myAgenda, myProjects, logOut, manageEmployee;
	
	public ControlPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Main menu");
		this.absence = new JButton("Absence");
		this.myAgenda = new JButton("My agenda");
		this.myProjects = new JButton("My projects");
		this.manageEmployee = new JButton("Manage employees");
		this.logOut = new JButton("Log out");
		this.errorLabel = new JLabel("");
		
		this.setLayout(null);
		
		titleLabel.setBounds(20, 20, 460, 40);
		absence.setBounds(20,80, 120, 40);
		myAgenda.setBounds(20,140, 120, 40);
		myProjects.setBounds(20,200, 120, 40);
		manageEmployee.setBounds(250,80,140,40);
		logOut.setBounds(20,260,120,40);
		
		errorLabel.setBounds(20, 430, 300, 40);
		errorLabel.setForeground(Color.RED);
		
		
		this.add(titleLabel);
		this.add(absence);
		this.add(myAgenda);
		this.add(myProjects);
		this.add(logOut);
		this.add(errorLabel);
		
		if (schedule.getUser().isAdmin())
			this.add(manageEmployee);
		
	}
	
	public void registerListener(ControlController controller) {
		absence.addActionListener(controller);
		myAgenda.addActionListener(controller);
		myProjects.addActionListener(controller);
		manageEmployee.addActionListener(controller);
		logOut.addActionListener(controller);
	}
	
	public void setErrorLabel(String error){
		errorLabel.setText(error);
	}

}
