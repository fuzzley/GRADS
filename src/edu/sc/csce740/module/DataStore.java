package edu.sc.csce740.module;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;

import edu.sc.csce740.model.*;

public class DataStore {
	/**
	 * Load a list of courses from the file at the location provided by "fileName"
	 * @param fileName The path of the courses file.
	 */
	public static void loadCourses(String fileName) {
		String coursesStr = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    coursesStr = sb.toString();
		    br.close();
		} catch (Exception ex) {
			//throw exception
		}
		
		if (coursesStr == null || "".equals(coursesStr)) {
			//throw exception
		}
		
		Gson gson = new Gson();
		courses = gson.fromJson(coursesStr, CourseTaken[].class);
	}

	/**
	 * Load a list of student records from the file at the location provided by "fileName"
	 * @param fileName The path of the student records file.
	 */
	public static void loadStudentRecords(String fileName) {
		String recordsStr = null;
		BufferedReader br = null;
		try {
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
		} catch (Exception ex) {
			//throw exception
		}
		
		if (recordsStr == null || "".equals(recordsStr)) {
			//throw exception
		}
		
		Gson gson = new Gson();
		studentRecords = gson.fromJson(recordsStr, StudentRecord[].class);
	}

	/**
	 * Load a list of users from the file at the location provided by "fileName"
	 * @param fileName The path of the users file.
	 */
	public static void loadUsers(String fileName) {
		String usersStr = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    usersStr = sb.toString();
		    br.close();
		} catch (Exception ex) {
			//throw exception
		}
		
		if (usersStr == null || "".equals(usersStr)) {
			//throw exception
		}
		
		Gson gson = new Gson();
		users = gson.fromJson(usersStr, User[].class);
	}
	
	public static StudentRecord[] studentRecords;
	public static CourseTaken[] courses;
	public static User[] users;
}
