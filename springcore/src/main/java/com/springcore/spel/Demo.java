package com.springcore.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Demo {
	@Value("#{6+7}")
	private int x;
	@Value("#{7}")
	private int y;
	
	@Value("#{T(java.lang.Math).sqrt(25)}") //static method
	private double z;
	

	//@Value("#{T(java.lang.Math).E}")			//static variable
	@Value("#{T(java.lang.Math).PI}")
	private double p;
	
	@Value("#{new java.lang.String('Saroj')}")		// Passing object using new Object()
	private String name;
	
	@Value("#{4>8}")			// boolean
	private boolean isActive;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getP() {
		return p;
	}
	public void setP(double p) {
		this.p = p;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "Demo [x=" + x + ", y=" + y + ", z=" + z + ", p=" + p + ", name=" + name + ", isActive=" + isActive
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	

}
