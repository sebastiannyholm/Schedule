package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jcomponents.ErrorLabel;
import jcomponents.TitleLabel;
import controller.ControlController;
import model.Schedule;

public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	
	private TitleLabel titleLabel;
	private ErrorLabel errorLabel;
	private JButton absence, myAgenda, myProjects, logOut, manageEmployee;
	
	public ControlPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Main menu");
		this.absence = new JButton("Check absence");
		this.myAgenda = new JButton("My agenda");
		this.myProjects = new JButton("My projects");
		this.manageEmployee = new JButton("Manage employees");
		this.logOut = new JButton("Log out");
		this.errorLabel = new ErrorLabel("");
		
		this.setLayout(null);
		
		titleLabel.setLocation(20, 170);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		
		myAgenda.setBounds(335,230,140,40);
		absence.setBounds(335,290, 140, 40);
		myProjects.setBounds(495,230,140,40);
		
		errorLabel.setBounds(20, 430, 300, 40);
		
		if (schedule.getUser().isAdmin()) {
			manageEmployee.setBounds(495,290,140,40);
			logOut.setBounds(495,350,140,40);
			this.add(manageEmployee);
		}
		else {
			logOut.setBounds(495,290,140,40);
		}
		
		this.add(titleLabel);
		this.add(absence);
		this.add(myAgenda);
		this.add(myProjects);
		this.add(logOut);
		this.add(errorLabel);
		
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
