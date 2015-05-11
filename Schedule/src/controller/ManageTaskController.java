package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import view.View;
import model.Employee;
import model.Schedule;
import model.Task;

public class ManageTaskController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	private String timeString = "", hourInDayString = "";
	private int time, hourInDay;
	private Calendar startDate;
	private Employee employee;
	private Task task;
	
	public ManageTaskController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getManageTaskPanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		task = view.getManageTaskPanel().getTask();
		
		switch (e.getActionCommand()) {
				
			case "Change description":
				view.getManageTaskPanel().remove(view.getManageTaskPanel().getDescriptionLabelComp());
				view.getManageTaskPanel().remove(view.getManageTaskPanel().getChangeDescriptionComp());
				view.getManageTaskPanel().add(view.getManageTaskPanel().getDescriptionTextComp());
				view.getManageTaskPanel().add(view.getManageTaskPanel().getSaveDescriptionComp());
				view.reset();
				break;
				
			case "Save description":
				task.setDescription(view.getManageTaskPanel().getDescriptionText());
				
				view.getManageTaskPanel().setDescriptionLabel(task.getDescription());
				view.getManageTaskPanel().remove(view.getManageTaskPanel().getDescriptionTextComp());
				view.getManageTaskPanel().remove(view.getManageTaskPanel().getSaveDescriptionComp());
				view.getManageTaskPanel().add(view.getManageTaskPanel().getDescriptionLabelComp());
				view.getManageTaskPanel().add(view.getManageTaskPanel().getChangeDescriptionComp());
				
				view.reset();
				break;	
		
			case "Create Assignment":
				task = view.getManageTaskPanel().getTask();
				view.getCreateAssignmentPanel().setTask(task);
				view.remove(view.getManageTaskPanel());
				view.add(view.getCreateAssignmentPanel());
				view.reset();
				
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.getManageTaskPanel().updateFindEmployeesList(new LinkedList<Employee>());
				view.remove(view.getManageTaskPanel());
				view.add(view.getManageProjectPanel());
				view.reset();
				break;

		}
	}
	
}
