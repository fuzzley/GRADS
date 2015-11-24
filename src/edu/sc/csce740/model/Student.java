package edu.sc.csce740.model;

public class Student {
	private String id;
	private String firstName;
	private String lastName;
	
	// getters and setters
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public Student(String id, String firstName, String lastName){
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}
	
	public Student(){
		this.id = "";
		this.firstName = "";
		this.lastName = "";
	}
	
}
