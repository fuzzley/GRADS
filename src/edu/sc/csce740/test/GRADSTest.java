package edu.sc.csce740.test;
import java.io.BufferedReader;
import java.io.FileReader;

import edu.sc.csce740.*;
import edu.sc.csce740.exception.NoUserSetInSessionException;
import edu.sc.csce740.exception.UserNotFoundException;
import edu.sc.csce740.model.ProgressSummary;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class GRADSTest {
	private GRADS grads;
	private String csUser = "ggay";
	
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
	
	@Before
	public void setUp() throws UserNotFoundException{
		this.grads = new GRADS();
		this.grads.setUser(this.csUser);
	}
	
	@Test
	public void testGenerateProgressSummary_passedPhD() throws Exception{
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = null;
		String studentId = null;
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(ps, generated);
	}
	
	@Test
	public void testGenerateProgressSummary_notPassedPhD() throws Exception{
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = null;
		String studentId = null;
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(ps, generated);
	}
	
	
	
	@After
	public void tearDown() throws NoUserSetInSessionException{
		this.grads.clearSession();
		this.grads = null;

	}

}
