package com.springcore.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/springcore/spel/seplconfig.xml");
		Demo d = context.getBean("demo",Demo.class);
		System.out.println(d);
	
		
		
		/*
		SpelExpressionParser sp =new SpelExpressionParser();
		Expression ex =  sp.parseExpression("7+9");
		System.out.println(ex.getValue());
		*/
	}

}
