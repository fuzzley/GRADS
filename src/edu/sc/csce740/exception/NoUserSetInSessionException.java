package edu.sc.csce740.exception;

public class NoUserSetInSessionException extends Exception {
	public NoUserSetInSessionException() {
		super("No user is set in the login session.");
	}
}
