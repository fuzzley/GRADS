package edu.sc.csce740.model;

import java.util.List;

public class RequirementDetail {
	private String gpa;
	private boolean passed;
	private List<CourseTaken> courses;
	private List<Milestone> milestones;
	private List<String> notes;
	
	public RequirementDetail(String gpa, boolean passed, List<CourseTaken> courses,
			List<Milestone> milestones, List<String> notes){
		this.setGpa(gpa);
		this.setPassed(passed);
		this.setCourses(courses);
		this.setMilestones(milestones);
		this.setNotes(notes);
	}

	public String getGpa() {
		return gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public List<CourseTaken> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseTaken> courses) {
		this.courses = courses;
	}

	public List<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}


}
