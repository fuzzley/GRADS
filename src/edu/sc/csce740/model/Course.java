package edu.sc.csce740.model;

public class Course {
	private String name;
	private String id;
	private String numCredits;
	
	/**
	 * 
	 * @param name
	 * @param id
	 * @param numCredits
	 */
	public Course(String name, String id, String numCredits){
		this.setName(name);
		this.setId(id);
		this.setNumCredits(numCredits);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumCredits() {
		return numCredits;
	}

	public void setNumCredits(String numCredits) {
		this.numCredits = numCredits;
	}
}
