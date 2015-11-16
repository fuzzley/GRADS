package edu.sc.csce740.model;

public class CourseRequirement {
	private CourseTaken course;
	private Term term;
	private String grade;
	
	public CourseRequirement(CourseTaken course, Term term, String grade){
		this.setCourse(course);
		this.setTerm(term);
		this.setGrade(grade);
	}

	public CourseTaken getCourse() {
		return course;
	}

	public void setCourse(CourseTaken course) {
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
