package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.AbsenceController;
import controller.AgendaController;
import model.Employee;
import model.Schedule;
import model.Task;
import model.Timer;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class AgendaPanel extends JPanel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	private List<Timer> todaysTimers;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	
	private JLabel titleLabel, dailyListLabel, dateLabel;
	private JLabel[] tasksTodayLabel;
	private JButton checkTask, back;
	
	private JList timerList;
	private JScrollPane scrollPane;
	private DefaultListModel<Timer> timers = new DefaultListModel<Timer>();
	
	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public AgendaPanel(Schedule schedule) {
		this.schedule = schedule;
		this.setLayout(null);
		
		this.titleLabel = new JLabel("Agenda");
		this.checkTask = new JButton("Check task");
		this.dailyListLabel = new JLabel("Todays agenda");
		this.back = new JButton("Back");
		this.timerList = new JList(timers);
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(timerList);
		
		this.dateModel = new UtilDateModel();
	    this.datePanel = new JDatePanelImpl(dateModel);
	    this.datePicker = new JDatePickerImpl(datePanel);
	    
	    updateList();
	    
		titleLabel.setBounds(20, 20, 460, 40);
		checkTask.setBounds(250, 120, 120, 40);
		dailyListLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 300);
		
		back.setBounds(250, 360, 120, 40);
		datePicker.setBounds(250,240,200,40);
		
		this.setLayout(null);
		this.add(titleLabel);
		this.add(dailyListLabel);
		this.add(checkTask);
		this.add(back);
		this.add(scrollPane);
		
	}
	
	public void registerListener(AgendaController controller) {
		checkTask.addActionListener(controller);
		back.addActionListener(controller);
	}	
	
	public int getSelectedIndex() {
		return timerList.getSelectedIndex();
	}
	
	public Timer getSelected() {
		int index = timerList.getSelectedIndex();
		
		return timers.get(index);
	}
	
	public void updateList() {

		todaysTimers = schedule.getUser().getTodaysAgenda();
		
		timers.clear();
	
		for (Timer timer : todaysTimers)
	    	timers.addElement(timer);
		
	}
	
}
