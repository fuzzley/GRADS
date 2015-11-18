package edu.sc.csce740.module;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
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
		List<RequirementCheck> requirementCheckResults = checkPhD(record);
		
		ProgressSummary ps = new ProgressSummary(record.getStudent(),
				record.getDepartment(), record.getTermBegan(),record.getDegreeSought(), 
				record.getCertificateSought(), record.getAdvisors(), record.getCommittee(), requirementCheckResults);

		return ps;
	}
	
	public static ProgressSummary simulateCourses(String studentId, List<CourseTaken> courses){
		ProgressSummary ps = new ProgressSummary();
		return ps;
	}
	
	//gpa calculation
	private static float getGpa(List<CourseTaken>courses){
		float gpa = 0; float credits = 0; float gradesTimesCredits = 0; int credit = 0; int grade = 0;
		for(int i=0; i<courses.size();i++){
			credit = Integer.parseInt(courses.get(i).getCourse().getNumCredits());
			
			if (courses.get(i).getGrade().equalsIgnoreCase("A")){
				grade = 4;
			}
			else if (courses.get(i).getGrade().equalsIgnoreCase("B")){
				grade = 3;
			}
			else if (courses.get(i).getGrade().equalsIgnoreCase("C")){
				grade = 2;
			}
			else if (courses.get(i).getGrade().equalsIgnoreCase("D")){
				grade = 1;
			}
			gradesTimesCredits += grade*credit;
			credits += credit;
		}
		gpa = gradesTimesCredits / credits;
		return gpa;
	}
	/*
	 * hard code graduation rules
	 * one of the concern is reuse issue, here every time, several new hashset will be created
	 */
	private static List<RequirementCheck> checkPhD(StudentRecord record){
		//current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//hard code the core courses
		HashSet<String> coreCourseSet = new HashSet<String>();
		coreCourseSet.add("csce513"); coreCourseSet.add("csce531"); coreCourseSet.add("csce551"); coreCourseSet.add("csce750");coreCourseSet.add("csce791");
		
		//hard code the courses which is not count as additional credits
		HashSet<String> nonAdditionalCourses = new HashSet<String>();
		nonAdditionalCourses.add("csce899"); nonAdditionalCourses.add("csce799"); nonAdditionalCourses.add("csce750");nonAdditionalCourses.add("csce791");
		
		//hard code the courses which is not count as degree based credits
		HashSet<String> nonDegreeCourses = new HashSet<String>();
		nonDegreeCourses.add("csce899"); nonDegreeCourses.add("csce799");
		
		//hard code possible master's degree
		HashSet<String> master = new HashSet<String>();
		master.add("MS"); master.add("MENG"); master.add("MSE");
		
		//hard code milestone set
		HashSet<String> milestones = new HashSet<String>();
		milestones.add("DISSERTATION_ADVISOR_SELECTED");
		milestones.add("PROGRAM_OF_STUDY_SUBMITTED");
		milestones.add("DISSERTATION_COMMITTEE_FORMED");
		milestones.add("QUALIFYING_EXAM_PASSED");
		milestones.add("DISSERTATION_PROPOSAL_SCHEDULED");
		milestones.add("COMPREHENSIVE_EXAM_PASSED");
		milestones.add("DISSERTATION_SUBMITTED");
		milestones.add("DISSERTATION_DEFENSE_SCHEDULED");
		milestones.add("DISSERTATION_DEFENSE_PASSED");
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		
		//coreCourses requirement, get the num of credits of 899 at the same time
		int credits899 = 0;
		List<CourseTaken> course899 = new ArrayList<CourseTaken>();
		
		RequirementCheck coreCourses = new RequirementCheck("CORE_COURSES_PHD");
		List<CourseTaken> coreCoursesTaken = new ArrayList<CourseTaken>();
		List<CourseTaken> otherCoursesTaken = new ArrayList<CourseTaken>();
		for(int i=0; i<record.getCoursesTaken().size();i++){
			//courses become invalid after 6 years, the valid course must be above 700
			if (year - Integer.parseInt(record.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				if(coreCourseSet.contains(record.getCoursesTaken().get(i).getCourse().getId())){
					coreCoursesTaken.add(record.getCoursesTaken().get(i));
				}else{
					otherCoursesTaken.add(record.getCoursesTaken().get(i));
					if(record.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce899")){
						course899.add(record.getCoursesTaken().get(i));
						credits899 = Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					}
				}
			}
		}
		if (coreCoursesTaken.size() == 4){
			coreCourses.setPassed(true);
		}
		coreCourses.getDetails().setCourses(coreCoursesTaken);
		requirementCheckResults.add(coreCourses);
		
		//additional credits
		RequirementCheck additionalCredits = new RequirementCheck("ADDITIONAL_CREDITS_PHD");
		List<CourseTaken> validAdditionalCoursesTaken = new ArrayList<CourseTaken>();
		int credits = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(!nonAdditionalCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				//the valid course must be above 700
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700){
					credits += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					validAdditionalCoursesTaken.add(otherCoursesTaken.get(i));
				}
			}
		}
		if(credits >= 20){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setCourses(validAdditionalCoursesTaken);
		requirementCheckResults.add(additionalCredits);
		
		//degree based credits
		RequirementCheck degreeBasedCredits = new RequirementCheck("DEGREE_BASED_CREDITS_PHD");
		List<CourseTaken> validDegreeCoursesTaken = new ArrayList<CourseTaken>();
		
		int credits500 = 0; int credits700 = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(!nonDegreeCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700){
					credits500 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					credits700 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					validDegreeCoursesTaken.add(otherCoursesTaken.get(i));
				}else{
					credits500 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}
			}
		}
		for (int i=0; i<record.getPreviousDegrees().size();i++){
			if(master.contains(record.getPreviousDegrees().get(i).getName())){
				if (credits700 >= 24){
					degreeBasedCredits.setPassed(true);
				}
			}
			else if(credits700 > 24 && credits500 >= 48){
				degreeBasedCredits.setPassed(true);
			}
		}
		degreeBasedCredits.getDetails().setCourses(validDegreeCoursesTaken);
		requirementCheckResults.add(degreeBasedCredits);
		
		//thesis credits
		RequirementCheck thesisCredits = new RequirementCheck("THESIS_CREDITS_PHD");
		if(credits899 >= 12){
			thesisCredits.setPassed(true);
		}
		thesisCredits.getDetails().setCourses(course899);
		requirementCheckResults.add(thesisCredits);
		
		//time limit
		RequirementCheck timeRequirement = new RequirementCheck("TIME_LIMIT_PHD");
		if(Integer.parseInt(record.getTermBegan().getYear()) <= 8){
			timeRequirement.setPassed(true);
		}
		List<String> timeNotes = new ArrayList<String>();
		timeNotes.add("A student must complete all degree requirements within a period of eight years after being admitted to the program as a regular student.");
		timeRequirement.getDetails().setNotes(timeNotes);
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = new RequirementCheck("GPA");
		if(getGpa(record.getCoursesTaken()) > 3){
			gpaRequirement.setPassed(true);
		}
		List<String> gpaNotes = new ArrayList<String>();
		gpaNotes.add("For graduation a student must have a 3.0 GPA on all graduate courses taken and all courses taken at the 700 level or above.");
		gpaRequirement.getDetails().setNotes(gpaNotes);
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = new RequirementCheck("MILESTONES_PHD");
		for(int i=0; i<record.getMilestonesSet().size();i++){
			milestones.remove(record.getMilestonesSet().get(i).getMilestone());
		}
		if(milestones.isEmpty()){
			milestoneRequirement.setPassed(true);
		}
		milestoneRequirement.getDetails().setMilestones(record.getMilestonesSet());
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
}
