package edu.sc.csce740.exception;

public class StudentRecordsNotLoadedException extends Exception {
	public StudentRecordsNotLoadedException() {
		super("Student records were not loaded. Make sure the path is correct and the data is in a valid JSON format.");
	}
}
