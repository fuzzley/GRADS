package edu.sc.csce740.module;

/**
 * 
 * Session module
 *
 */
public class Session {
	private String userId;
	
	/**
	 * Set the currently logged in user id.
	 * @param id
	 */
	public void setUser(String id) {
		userId = id;
	}
	
	/**
	 * Get the currently logged in user id.
	 * @return Currently logged in user id.
	 */
	public String getUser() {
		return userId;
	}
	
	/**
	 * Clear out the currently logged in user id.
	 */
	public void clearSession() {
		userId = null;
	}
}
