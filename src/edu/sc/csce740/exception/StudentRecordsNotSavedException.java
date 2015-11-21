package edu.sc.csce740.exception;

public class StudentRecordsNotSavedException extends Exception {
	public StudentRecordsNotSavedException() {
		super("Student records were not saved to the specified file.");
	}
}
