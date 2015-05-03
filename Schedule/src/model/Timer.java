package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Timer {

	private Calendar startDate, endDate;
	private int time, days, hours, minutes;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	
	public Timer(Calendar startDate, Calendar endDate, int time) {		
		this.startDate = new GregorianCalendar();
		this.startDate.setTime(startDate.getTime());
		
		this.endDate = new GregorianCalendar();
		this.endDate.setTime(endDate.getTime());
		
		this.time = time;
		
		agenda();
	}
	
	private void agenda() {
		System.out.println(df.format(startDate.getTime()));
		System.out.println(df.format(endDate.getTime()));
		System.out.println();
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public int getTimeForATask() {
		return time;
	}

	public boolean isInPeriod(Calendar startDate, Calendar endDate) {
		return (this.startDate.compareTo(startDate) >= 0 && this.startDate.before(endDate)) 
				|| (this.endDate.after(startDate) && this.endDate.compareTo(endDate) <= 0)
				|| (this.startDate.compareTo(startDate) <= 0 && this.endDate.compareTo(endDate) >= 0);
	}
	
}
