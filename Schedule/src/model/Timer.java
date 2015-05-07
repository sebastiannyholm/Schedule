package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Timer {

	private Task task;
	private Calendar startDate, endDate;
	private int timeSpent, time;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	
	public Timer(Task task, Calendar startDate, Calendar endDate, int time) {
		this.task = task;
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
	
	public boolean isToday(Calendar today) {
		return startDate.compareTo(today) <= 0 && endDate.compareTo(today) >= 0;
	}
	
	public String toString(){
		return task.getName() + " from " + df.format(startDate.getTime()) + " to " + df.format(endDate.getTime());
	}

	public Task getTask() {
		return task;
	}
	
	public int getTimeSpent(){
		return timeSpent;
	}
	
	public void setTimeSpent(int timeSpent){
		timeSpent = timeSpent;
	}
}
