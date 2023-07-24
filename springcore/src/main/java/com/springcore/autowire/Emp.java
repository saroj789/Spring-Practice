package com.springcore.autowire;

public class Emp { 
	private Address address;
	
	public Emp() {
		super();
	}
	
	public Emp(Address address) {
		super();
		this.address = address;
		System.out.println("Inside constructor");
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		System.out.println("Setter Address");
		this.address = address;
	}

	@Override
	public String toString() {
		return "Emp [address=" + address + "]";
	}

	
	
		
	
	
	
	
	
}
