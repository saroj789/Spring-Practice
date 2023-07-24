package com.springcore.standalone.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  ApplicationContext context = new ClassPathXmlApplicationContext("com/springcore/Standalone/collection/aloneconfig.xml");
		 Person p1 = context.getBean("p1",Person.class);
		 
		 System.out.println(p1);
		 System.out.println(p1.getFriends());
		 System.out.println(p1.getFriends().getClass().getName());
		 System.out.println(p1.getFeeStructure());
		 System.out.println(p1.getFeeStructure().getClass().getName());

	}

}
