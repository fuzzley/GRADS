package edu.sc.csce740.exception;

public class StudentRecordNotFound extends Exception {
	public StudentRecordNotFound() {
		super("Student record was not found. Make sure the provided id is valid.");
	}
}
