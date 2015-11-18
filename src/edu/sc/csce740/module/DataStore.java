package edu.sc.csce740.module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import edu.sc.csce740.exception.*;
import edu.sc.csce740.model.*;

public class DataStore {
	public static List<StudentRecord> studentRecords;
	public static List<Course> courses;
	public static List<User> users;
	
	/**
	 * Load a list of courses from the file at the location provided by "fileName"
	 * @param fileName The path of the courses file.
	 */
	public static void loadCourses(String fileName) throws CoursesNotLoadedException {
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
			throw new CoursesNotLoadedException();
		}
		
		if (coursesStr == null || "".equals(coursesStr)) {
			throw new CoursesNotLoadedException();
		}
		
		Gson gson = new Gson();
		try {
			courses = Arrays.asList(gson.fromJson(coursesStr, Course[].class));
		} catch (Exception ex) {
			throw new CoursesNotLoadedException();
		}
	}

	/**
	 * Load a list of student records from the file at the location provided by "fileName"
	 * @param fileName The path of the student records file.
	 */
	public static void loadRecords(String fileName) throws StudentRecordsNotLoadedException {
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
			throw new StudentRecordsNotLoadedException();
		}
		
		if (recordsStr == null || "".equals(recordsStr)) {
			throw new StudentRecordsNotLoadedException();
		}
		
		Gson gson = new Gson();
		try {
			studentRecords = Arrays.asList(gson.fromJson(recordsStr, StudentRecord[].class));
		} catch (Exception ex) {
			throw new StudentRecordsNotLoadedException();
		}
	}

	/**
	 * Load a list of users from the file at the location provided by "fileName"
	 * @param fileName The path of the users file.
	 */
	public static void loadUsers(String fileName) throws UsersNotLoadedException {
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
			throw new UsersNotLoadedException();
		}
		
		if (usersStr == null || "".equals(usersStr)) {
			throw new UsersNotLoadedException();
		}
		
		Gson gson = new Gson();
		try {
			users = Arrays.asList(gson.fromJson(usersStr, User[].class));
		} catch (Exception ex) {
			throw new UsersNotLoadedException();
		}
	}

	/**
	 * Return a list of student ids based on the student records loaded in memory.
	 * @return List of student ids.
	 */
	public static List<String> getStudentIDs() {
		List<String> studentIDs = new ArrayList<String>();
		
		for (StudentRecord record : studentRecords) {
			if (record.getStudent() != null) {
				studentIDs.add(record.getStudent().getId());
			}
		}
		
		return studentIDs;
	}
	
	/**
	 * Add a note to a student's record.
	 * @param userId The id of the student for whom the note is intended.
	 * @param note The note to be added to the student's record.
	 * @param permanent Indicates whether the change is permanent or temporary.
	 * @throws StudentRecordNotFoundException
	 */
	public static void addNote(String userId, String note, boolean permanent) throws StudentRecordNotFoundException {
		StudentRecord record = getTranscript(userId);
		if (record == null) {
			throw new StudentRecordNotFoundException();
		} //else
		
		List<String> notes = record.getNotes();
		if (notes == null) {
			//create a new empty slot
			notes = new ArrayList<String>();
		} else {
			notes.add(note);
		}
		record.setNotes(notes);
		
		if (permanent) {
			//TODO: save student records
		}
	}
	
	/**
	 * Returns the User object associated with the given user id.
	 * @param userId The user id to search for.
	 * @return Returns the User that matches the given user id.
	 */
	public static User getPermissionByUserId(String userId) {
		if (users == null) {
			return null;
		} //else
		
		for (User user : users) {
			String id = user.getId();
			if (id != null && id.equals(userId)) {
				return user;
			}
		} //else
		
		return null;
	}

	/**
	 * Returns the StudentRecord object associated with the given user id.
	 * @param userId The user id to search for.
	 * @return Returns the StudentRecord that matches the given user id.
	 */
	public static StudentRecord getTranscript(String userId) {
		if (studentRecords == null) {
			return null;
		} //else
		
		for (StudentRecord record : studentRecords) {
			Student student = record.getStudent();
			if (student != null) {
				String id = student.getId();
				if (id != null && id.equals(userId)) {
					return record;
				}
			}
		} //else
		
		return null;
	}

	/**
	 * Update a StudentRecord of an existing student.
	 * @param userId The id of the student to whom the student record belongs.
	 * @param transcript The new StudentRecord that will replace the old one.
	 * @param permanent Indicates whether the change is permanent or temporary.
	 * @throws StudentRecordNotFoundException
	 */
	public static void updateTranscript(String userId, StudentRecord transcript, boolean permanent) throws StudentRecordNotFoundException {
		StudentRecord record = getTranscript(userId);
		if (record == null) {
			throw new StudentRecordNotFoundException();
		} //else
		
		int indexOfRecord = studentRecords.indexOf(record);
		if (indexOfRecord < 0) {
			throw new StudentRecordNotFoundException();
		} //else
		
		//overwrite existing transcript
		studentRecords.set(indexOfRecord, transcript);;
		
		if (permanent) {
			//TODO: save student records
		}
	}
}
