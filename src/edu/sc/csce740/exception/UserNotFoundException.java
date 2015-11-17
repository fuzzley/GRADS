package edu.sc.csce740.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException() {
		super("The user matching the provided userId cannot be found in the database.");
	}
}
