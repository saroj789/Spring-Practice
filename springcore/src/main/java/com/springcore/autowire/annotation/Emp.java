package com.springcore.autowire.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Emp { 
	
	@Autowired   //bytype used
	@Qualifier("address2")
	private Address address;
	
	public Emp() {
		super();
	}
	
	//@Autowired    // constructor injection
	public Emp(Address address) {
		super();
		this.address = address;
		System.out.println("Inside constructor");
	}


	public Address getAddress() {
		return address;
	}

	//@Autowired    //setter inkection
	public void setAddress(Address address) {
		System.out.println("Setter Address");
		this.address = address;
	}

	@Override
	public String toString() {
		return "Emp [address=" + address + "]";
	}

	
	
		
	
	
	
	
	
}
