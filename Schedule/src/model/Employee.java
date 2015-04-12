package model;

public class Employee {

	private String initials, name;
	private int age;
	private Address address;
	
	public Employee(String name, String initials, int age, Address address) {
		this.name = name;
		this.initials = initials;
		this.age = age;
		this.address = address;
	}
	
	public String getInitials() {
		return initials;
	}
	
}
