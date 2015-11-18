package edu.sc.csce740.module;

public class Session {
	private static String userId;
	
	/**
	 * Set the currently logged in user id.
	 * @param id
	 */
	public static void setUser(String id) {
		userId = id;
	}
	
	/**
	 * Get the currently logged in user id.
	 * @return Currently logged in user id.
	 */
	public static String getUser() {
		return userId;
	}
	
	/**
	 * Clear out the currently logged in user id.
	 */
	public static void clearSession() {
		userId = null;
	}
}
