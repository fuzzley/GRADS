package edu.sc.csce740.exception;

public class StudentRecordNotFoundException extends Exception {
	public StudentRecordNotFoundException() {
		super("Student record was not found. Make sure the provided id is valid.");
	}
}
