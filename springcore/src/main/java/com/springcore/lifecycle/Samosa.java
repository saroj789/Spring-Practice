package com.springcore.lifecycle;

public class Samosa {
	private double price;
	
	

	public Samosa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Samosa [price=" + price + "]";
	}
	
	public void init() {
		System.out.println("insde Samosa init");
	}
	
	public void destroy() {
		System.out.println("insde Samosa Destroy");
	}
	
	

}
