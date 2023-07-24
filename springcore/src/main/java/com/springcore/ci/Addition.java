package com.springcore.ci;

public class Addition {
	private int a;
	private int b;
	
	public Addition(int a, int b) {
		super();
		this.a = a;
		this.b = b;
		System.out.println("int, int");
	}
	
	public Addition(float a, float b) {
		super();
		this.a = (int) a;
		this.b = (int) b;
		System.out.println("float, float");
	}
	
	public Addition(String a, String b) {
		super();
		this.a =Integer.parseInt(a);
		this.b = Integer.parseInt(b);
		System.out.println("String, String");
	}
	
	public void doSum() {
		System.out.println("Sum is "+ (this.a+this.b));
	}

	@Override
	public String toString() {
		return "Addition [a=" + a + ", b=" + b + "]";
	}
	
	


}
