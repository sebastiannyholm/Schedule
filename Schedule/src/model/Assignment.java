package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Timer;

public class Assignment implements ActionListener {

	private Task task;
	private Calendar startDate, endDate;
	private int time, registeredTime, hours, minutes, seconds;
	private Timer timer;
	private String registeredTimeString = "00:00:00", hoursString = "00", minutesString = "00", secondsString = "00", description = ""; 
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	
	public Assignment(Task task, Calendar startDate, Calendar endDate, int time) {
		this.task = task;
		this.startDate = new GregorianCalendar();
		this.startDate.setTime(startDate.getTime());
		
		this.endDate = new GregorianCalendar();
		this.endDate.setTime(endDate.getTime());
		
		this.time = time;
		
		this.timer = new Timer(1000, this);
		
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public int getTime() {
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
	
	public int getRegisteredTime(){
		return registeredTime;
	}
	
	public void setRegisteredTime(int timeSpent){
		this.registeredTime = timeSpent;
		setCorrectFormat();
	}

	public boolean limitExceeded() {
		return registeredTime > time*60;
	}

	public int getRegisteredHour() {
		return (registeredTime / (60*60));
	}

	public int getRegisteredMinutes() {
		return (registeredTime / 60) % 60;
	}

	public int getRegisteredSeconds() {
		return registeredTime % 60;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		registeredTime++;
		setCorrectFormat();
	}
	
	private void setCorrectFormat() {
		hours = getRegisteredHour();
		minutes = getRegisteredMinutes();
		seconds = getRegisteredSeconds();
		
		if (hours < 10)
			hoursString = "0" + hours;
		else 
			hoursString = Integer.toString(hours);
		 
		if (minutes < 10)
			minutesString = "0" + minutes;
		else 
			minutesString = Integer.toString(minutes);
		
		if (seconds < 10)
			secondsString = "0" + seconds;
		else 
			secondsString = Integer.toString(seconds);
		
		registeredTimeString = hoursString + ":" + minutesString + ":" + secondsString;
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}

	public Timer getTimer() {
		return timer;
	}
	
	public String getRegisteredHoursString() {
		return hoursString;
	}
	
	public String getRegisteredMinutesString() {
		return minutesString;
	}
	
	public String getRegisteredSecondsString() {
		return secondsString;
	}
	
	public String getRegisteredTimeString() {
		return registeredTimeString;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
