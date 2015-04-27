package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {

	private Calendar calendar;
	
	public DateServer() {
		this.calendar = new GregorianCalendar();
	}
	
	public Calendar getDate() {
        return calendar.getInstance();
    }
	
}