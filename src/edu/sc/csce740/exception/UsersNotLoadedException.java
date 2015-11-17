package edu.sc.csce740.exception;

public class UsersNotLoadedException extends Exception {
	public UsersNotLoadedException() {
		super("Users were not loaded. Make sure the path is correct and the data is in a valid JSON format.");
	}
}
