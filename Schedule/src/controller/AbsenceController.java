package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Employee;
import model.Schedule;
import model.Status;
import view.View;

public class AbsenceController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	private List<Employee> absenceEmployees;
	private Employee absenceEmployee = null;
	private int time = 0;
	private Calendar startDate = null;
	
	public AbsenceController(Schedule schedule, View view) {
		this.schedule = schedule;
		this.view = view;
		
		view.getAbsencePanel().registerListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
			case "Is sick":
				absenceEmployees = schedule.searchEmployee(view.getAbsencePanel().getSickEmployee());
				if (absenceEmployees.size() != 1 || view.getAbsencePanel().getSickEmployee().length() != 4) {
					view.getAbsencePanel().setErrorLabel("Wrong initials");
					break;
				}
				
				absenceEmployee = absenceEmployees.get(0);
				try {
					schedule.getUser().reportAbsence(absenceEmployee, Status.SICK, schedule.getDate(), 8*60);
					view.resetErrorLabels();
				} catch (Exception error) {
					view.getAbsencePanel().setErrorLabel(error.getMessage());
				}
				view.getAbsencePanel().updateListSick();
				
				break;
				
			case "Add employee":
				absenceEmployees = schedule.searchEmployee(view.getAbsencePanel().getEmployee());
				
				if (view.getAbsencePanel().getStartDate() != null) {
					startDate = new GregorianCalendar();
					startDate.setTime(view.getAbsencePanel().getStartDate());
				}				
				
				if (absenceEmployees.size() != 1 || view.getAbsencePanel().getEmployee().length() != 4) {
					view.getAbsencePanel().setErrorLabel("Wrong initials");
					break;
				}
				
				if (startDate == null) {
					view.getAbsencePanel().setErrorLabel("Set a date");
					break;
				}
				
				try {  
					time = Integer.parseInt(view.getAbsencePanel().getTime());
				} catch(NumberFormatException error) {
					System.err.println(error);
					view.getAbsencePanel().setErrorLabel("Correct your time");
					break;
				}
				
				absenceEmployee = absenceEmployees.get(0);
				try {
					schedule.getUser().reportAbsence(absenceEmployee, Status.VACATION, startDate, time);
					view.resetErrorLabels();
					
				} catch (Exception error) {
					view.getAbsencePanel().setErrorLabel(error.getMessage());
				}
				view.getAbsencePanel().updateListSick();
				break;
				
			case "Back":
				view.resetErrorLabels();
				view.remove(view.getAbsencePanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
