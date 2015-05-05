package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.WorkingController;
import model.Schedule;
import model.Task;

public class WorkingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	private Task task;
	
	private JLabel titleLabel, timeSpentLabel, timeSpentTitleLabel, changeTimeSpentLabel, errorLabel;
	private JButton back, startWork, stopWork, changeTimeSpent;
	private JTextField changeTimeSpentTextField;
	
	public WorkingPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("");
		this.timeSpentLabel = new JLabel("0");
		this.timeSpentTitleLabel = new JLabel("Time spent on this task");
		this.startWork = new JButton("Start timer");
		this.stopWork = new JButton("Stop timer");
		this.changeTimeSpentLabel = new JLabel("Change time worked on this task");
		this.changeTimeSpent = new JButton("Change time");
		this.changeTimeSpentTextField = new JTextField();
		this.errorLabel = new JLabel("");
		this.back = new JButton("Back");
		
		this.setLayout(null);
		
		titleLabel.setBounds(20, 20, 460, 40);
		timeSpentTitleLabel.setBounds(20,80,200,40);
		timeSpentLabel.setBounds(20,120,200,40);
		startWork.setBounds(250, 80, 120, 40);
		stopWork.setBounds(250, 140, 120, 40);
		changeTimeSpentLabel.setBounds(20, 200, 200, 40);
		changeTimeSpentTextField.setBounds(20, 240, 200, 40);
		changeTimeSpent.setBounds(250, 240, 120, 40);
		errorLabel.setBounds(20,300,200,40);
		back.setBounds(250, 360, 120, 40);
		
		this.setLayout(null);
		
		this.add(titleLabel);
		this.add(timeSpentTitleLabel);
		this.add(timeSpentLabel);
		this.add(startWork);
		this.add(stopWork);
		this.add(changeTimeSpentLabel);
		this.add(changeTimeSpentTextField);
		this.add(changeTimeSpent);
		this.add(errorLabel);
		this.add(back);
		
		stopWork.setEnabled(false);
	}

	public void registerListener(WorkingController controller) {
		back.addActionListener(controller);
		startWork.addActionListener(controller);
		stopWork.addActionListener(controller);
		changeTimeSpent.addActionListener(controller);
	}
	
	public String getChangeTimeSpent() {
		return changeTimeSpentTextField.getText();
	}
	
	public void setTitleLabel(String title) {
		titleLabel.setText(title);
	}
	
	public void setTimeSpentLabel(String timeSpent) {
		this.timeSpentLabel.setText(timeSpent + " minutes");
	}
	
	public String getTimeSpentLabel() {
		return timeSpentLabel.getText();
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}

	public void setStartWork(boolean b) {
		startWork.setEnabled(b);
	}

	public void setStopWork(boolean b) {
		stopWork.setEnabled(b);
	}

	public void setErrorLabel(String error) {
		errorLabel.setText(error);
	}

}
