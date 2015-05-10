package model;

public class Address {

	private String street, city;
	private int streetNumber, zipCode;
	
	public Address(String street, int streetNumber, int zipCode, String city) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}
}
