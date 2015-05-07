package view;

import java.awt.Color;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import controller.CreateProjectController;
import model.Schedule;

public class CreateProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Schedule schedule;
	private JLabel titleLabel, errorLabel, projectLeaderLabel, projectNameLabel, startDateLabel, endDateLabel;
	private JButton createProject, back;
	private JTextField projectName, projectLeader;
	
	private UtilDateModel startDateModel, endDateModel;
	private JDatePanelImpl startDatePanel, endDatePanel;
	private JDatePickerImpl startDatePicker, endDatePicker;
	
	
	public CreateProjectPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("Create project");
		this.back = new JButton("Back");
		this.projectName = new JTextField("Name");
		this.projectLeader = new JTextField("Initials");
		this.createProject = new JButton("Create project");
		this.errorLabel = new JLabel("");
		this.projectLeaderLabel = new JLabel("Project leader (initials)");
		this.projectNameLabel = new JLabel("Project name");
		this.startDateLabel = new JLabel("Project start date");
		this.endDateLabel = new JLabel("Project end date");
		
		this.startDateModel = new UtilDateModel();
		this.endDateModel = new UtilDateModel();
		this.startDatePanel = new JDatePanelImpl(startDateModel);
	    this.endDatePanel = new JDatePanelImpl(endDateModel);
	    this.startDatePicker = new JDatePickerImpl(startDatePanel);
	    this.endDatePicker = new JDatePickerImpl(endDatePanel);
		
		titleLabel.setBounds(20, 20, 460, 40);
		
		projectNameLabel.setBounds(20, 80, 200, 40);
		projectName.setBounds(20, 120, 200, 40);
		
		projectLeaderLabel.setBounds(240, 80, 200, 40);
		projectLeader.setBounds(240, 120, 200, 40);
		
		startDateLabel.setBounds(20, 160, 200, 40);
		startDatePicker.setBounds(20, 200, 200, 40);
		
		endDateLabel.setBounds(240, 160, 200, 40);
		endDatePicker.setBounds(240,200,200,40);
		
		errorLabel.setBounds(20, 260, 400, 40);
		
		createProject.setBounds(20, 420, 150, 40);
		back.setBounds(380, 420, 100, 40);
		
		errorLabel.setForeground(Color.RED);
		
		this.setLayout(null);
		
		this.add(projectLeaderLabel);
		this.add(projectNameLabel);
		this.add(startDateLabel);
		this.add(endDateLabel);
		this.add(errorLabel);
		this.add(back);
		this.add(titleLabel);
		this.add(startDatePicker);
		this.add(endDatePicker);
		this.add(projectName);
		this.add(projectLeader);
		this.add(createProject);
		
	}

	public void registerListener(CreateProjectController controller) {
		createProject.addActionListener(controller);
		back.addActionListener(controller);
	}
	
	public Date getStartDate() {
        return (Date) startDatePicker.getModel().getValue();
	}
	
	public Date getEndDate() {
        return (Date) endDatePicker.getModel().getValue();
	}
	
	public String getProjectName() {
		return projectName.getText();
	}
	
	public String getProjectLeader() {
		return projectLeader.getText();
	}

	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}
}
