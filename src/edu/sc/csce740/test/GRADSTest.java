package edu.sc.csce740.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.sc.csce740.GRADS;

public class GRADSTest {

	private static String coursesPath = "src/resources/courses.json";
	private static String studentRecordsPath = "src/resources/students.json";
	private static String usersPath = "src/resources/users.json";
	
	@Test
	public void testSetUserValid() {
		GRADS grads = new GRADS();

		try {
			grads.loadCourses(coursesPath);
			grads.loadRecords(studentRecordsPath);
			grads.loadUsers(usersPath);
			
			grads.setUser("ggay");
		} catch (Exception ex) { }
		
		assert(grads.getUser() == "ggay");
	}

	@Test
	public void testSetUserInvalid() {
		GRADS grads = new GRADS();
		
		try {
			grads.loadCourses(coursesPath);
			grads.loadRecords(studentRecordsPath);
			grads.loadUsers(usersPath);
			
			grads.setUser("gggay");
		} catch (Exception ex) { }
		
		assert(grads.getUser() == null);
	}

}
