package model;

import java.util.LinkedList;
import java.util.List;

public class Schedule {

	List<Employee> users;
	
	public Schedule(){
		users = new LinkedList<Employee>();
	}
	
	public List<Employee> getEmployees() {
		return users;
	}

}
