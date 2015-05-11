package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jcomponents.ErrorLabel;
import jcomponents.Label;
import jcomponents.SubTitleLabel;
import jcomponents.TitleLabel;
import controller.AgendaController;
import model.Schedule;
import model.Assignment;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class AgendaPanel extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	private List<Assignment> todaysTimers;
	
	private SubTitleLabel dailyListLabel;
	private TitleLabel titleLabel;
	private ErrorLabel errorLabel;
	private JButton checkTask, back;
	
	private JList timerList;
	private JScrollPane scrollPane;
	private DefaultListModel<Assignment> assignments = new DefaultListModel<Assignment>();
	
	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public AgendaPanel(Schedule schedule) {
		this.schedule = schedule;
		this.setLayout(null);
		
		this.titleLabel = new TitleLabel("Agenda");
		this.checkTask = new JButton("Check task");
		this.dailyListLabel = new SubTitleLabel("Todays agenda");
		this.back = new JButton("Back");
		this.timerList = new JList(assignments);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(timerList);
		this.errorLabel = new ErrorLabel("");
		this.dateModel = new UtilDateModel();
	    this.datePanel = new JDatePanelImpl(dateModel);
	    this.datePicker = new JDatePickerImpl(datePanel);
	    
	    titleLabel.setLocation(20, 20);
		dailyListLabel.setBounds(20, 80, 200, 20);
		scrollPane.setBounds(20, 120, 500, 380);
		checkTask.setBounds(20, 520, 120, 40);
		back.setBounds(820,520,120,40);
		datePicker.setBounds(250,240,200,40);
		
		errorLabel.setBounds(160, 520, 300, 40);
		
		this.setLayout(null);
		this.add(titleLabel);
		this.add(dailyListLabel);
		this.add(checkTask);
		this.add(back);
		this.add(scrollPane);
		this.add(errorLabel);
		
	}
	
	public void registerListener(AgendaController controller) {
		checkTask.addActionListener(controller);
		back.addActionListener(controller);
	}	
	
	public int getSelectedIndex() {
		return timerList.getSelectedIndex();
	}
	
	public Assignment getSelected() {
		int index = timerList.getSelectedIndex();
		
		return assignments.get(index);
	}
	
	public void updateList() {

		todaysTimers = schedule.getUser().getTodaysAgenda();
		
		assignments.clear();
	
		for (Assignment assignment : todaysTimers)
	    	assignments.addElement(assignment);	
	}
	
	public void setErrorLabel(String error){
		errorLabel.setText(error);
	}
}
