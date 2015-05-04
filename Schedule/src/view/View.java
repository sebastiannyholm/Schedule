package view;

import java.awt.CardLayout;

import javax.swing.JFrame;

import model.Schedule;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Schedule schedule;
	private LogInPanel logInPanel;
//	private ProjectPanel gamePanel;
//	private ManageProjectPanel manageProjectPanel;
//	private CreateProjectPanel createProjectPanel;
//	private CreateTaskPanel createTaskPanel;
//	private ManageTaskPanel manageTaskPanel;
//	
//	private AbsencePanel absencePanel;
//	private SickPanel sickPanel;
//	private VacationPanel vacationPanel;
//	
	public View(Schedule schedule) {
		super("Softwarehuset");
		this.schedule = schedule;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.logInPanel = new LogInPanel(schedule);
		
		this.setSize(500, 400);
	
		
		this.setResizable(false);
		this.setLayout(new CardLayout());

		this.add(logInPanel);

//		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		
	}
	
}
