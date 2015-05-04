package view;

import javax.swing.JPanel;

import model.Schedule;

public class AgendaPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Schedule schedule;
	
	public AgendaPanel(Schedule schedule) {
		this.schedule = schedule;
		
		this.setLayout(null);
	}
	
}
