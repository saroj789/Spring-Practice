package fom.springcore.stereotype;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


//@Component
@Component("student1")
@Scope("prototype")   //to change SCOPE   // use this for xml <bean class="" name="" scope="prototype" />
public class Student {
	@Value("Saroj")
	private String studentName;
	@Value("Bhopal")
	private String city;
	
	@Value("#{temp}")     //spring expression language, getting from xml 
	public List<String> address;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", city=" + city + ", address=" + address + "]";
	}
	
	
	
	
	
	

}
