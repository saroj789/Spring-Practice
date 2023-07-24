package com.springcore.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
//@ComponentScan(basePackages =  "com.springcore.javaconfig")   //if you are using Component annotation  then use this.  not when bean annotation are used.
public class JavaConfig {
	
	@Bean
	public Samosa getSamosa() {
		Samosa s = new Samosa();
		return s;
	}
	
	
	@Bean(name= {"student","getStudent"})         //bean will be created by method name if not given
	public Student getStudent() {   
		//creating new Student object
		Student st = new Student(getSamosa());
		return st;
	}
	
	

}
