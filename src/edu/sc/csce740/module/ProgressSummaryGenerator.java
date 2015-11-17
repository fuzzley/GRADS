package edu.sc.csce740.module;

import java.util.ArrayList;
import java.util.List;

import edu.sc.csce740.model.*;
import edu.sc.csce740.module.DataStore;

/*
 * Parameters for StudentRecord
 *  private Student student;
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
	
	Parameters for Summary
	private Student student;
	private String department;
	private Term termBegan;
	private Degree degreeSought;
	private Degree certificateSought;
	private List<Professor> advisors;
	private List<Professor> committee;
	private List<RequirementCheck> requirementCheckResults;
 */

public class ProgressSummaryGenerator {
	public static ProgressSummary generateProgressSummary(String studentId){
		StudentRecord record = DataStore.getTranscript(studentId);
		
		
		ProgressSummary ps = new ProgressSummary(record.getStudent(),
				record.getDepartment(), record.getTermBegan(),record.getDegreeSought(), 
				record.getCertificateSought(), record.getAdvisors(), record.getCommittee(), new ArrayList<RequirementCheck>());
		return ps;
	}
	
	public static ProgressSummary simulateCourses(String studentId, List<CourseTaken> courses){
		ProgressSummary ps = new ProgressSummary();
		return ps;
	}
}
