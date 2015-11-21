package edu.sc.csce740.exception;

public class UsersNotSavedException extends Exception {
	public UsersNotSavedException() {
		super("Users were not saved to the specified file.");
	}
}
