package com.springcore.lifecycle;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AbstractApplicationContext context =new ClassPathXmlApplicationContext("com/springcore/lifecycle/lifecycleconfig.xml");

		Example e1= (Example) context.getBean("e1");
		System.out.println(e1);
		//registry shutdownhook in AbstractApplicationContext interface
		context.registerShutdownHook();
				

	}

}
