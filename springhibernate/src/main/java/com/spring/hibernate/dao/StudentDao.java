package com.spring.hibernate.dao;


import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.spring.hibernate.*;
import com.spring.hibernate.entities.Student;

public class StudentDao {
	
	private HibernateTemplate hibernateTemplate;
	
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}



	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}



	//insert student
	@Transactional
	public int insert(Student student) {
		Integer i = (Integer) this.hibernateTemplate.save(student);
		return i;
	}
	
	
	public Student getStudent(int studentId) {
		Student st = this.hibernateTemplate.get(Student.class,studentId );
		return st;
	}
	
	public List<Student> getAllStudent(int studentId) {
		List<Student> st = this.hibernateTemplate.loadAll(Student.class);
		return st;
	}
	
	@Transactional
	public void deleteStudent(int studentId) {
		Student st = this.hibernateTemplate.get(Student.class,studentId );
		this.hibernateTemplate.delete(st);
	}
	
	
	@Transactional
	public void updateStudent(Student student) {
		this.hibernateTemplate.update(student);
	}
	
	
}