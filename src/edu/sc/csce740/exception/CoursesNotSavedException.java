package edu.sc.csce740.exception;

public class CoursesNotSavedException extends Exception {
	public CoursesNotSavedException() {
		super("Courses were not saved to the specified file.");
	}
}
