package view;

import java.awt.Color;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.TitleLabel;
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
	private TitleLabel titleLabel;
	private Label projectLeaderLabel, projectNameLabel, startDateLabel, endDateLabel, projectDescriptionLabel;
	private ErrorLabel errorLabel;
	private JButton createProject, back;
	private JTextField projectName, projectLeader;
	private JTextArea projectDescription;
	private JScrollPane descriptionScrollPane;
	
	private UtilDateModel startDateModel, endDateModel;
	private JDatePanelImpl startDatePanel, endDatePanel;
	private JDatePickerImpl startDatePicker, endDatePicker;
	
	
	public CreateProjectPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new TitleLabel("Create project");
		this.back = new JButton("Back");
		this.projectName = new JTextField("Name");
		this.projectLeader = new JTextField("Initials");
		this.createProject = new JButton("Create project");
		this.errorLabel = new ErrorLabel("");
		this.projectLeaderLabel = new Label("Project leader (initials)");
		this.projectNameLabel = new Label("Project name");
		this.startDateLabel = new Label("Project start date");
		this.endDateLabel = new Label("Project end date");
		
		this.startDateModel = new UtilDateModel();
		this.endDateModel = new UtilDateModel();
		this.startDatePanel = new JDatePanelImpl(startDateModel);
	    this.endDatePanel = new JDatePanelImpl(endDateModel);
	    this.startDatePicker = new JDatePickerImpl(startDatePanel);
	    this.endDatePicker = new JDatePickerImpl(endDatePanel);
		
	    this.projectDescriptionLabel = new Label("Project description");
	    this.projectDescription = new JTextArea();
	    this.descriptionScrollPane = new JScrollPane(projectDescription);
	    
	    titleLabel.setLocation(20, 20);
		
		projectNameLabel.setBounds(20, 80, 200, 20);
		projectName.setBounds(20, 100, 200, 40);
		
		projectLeaderLabel.setBounds(240, 80, 200, 20);
		projectLeader.setBounds(240, 100, 200, 40);
		
		startDateLabel.setBounds(20, 160, 200, 20);
		startDatePicker.setBounds(20, 180, 200, 40);
		
		endDateLabel.setBounds(240, 160, 200, 20);
		endDatePicker.setBounds(240,180,200,40);
		
		projectDescriptionLabel.setBounds(20, 240, 200, 20);
		descriptionScrollPane.setBounds(20, 260, 200, 200);
		
		errorLabel.setBounds(20, 480, 400, 20);
		
		createProject.setBounds(20, 520, 120, 40);
		
		back.setBounds(820,520,120,40);
		
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
		this.add(projectDescriptionLabel);
		this.add(descriptionScrollPane);
		
	}
	
	public String getProjectDescription() {
		return projectDescription.getText();
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
