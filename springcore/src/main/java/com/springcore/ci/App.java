package com.springcore.ci;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  ApplicationContext context = new ClassPathXmlApplicationContext("com/springcore/ci/ciconfig.xml");
	      Addition add = (Addition) context.getBean("add");
	      add.doSum();
	      System.out.println(add);

	}

}
