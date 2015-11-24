package edu.sc.csce740.model;

import java.util.List;

public class StudentRecord {
	private Student student;
	private String department;
	private Term termBegan;
	private Degree degreeSought;
	private Degree certificateSought;
	private List<Degree> previousDegrees;
	private List<Professor> advisors;
	private List<Professor> committee;
	private List<CourseTaken> coursesTaken;
	private List<Milestone> milestonesSet;
	private List<String> notes;
	
	/**
	 * 
	 * @param student
	 * @param department
	 * @param termBegan
	 * @param degreeSought
	 * @param certificateSought
	 * @param previousDegrees
	 * @param advisors
	 * @param committee
	 * @param coursesTaken
	 * @param milestonesSet
	 * @param notes
	 */
	public StudentRecord(Student student, String department, Term termBegan, Degree degreeSought,
			Degree certificateSought, List<Degree>previousDegrees, List<Professor>advisors,
			List<Professor>committee, List<CourseTaken>coursesTaken, List<Milestone>milestonesSet, List<String> notes){
		this.setStudent(student);
		this.setDepartment(department);
		this.setTermBegan(termBegan);
		this.setDegreeSought(degreeSought);
		this.setCertificateSought(certificateSought);
		this.setPreviousDegrees(previousDegrees);
		this.setCoursesTaken(coursesTaken);
		this.setMilestonesSet(milestonesSet);
		this.setNotes(notes);
	}
	
	public StudentRecord() {
		
	}

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

	public List<Degree> getPreviousDegrees() {
		return previousDegrees;
	}

	public void setPreviousDegrees(List<Degree> previousDegrees) {
		this.previousDegrees = previousDegrees;
	}

	public List<Professor> getAdvisors() {
		return advisors;
	}

	public void setAdvisors(List<Professor> advisors) {
		this.advisors = advisors;
	}

	public List<Professor> getCommittee() {
		return committee;
	}

	public void setCommittee(List<Professor> committee) {
		this.committee = committee;
	}

	public List<CourseTaken> getCoursesTaken() {
		return coursesTaken;
	}

	public void setCoursesTaken(List<CourseTaken> coursesTaken) {
		this.coursesTaken = coursesTaken;
	}

	public List<Milestone> getMilestonesSet() {
		return milestonesSet;
	}

	public void setMilestonesSet(List<Milestone> milestonesSet) {
		this.milestonesSet = milestonesSet;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	
}
