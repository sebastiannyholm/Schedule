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
//		
//		int timeInDay = startDate.get(Calendar.HOUR_OF_DAY)*60 + startDate.get(Calendar.MINUTE) - 8*60;
//		
//		setDays(time + timeInDay);
//		setHours(time + timeInDay);
//		setMinutes(time + timeInDay);
//		
//		this.endDate = new GregorianCalendar();
//		this.endDate.setTime(startDate.getTime());
//		this.endDate.add(Calendar.DAY_OF_YEAR, days);
//		this.endDate.set(Calendar.HOUR_OF_DAY, hours + 8);
//		this.endDate.set(Calendar.MINUTE, minutes + 0);
//		
//		skipWeekend(endDate);
		
		agenda();
	}
	
	private void skipWeekend(Calendar endDate) {
		if (endDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			endDate.add(Calendar.DAY_OF_YEAR, 2);
		else if (endDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			endDate.add(Calendar.DAY_OF_YEAR, 2);
	}
	
	private void setDays(int time) {
		if (time >= 480) {
			days++;
			setDays(time-480);
		}
	}
	
	private void setHours(int time) {
		if ((time % 480) >= 60) {
			hours++;
			setHours(time-60);
		}
	}
	
	private void setMinutes(int time) {
		minutes = (time % 60);
	}
	
	private void agenda() {
		System.out.println(df.format(startDate.getTime()));
		System.out.println(df.format(endDate.getTime()));
		System.out.println();
	}
	
//	public Calendar getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Calendar startDate) {
//		this.startDate = startDate;
//	}

	public Calendar getEndDate() {
		return endDate;
	}

//	public void setEndDate(Calendar endDate) {
//		this.endDate = endDate;
//	}
	
	public int getTimeForATask() {
		return time;
	}
	
}
