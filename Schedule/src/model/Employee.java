package model;

public class Employee {

	private String initials, name;
	private int age;
	private Address address;
	
	public Employee(String initials, String name, int age, Address address) {
		this.initials = initials;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
}
