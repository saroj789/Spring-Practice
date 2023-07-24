package com.springcore.lifecycle;

import javax.swing.AbstractAction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ApplicationContext context =new ClassPathXmlApplicationContext("com/springcore/lifecycle/lifecycleconfig.xml");
		AbstractApplicationContext context =new ClassPathXmlApplicationContext("com/springcore/lifecycle/lifecycleconfig.xml");
		//Samosa s1 = (Samosa) context.getBean("s1");
		//System.out.println(s1);
		
		//registry shutdownhook in AbstractApplicationContext interface
		context.registerShutdownHook();
		
		System.out.println("---------------------------------------");
		
		Pepsi p1= (Pepsi) context.getBean("p1");
		System.out.println(p1);

	}

}
