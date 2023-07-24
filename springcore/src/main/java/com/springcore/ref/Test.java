package com.springcore.ref;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {
	
	  public static void main( String[] args )
	    {
	        System.out.println( "Hello World!" );
	        
	     // instantiating a IOC Container  
	       ApplicationContext context = new ClassPathXmlApplicationContext("com/springcore/ref/refconfig.xml");
	       A a = (A) context.getBean("aref");
	       
	       System.out.println(a.getX() );
	       System.out.println( a.getOb().getY() );
	       
	    
	    }

}
