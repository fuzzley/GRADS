package edu.sc.csce740.exception;

public class LoggedInUserDoesNotHavePermissionException extends Exception {
	public LoggedInUserDoesNotHavePermissionException() {
		super("The logged in user does not have permission to perform this operation.");
	}
}
