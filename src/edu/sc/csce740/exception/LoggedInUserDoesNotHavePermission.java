package edu.sc.csce740.exception;

public class LoggedInUserDoesNotHavePermission extends Exception {
	public LoggedInUserDoesNotHavePermission() {
		super("The logged in user does not have permission to perform this operation.");
	}
}
