package com.springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
     // instantiating a IOC Container  
       ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
       Student s = (Student) context.getBean("student1");
       System.out.println(s);
    
    }
}
