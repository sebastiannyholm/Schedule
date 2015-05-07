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
	private CreateProjectPanel createProjectPanel;
	private ManageProjectPanel manageProjectPanel;
	private CreateTaskPanel createTaskPanel;
	private ManageTaskPanel manageTaskPanel;
	private WorkingPanel workingPanel;
	private ManageEmployeesPanel manageEmployeesPanel;
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
		super("Softwarehuset A/S");
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
	}
	
	public void setUpPanels() {
		this.controlPanel = new ControlPanel(schedule);
		this.absencePanel = new AbsencePanel(schedule);
		this.agendaPanel = new AgendaPanel(schedule);
		this.projectPanel = new ProjectPanel(schedule);
		this.createProjectPanel = new CreateProjectPanel(schedule);
		this.manageProjectPanel = new ManageProjectPanel(schedule);
		this.createTaskPanel = new CreateTaskPanel(schedule);
		this.manageTaskPanel = new ManageTaskPanel(schedule);
		this.workingPanel = new WorkingPanel(schedule);
		this.manageEmployeesPanel = new ManageEmployeesPanel(schedule);
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

	public CreateProjectPanel getCreateProjectPanel() {
		return createProjectPanel;
	}

	public ManageProjectPanel getManageProjectPanel() {
		// TODO Auto-generated method stub
		return manageProjectPanel;
	}

	public CreateTaskPanel getCreateTaskPanel() {
		return createTaskPanel;
	}

	public ManageTaskPanel getManageTaskPanel() {
		return manageTaskPanel;
	}

	public WorkingPanel getWorkingPanel() {
		// TODO Auto-generated method stub
		return workingPanel;
	}

	public ManageEmployeesPanel getManageEmployeesPanel() {
		return manageEmployeesPanel;
	}

	public void resetErrorLabels() {
		logInPanel.setErrorLabel("");
		absencePanel.setErrorLabel("");
		agendaPanel.setErrorLabel("");
		controlPanel.setErrorLabel("");
		createProjectPanel.setErrorLabel("");
		createTaskPanel.setErrorLabel("");
		manageEmployeesPanel.setErrorLabel("");
		manageTaskPanel.setErrorLabel("");
		projectPanel.setErrorLabel("");
		workingPanel.setErrorLabel("");
	}
	
}
