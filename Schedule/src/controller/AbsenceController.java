package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import view.View;
import model.Employee;
import model.Schedule;
import model.Status;

public class AbsenceController implements ActionListener {

	private Schedule schedule;
	private View view;
	
	List<Employee> absenceEmployees;
	Employee absenceEmployee = null;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
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
				if (absenceEmployees.size() == 1) {
					absenceEmployee = absenceEmployees.get(0);
					try {
						schedule.getUser().reportAbsence(absenceEmployee, Status.SICK, schedule.getDate(), 8*60);
					} catch (Exception error) {
						System.err.println(error);
					}
					view.getAbsencePanel().updateList();
				} else {
					view.getAbsencePanel().setErrorLabel("Wrong initials");
				}
				break;
				
			case "Add employee":
				absenceEmployees = schedule.searchEmployee(view.getAbsencePanel().getEmployee());
				if (absenceEmployees.size() == 1) {
					absenceEmployee = absenceEmployees.get(0);
					
					try {
						schedule.getUser().reportAbsence(absenceEmployee, Status.VACATION, view.getAbsencePanel().getStartDate(), view.getAbsencePanel().getTime());
					} catch (Exception error) {
						System.err.println(error);
					}
					view.getAbsencePanel().updateList();
				} else {
					view.getAbsencePanel().setErrorLabel("Wrong initials");
				}
				break;
				
			case "Back":
				view.remove(view.getAbsencePanel());
				view.add(view.getControlPanel());
				view.reset();
				break;

		}
	}
	
}
