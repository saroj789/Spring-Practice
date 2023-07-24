package com.springcore.autowire;

public class Address {
	public String city;
	public String country;
	
	
	
	@Override
	public String toString() {
		return "Address [city=" + city + ", country=" + country + "]";
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
