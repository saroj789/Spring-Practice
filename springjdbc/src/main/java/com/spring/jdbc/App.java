package com.spring.jdbc;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.spring.jdbc.dao.StudentDao;
import com.spring.jdbc.entities.Student;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       System.out.println( "Hello World!" );
        
        //Spring JDBC --> JdbcTemplate
       //ApplicationContext  context = new ClassPathXmlApplicationContext("com/spring/jdbc/config.xml");
       
       //annotation without xml
       ApplicationContext  context = new AnnotationConfigApplicationContext(JavaConfig.class);
       
       StudentDao studentDao = context.getBean("studentDao",StudentDao.class);
       //Student st = new Student(444,"Shubhanshu","Jabalpur");
       
       //INSERT
      // int result = studentDao.insert(st);
       //System.out.println( "number of record inserted..."+result );
       
       //UPDATE
       //Student st2 = new Student(444,"Rahul","Jabalpur");
       //int result2 = studentDao.change(st2);
       //System.out.println( "number of record updated..."+result2 );
       
       
       //DELETE
       //int result3 = studentDao.delete(444);
       //System.out.println( "number of record delete..."+result3 );
       
       //selecting single object
       //Student st3 = studentDao.getStudent(111);
       //System.out.println(st3);
       
     //selecting multiple object
       List<Student> students = studentDao.getAllStudent();
       for (Student s : students) {
    	   System.out.println(s);
       }
       
       
       
       
   /*  //without dao
       JdbcTemplate template =  context.getBean("jdbcTemplate",JdbcTemplate.class) ;
       
       //insert query
       String query = "insert into student(id,name,city) values(?,?,?)";
       
       //fire query
       int result = template.update(query,333,"Apoorva","Mumbai");
       System.out.println( "number of record inserted..."+result );
   */
    }
}
