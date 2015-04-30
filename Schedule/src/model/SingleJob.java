package model;

import java.util.Calendar;

public class SingleJob {

	Calendar startDate, endDate;
	int time;
	boolean jobDone = false;
	
	public SingleJob (Calendar startDate, Calendar endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.time = endDate.get(Calendar.HOUR_OF_DAY) - startDate.get(Calendar.HOUR_OF_DAY);
	}
	
	public void setJobDone(boolean jobDone) {
		this.jobDone = jobDone;
	}
	
	public int getTime() {
		return time;
	}
	
}
