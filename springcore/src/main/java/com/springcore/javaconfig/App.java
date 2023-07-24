package com.springcore.javaconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springcore.javaconfig.Student;

public class App {

	public static void main(String[] args) {
		
		// for annotation based config(totaly)
		
		ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
		//Student st = context.getBean("firststudent",Student.class);
		Student st = context.getBean("student",Student.class);
		System.out.println(st);
		st.study();
		
		
		
		
		
		
	/*  // for xml
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/springcore/javaconfig/config.xml");
		Student st = context.getBean("firststudent",Student.class);
		System.out.println(st);
		context.close();
		
	*/
		

	}

}
