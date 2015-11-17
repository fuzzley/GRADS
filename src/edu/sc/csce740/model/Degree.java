package edu.sc.csce740.model;

public class Degree {
	private String name;
	private Term graduation;
	
	public Degree(String name, Term graduation){
		this.setName(name);
		this.setGraduation(graduation);
	}
	
	public Degree(){
		this.name = "";
		this.graduation = new Term();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Term getGraduation() {
		return graduation;
	}

	public void setGraduation(Term graduation) {
		this.graduation = graduation;
	}
}
