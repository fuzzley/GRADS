package edu.sc.csce740.model;

import java.util.ArrayList;

public class RequirementDetail {
	private String gpa;
	private boolean passed;
	private ArrayList<CourseRequirement> courses;
	private ArrayList<Milestone> milestones;
	private ArrayList<String> notes;
	
	public RequirementDetail(String gpa, boolean passed, ArrayList<CourseRequirement> courses,
			ArrayList<Milestone> milestones, ArrayList<String> notes){
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

	public ArrayList<CourseRequirement> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<CourseRequirement> courses) {
		this.courses = courses;
	}

	public ArrayList<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(ArrayList<Milestone> milestones) {
		this.milestones = milestones;
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}


}
