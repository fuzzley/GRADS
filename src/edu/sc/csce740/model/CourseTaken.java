package edu.sc.csce740.model;

public class CourseTaken {
	private Course course;
	private Term term;
	private String grade;
	
	/**
	 * 
	 * @param course
	 * @param term
	 * @param grade
	 */
	public CourseTaken(Course course, Term term, String grade){
		this.setCourse(course);
		this.setTerm(term);
		this.setGrade(grade);
	}
	
	public CourseTaken(){
		this.course = null;
		this.term = null;
		this.grade = null;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
