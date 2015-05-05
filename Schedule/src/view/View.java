package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

import model.Schedule;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int SIZE = 500;
	
	private Schedule schedule;
	
	private LogInPanel logInPanel;
	private ControlPanel controlPanel;
	private AbsencePanel absencePanel;
	private AgendaPanel agendaPanel;
	private ProjectPanel projectPanel;
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
		
		this.setPreferredSize(new Dimension(SIZE, SIZE));
		this.setResizable(false);
		this.setLayout(new CardLayout());

		this.add(logInPanel);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		
	}

	public void reset() {
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public void setUpPanels() {
		this.controlPanel = new ControlPanel(schedule);
		this.absencePanel = new AbsencePanel(schedule);
		this.agendaPanel = new AgendaPanel(schedule);
		this.projectPanel = new ProjectPanel(schedule);
	}
	
	public LogInPanel getLogInPanel() {
		return logInPanel;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public AbsencePanel getAbsencePanel() {
		return absencePanel;
	}

	public AgendaPanel getAgendaPanel() {
		return agendaPanel;
	}

	public ProjectPanel getProjectPanel() {
		return projectPanel;
	}
	
}
