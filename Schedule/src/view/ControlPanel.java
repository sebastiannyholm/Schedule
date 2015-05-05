package view;

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
	
	private JLabel titleLabel;
	private JButton absence, myAgenda, myProjects, logOut;
	
	public ControlPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Main menu");
		this.absence = new JButton("Absence");
		this.myAgenda = new JButton("My agenda");
		this.myProjects = new JButton("My projects");
		this.logOut = new JButton("Log out");
		
		this.setLayout(null);
		
		titleLabel.setBounds(20, 20, 460, 40);
		absence.setBounds(20,80, 100, 40);
		myAgenda.setBounds(20,140, 100, 40);
		myProjects.setBounds(20,200, 100, 40);
		logOut.setBounds(20,260,100,40);
		
		this.add(titleLabel);
		this.add(absence);
		this.add(myAgenda);
		this.add(myProjects);
		this.add(logOut);
		
	}
	
	public void registerListener(ControlController controller) {
		absence.addActionListener(controller);
		myAgenda.addActionListener(controller);
		myProjects.addActionListener(controller);
		logOut.addActionListener(controller);
	}

}
