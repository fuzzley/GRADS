package edu.sc.csce740.model;

public class StudentRecord {
	Student student;
	String department;
	Term termBegan;
	Degree degreeSought;
	Degree certificateSought;
	Degree[] previousDegrees;
	Professor[] advisors;
	Professor[] committee;
	CourseTaken[] coursesTaken;
	Milestone[] milestoneSet;
	String[] notes;
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Term getTermBegan() {
		return termBegan;
	}
	public void setTermBegan(Term termBegan) {
		this.termBegan = termBegan;
	}
	public Degree getDegreeSought() {
		return degreeSought;
	}
	public void setDegreeSought(Degree degreeSought) {
		this.degreeSought = degreeSought;
	}
	public Degree getCertificateSought() {
		return certificateSought;
	}
	public void setCertificateSought(Degree certificateSought) {
		this.certificateSought = certificateSought;
	}
	public Degree[] getPreviousDegrees() {
		return previousDegrees;
	}
	public void setPreviousDegrees(Degree[] previousDegrees) {
		this.previousDegrees = previousDegrees;
	}
	public Professor[] getAdvisors() {
		return advisors;
	}
	public void setAdvisors(Professor[] advisors) {
		this.advisors = advisors;
	}
	public Professor[] getCommittee() {
		return committee;
	}
	public void setCommittee(Professor[] committee) {
		this.committee = committee;
	}
	public CourseTaken[] getCoursesTaken() {
		return coursesTaken;
	}
	public void setCoursesTaken(CourseTaken[] coursesTaken) {
		this.coursesTaken = coursesTaken;
	}
	public Milestone[] getMilestoneSet() {
		return milestoneSet;
	}
	public void setMilestoneSet(Milestone[] milestoneSet) {
		this.milestoneSet = milestoneSet;
	}
	public String[] getNotes() {
		return notes;
	}
	public void setNotes(String[] notes) {
		this.notes = notes;
	}
}
