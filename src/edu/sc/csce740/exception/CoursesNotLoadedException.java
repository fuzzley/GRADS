package edu.sc.csce740.exception;

public class CoursesNotLoadedException extends Exception {
	public CoursesNotLoadedException() {
		super("Courses were not loaded. Make sure the path is correct and the data is in a valid JSON format.");
	}
}
