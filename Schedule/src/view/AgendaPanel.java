package view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Schedule;
import model.Task;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class AgendaPanel extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	private JLabel titleLabel, dateLabel;
	private JLabel[] tasksTodayLabel;
	private JButton back;
	
	private UtilDateModel dateModel;
	
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public AgendaPanel(Schedule schedule) {
		this.schedule = schedule;
		this.setLayout(null);
		
		this.titleLabel = new JLabel("Absence");
		this.back = new JButton("Back");
		
		this.dateModel = new UtilDateModel();
	    this.datePanel = new JDatePanelImpl(dateModel);
	    this.datePicker = new JDatePickerImpl(datePanel);
		
	    List<Task> todaysTasks = schedule.getUser().getTodaysAgenda();
		
	    for (int i = 0; i < todaysTasks.size()-1; i++){
	    	tasksTodayLabel[i] = new JLabel("" + todaysTasks.get(i).getName() + " " + schedule.getUser().getTasksAndTime().get(todaysTasks).get(i));
	    	tasksTodayLabel[i].setBounds(20,80+60*i,350,40);
	    	this.add(tasksTodayLabel[i]);
	    }
	    
		titleLabel.setBounds(20, 20, 460, 40);
		
		back.setBounds(250, 360, 120, 40);
		datePicker.setBounds(250,240,200,40);
		
		this.setLayout(null);
		this.add(titleLabel);
		this.add(back);
		
	}
	
}
