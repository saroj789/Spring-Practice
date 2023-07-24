package fom.springcore.stereotype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("fom/springcore/stereotype/stereoconfig.xml");
		Student st = context.getBean("student1",Student.class);
		//System.out.println(st);
		//System.out.println(st.getAddress().getClass().getName());

		System.out.println(st.hashCode());
		
		Student st2 = context.getBean("student1",Student.class);
		System.out.println(st2.hashCode());   // singlton bean return same obj(ref) everytime we call
		
		System.out.println("_____________________________________________________");
		//to change SCOPE singlton to prototype, so when we get bean, everytime it will return new object
		//<bean class="" name="" scope="prototype" />
		//@Scope("prototype")
		
		Student st3 = context.getBean("st3",Student.class);
		System.out.println(st3.hashCode());
		Student st4 = context.getBean("st3",Student.class);
		System.out.println(st4.hashCode());
	}

}
