package edu.sc.csce740.model;

public class Professor {
	private String department;
	private String firstName;
	private String lastName;
	
	/**
	 * 
	 * @param department
	 * @param firstName
	 * @param lastName
	 */
	public Professor(String department, String firstName, String lastName){
		this.setDepartment(department);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
