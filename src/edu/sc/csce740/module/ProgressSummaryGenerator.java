package edu.sc.csce740.module;

import java.math.BigDecimal;
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
	//current year
	private static int year = Calendar.getInstance().get(Calendar.YEAR);
	private static int month = Calendar.getInstance().get(Calendar.MONTH);
	
	public static ProgressSummary generateProgressSummary(String studentId){
		StudentRecord record = DataStore.getTranscript(studentId);
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		if(record.getDegreeSought().getName().equalsIgnoreCase("PHD")){
			requirementCheckResults = checkPhD(record);
		}
		else if(record.getDegreeSought().getName().equalsIgnoreCase("MS")){
			requirementCheckResults = checkMS(record);
		}
		else if(record.getDegreeSought().getName().equalsIgnoreCase("MENG")){
			requirementCheckResults = checkMENG(record);
		}
		else if(record.getDegreeSought().getName().equalsIgnoreCase("MSE")){
			requirementCheckResults = checkMSE(record);
		}
		
		if (record.getDegreeSought().getName().equalsIgnoreCase("MSE")){
			List<RequirementCheck> requirementINFAS = checkINFAS(record);
			for(int i=0; i<requirementINFAS.size();i++){
				requirementCheckResults.add(requirementINFAS.get(i));
			}
		}
		
		ProgressSummary ps = new ProgressSummary(record.getStudent(),
				record.getDepartment(), record.getTermBegan(),record.getDegreeSought(), 
				record.getCertificateSought(), record.getAdvisors(), record.getCommittee(), requirementCheckResults);

		return ps;
	}
	
	public static ProgressSummary simulateCourses(String studentId, List<CourseTaken> courses){
		StudentRecord record = DataStore.getTranscript(studentId);
		//adding simulated courses to record
		record.getCoursesTaken().addAll(courses);
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		if(record.getDegreeSought().getName().equalsIgnoreCase("PHD")){
			requirementCheckResults = checkPhD(record);
		}
		else if(record.getDegreeSought().getName().equalsIgnoreCase("MS")){
			requirementCheckResults = checkMS(record);
		}
		else if(record.getDegreeSought().getName().equalsIgnoreCase("MENG")){
			requirementCheckResults = checkMENG(record);
		}
		else if(record.getDegreeSought().getName().equalsIgnoreCase("MSE")){
			requirementCheckResults = checkMSE(record);
		}
		
		if (record.getDegreeSought().getName().equalsIgnoreCase("INFAS")){
			List<RequirementCheck> requirementINFAS = checkINFAS(record);
			for(int i=0; i<requirementINFAS.size();i++){
				requirementCheckResults.add(requirementINFAS.get(i));
			}
		}
		
		ProgressSummary ps = new ProgressSummary(record.getStudent(),
				record.getDepartment(), record.getTermBegan(),record.getDegreeSought(), 
				record.getCertificateSought(), record.getAdvisors(), record.getCommittee(), requirementCheckResults);

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
			else if (courses.get(i).getGrade().equalsIgnoreCase("F")){
				grade = 0;
			}
			else if (courses.get(i).getGrade().equalsIgnoreCase("P")){
				grade = 0;
				credit = 0;
			}
			gradesTimesCredits += grade*credit;
			credits += credit;
		}
		
		gpa = gradesTimesCredits / credits;
		BigDecimal b = new BigDecimal(gpa);  
		return b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	private static RequirementCheck getTimeLimitRequirement(int limit, StudentRecord record, String name){
		RequirementCheck timeRequirement = new RequirementCheck(name);
		if(year - Integer.parseInt(record.getTermBegan().getYear()) <= 8){
			if(record.getTermBegan().getSemester().equalsIgnoreCase("SPRING") && month <= 4){
				timeRequirement.setPassed(true);
			}
			else if(record.getTermBegan().getSemester().equalsIgnoreCase("SUMMBER") && month <= 7){
				timeRequirement.setPassed(true);
			}
			else if(record.getTermBegan().getSemester().equalsIgnoreCase("FALL") && month <= 12){
				timeRequirement.setPassed(true);
			}
			
		}
		List<String> timeNotes = new ArrayList<String>();
		timeNotes.add("A student must complete all degree requirements within a period of eight years after being admitted to the program as a regular student.");
		timeRequirement.getDetails().setNotes(timeNotes);
		return timeRequirement;
	}
	
	private static RequirementCheck getGpaRequirement(StudentRecord record, String name){
		RequirementCheck gpaRequirement = new RequirementCheck(name);
		float current_gpa = getGpa(record.getCoursesTaken());
		gpaRequirement.getDetails().setGpa(Float.toString(current_gpa));
		if(current_gpa > 3){
			gpaRequirement.setPassed(true);
		}
		List<String> gpaNotes = new ArrayList<String>();
		gpaNotes.add("For graduation a student must have a 3.0 GPA on all graduate courses taken and all courses taken at the 700 level or above.");
		gpaRequirement.getDetails().setNotes(gpaNotes);
		return gpaRequirement;
	}
	
	private static RequirementCheck getMilestoneRequirement(HashSet<String> milestones, StudentRecord record, String name){
		RequirementCheck milestoneRequirement = new RequirementCheck(name);
		for(int i=0; i<record.getMilestonesSet().size();i++){
			milestones.remove(record.getMilestonesSet().get(i).getMilestone());
		}
		List<String> milestoneNotes = new ArrayList<String>();
		if(milestones.isEmpty()){
			milestoneRequirement.setPassed(true);
		}else{
			
			for(String s: milestones){
				milestoneNotes.add("Missing milestone "+s);
			}
		}
		if (!milestoneNotes.isEmpty()){
			milestoneRequirement.getDetails().setNotes(milestoneNotes);
		}
		milestoneRequirement.getDetails().setMilestones(record.getMilestonesSet());
		return milestoneRequirement;
	}
	/*
	 * hard code graduation rules
	 * one of the concern is reuse issue, here every time, several new hashset will be created
	 */
	//PHD requirement
	private static List<RequirementCheck> checkPhD(StudentRecord record){
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
			//courses become invalid after 6 years
			if (year - Integer.parseInt(record.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				if(coreCourseSet.contains(record.getCoursesTaken().get(i).getCourse().getId())){
					coreCoursesTaken.add(record.getCoursesTaken().get(i));
				}else{
					otherCoursesTaken.add(record.getCoursesTaken().get(i));
					if(record.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce899")){
						course899.add(record.getCoursesTaken().get(i));
						credits899 += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					}
				}
			}
		}
		if (coreCoursesTaken.size() == 5){
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
		RequirementCheck timeRequirement = getTimeLimitRequirement(8, record, "TIME_LIMIT_PHD");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(record, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, record, "MILESTONES_PHD");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	//MS requirement
	private static List<RequirementCheck> checkMS(StudentRecord record){
		//current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//hard code the core courses
		HashSet<String> coreCourseSet = new HashSet<String>();
		coreCourseSet.add("csce513"); coreCourseSet.add("csce531"); coreCourseSet.add("csce750");coreCourseSet.add("csce791");
		
		//hard code the courses which is not count as additional credits
		HashSet<String> nonAdditionalCourses = new HashSet<String>();
		nonAdditionalCourses.add("csce797"); nonAdditionalCourses.add("csce799"); nonAdditionalCourses.add("csce750");nonAdditionalCourses.add("csce791");
		
		//hard code the courses which is not count as degree based credits
		HashSet<String> nonDegreeCourses = new HashSet<String>();
		nonDegreeCourses.add("csce797"); nonDegreeCourses.add("csce799");
		
		
		//hard code milestone set
		HashSet<String> milestones = new HashSet<String>();
		milestones.add("ACADEMIC_ADVISOR_APPOINTED");
		milestones.add("THESIS_ADVISOR_SELECTED");
		milestones.add("PROGRAM_OF_STUDY_SUBMITTED");
		milestones.add("THESIS_COMMITTEE_FORMED");
		milestones.add("THESIS_PROPOSAL_SCHEDULED");
		milestones.add("THESIS_PROPOSAL_APPROVED");
		milestones.add("THESIS_SUBMITTED");
		milestones.add("THESIS_DEFENSE_SCHEDULED");
		milestones.add("THESIS_DEFENSE_PASSED");
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		
		//coreCourses requirement, get the num of credits of 799 at the same time
		int credits799 = 0;
		List<CourseTaken> course799 = new ArrayList<CourseTaken>();
		
		RequirementCheck coreCourses = new RequirementCheck("CORE_COURSES_MS");
		List<CourseTaken> coreCoursesTaken = new ArrayList<CourseTaken>();
		List<CourseTaken> otherCoursesTaken = new ArrayList<CourseTaken>();
		for(int i=0; i<record.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(record.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				if(coreCourseSet.contains(record.getCoursesTaken().get(i).getCourse().getId())){
					coreCoursesTaken.add(record.getCoursesTaken().get(i));
				}else{
					otherCoursesTaken.add(record.getCoursesTaken().get(i));
					if(record.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce799")){
						course799.add(record.getCoursesTaken().get(i));
						credits799 += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
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
		RequirementCheck additionalCredits = new RequirementCheck("ADDITIONAL_CREDITS_MS");
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
		if(credits >= 8){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setCourses(validAdditionalCoursesTaken);
		requirementCheckResults.add(additionalCredits);
		
		//degree based credits
		RequirementCheck degreeBasedCredits = new RequirementCheck("DEGREE_BASED_CREDITS_MS");
		//all valid degree courses
		List<CourseTaken> validDegreeCoursesTaken = new ArrayList<CourseTaken>();
		
		credits = 0; int non_csce_credits = 0; int csce798_credits = 0;
		for (int i=0; i<record.getCoursesTaken().size();i++){
			if(!nonDegreeCourses.contains(record.getCoursesTaken().get(i).getCourse().getId())){
				validDegreeCoursesTaken.add(record.getCoursesTaken().get(i));
				if(!record.getCoursesTaken().get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					non_csce_credits += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					if (non_csce_credits > 6){
						non_csce_credits = 6;
					}
				}
				else if(record.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce798")){
					csce798_credits += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					if(csce798_credits > 3){
						csce798_credits = 3;
					}
					
				}else{
					credits += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
				}
			}
		}
		
		if (credits+non_csce_credits+csce798_credits >= 24){
			degreeBasedCredits.setPassed(true);
		}
		
		degreeBasedCredits.getDetails().setCourses(validDegreeCoursesTaken);
		requirementCheckResults.add(degreeBasedCredits);
		
		//thesis credits
		RequirementCheck thesisCredits = new RequirementCheck("THESIS_CREDITS_MS");
		if(credits799 >= 6){
			thesisCredits.setPassed(true);
		}
		thesisCredits.getDetails().setCourses(course799);
		requirementCheckResults.add(thesisCredits);
		
		//time limit
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, record, "TIME_LIMIT_MS");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(record, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, record, "MILESTONES_MS");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	//MENG requirement
	private static List<RequirementCheck> checkMENG(StudentRecord record){
		//current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//hard code the core courses
		HashSet<String> coreCourseSet = new HashSet<String>();
		coreCourseSet.add("csce513"); coreCourseSet.add("csce531"); coreCourseSet.add("csce750");coreCourseSet.add("csce791");
		
		//hard code the courses which is not count as additional credits
		HashSet<String> nonAdditionalCourses = new HashSet<String>();
		nonAdditionalCourses.add("csce797"); nonAdditionalCourses.add("csce799"); nonAdditionalCourses.add("csce750");nonAdditionalCourses.add("csce791");
		
		//hard code the courses which is not count as degree based credits
		HashSet<String> nonDegreeCourses = new HashSet<String>();
		nonDegreeCourses.add("csce797"); nonDegreeCourses.add("csce799");
		
		
		//hard code milestone set
		HashSet<String> milestones = new HashSet<String>();
		milestones.add("ACADEMIC_ADVISOR_APPOINTED");
		milestones.add("PROGRAM_OF_STUDY_SUBMITTED");
		milestones.add("COMPREHENSIVE_EXAM_PASSED");
		
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		
		//coreCourses requirement
		
		RequirementCheck coreCourses = new RequirementCheck("CORE_COURSES_MENG");
		List<CourseTaken> coreCoursesTaken = new ArrayList<CourseTaken>();
		List<CourseTaken> otherCoursesTaken = new ArrayList<CourseTaken>();
		for(int i=0; i<record.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(record.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				if(coreCourseSet.contains(record.getCoursesTaken().get(i).getCourse().getId())){
					coreCoursesTaken.add(record.getCoursesTaken().get(i));
				}else{
					otherCoursesTaken.add(record.getCoursesTaken().get(i));
				}
			}
		}
		if (coreCoursesTaken.size() == 4){
			coreCourses.setPassed(true);
		}
		coreCourses.getDetails().setCourses(coreCoursesTaken);
		requirementCheckResults.add(coreCourses);
		
		//additional credits
		RequirementCheck additionalCredits = new RequirementCheck("ADDITIONAL_CREDITS_MENG");
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
		if(credits >= 11){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setCourses(validAdditionalCoursesTaken);
		requirementCheckResults.add(additionalCredits);
		
		//degree based credits
		RequirementCheck degreeBasedCredits = new RequirementCheck("DEGREE_BASED_CREDITS_MENG");
		//all valid degree courses
		List<CourseTaken> validDegreeCoursesTaken = new ArrayList<CourseTaken>();
		
		credits = 0; int non_csce_credits = 0; int csce798_credits = 0;
		for (int i=0; i<record.getCoursesTaken().size();i++){
			if(!nonDegreeCourses.contains(record.getCoursesTaken().get(i).getCourse().getId())){
				validDegreeCoursesTaken.add(record.getCoursesTaken().get(i));
				if(!record.getCoursesTaken().get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					non_csce_credits += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					if (non_csce_credits > 6){
						non_csce_credits = 6;
					}
				}
				else if(record.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce798")){
					csce798_credits = Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					if(csce798_credits > 3){
						csce798_credits = 3;
					}
					
				}else{
					credits += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
				}
			}
		}
		
		if (credits+non_csce_credits+csce798_credits >= 30){
			degreeBasedCredits.setPassed(true);
		}
		
		degreeBasedCredits.getDetails().setCourses(validDegreeCoursesTaken);
		requirementCheckResults.add(degreeBasedCredits);
		
		//time limit
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, record, "TIME_LIMIT_MENG");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(record, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, record, "MILESTONES_MENG");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	//MSE requirement
	private static List<RequirementCheck> checkMSE(StudentRecord record){
		//current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//hard code the core courses
		HashSet<String> coreCourseSet = new HashSet<String>();
		coreCourseSet.add("csce740"); coreCourseSet.add("csce741"); coreCourseSet.add("csce742");coreCourseSet.add("csce743");coreCourseSet.add("csce747");
		
		//hard code the courses which is not count as additional credits
		HashSet<String> additionalCourses = new HashSet<String>();
		additionalCourses.add("csce510"); additionalCourses.add("csce512"); additionalCourses.add("csce515");additionalCourses.add("csce516");
		additionalCourses.add("csce520"); additionalCourses.add("csce522"); additionalCourses.add("csce547");additionalCourses.add("csce721");
		additionalCourses.add("csce723"); additionalCourses.add("csce725"); additionalCourses.add("csce744");additionalCourses.add("csce745");
		additionalCourses.add("csce767"); additionalCourses.add("csce782"); additionalCourses.add("csce821");additionalCourses.add("csce822");
		additionalCourses.add("csce826"); additionalCourses.add("csce846"); additionalCourses.add("csce872");
		
		//hard code milestone set
		HashSet<String> milestones = new HashSet<String>();
		milestones.add("ACADEMIC_ADVISOR_APPOINTED");
		milestones.add("PROGRAM_OF_STUDY_SUBMITTED");
		milestones.add("COMPREHENSIVE_EXAM_PASSED");
		milestones.add("REPORT_SUBMITTED");
		milestones.add("REPORT_APPROVED");
		
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		
		//coreCourses requirement, record 793
		List<CourseTaken> course793 = new ArrayList<CourseTaken>();
		
		RequirementCheck coreCourses = new RequirementCheck("CORE_COURSES_MSE");
		List<CourseTaken> coreCoursesTaken = new ArrayList<CourseTaken>();
		List<CourseTaken> otherCoursesTaken = new ArrayList<CourseTaken>();
		for(int i=0; i<record.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(record.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				if(coreCourseSet.contains(record.getCoursesTaken().get(i).getCourse().getId())){
					coreCoursesTaken.add(record.getCoursesTaken().get(i));
				}else{
					otherCoursesTaken.add(record.getCoursesTaken().get(i));
					if(record.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce793")){
						course793.add(record.getCoursesTaken().get(i));
					}
				}
			}
		}
		if (coreCoursesTaken.size() == 5){
			coreCourses.setPassed(true);
		}
		coreCourses.getDetails().setCourses(coreCoursesTaken);
		requirementCheckResults.add(coreCourses);
		
		//additional credits
		RequirementCheck additionalCredits = new RequirementCheck("ADDITIONAL_CREDITS_MSE");
		List<CourseTaken> validAdditionalCoursesTaken = new ArrayList<CourseTaken>();
		int credits = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(additionalCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				//the valid course must be above 700
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700){
					credits += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					validAdditionalCoursesTaken.add(otherCoursesTaken.get(i));
				}
			}
		}
		if(credits >= 15){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setCourses(validAdditionalCoursesTaken);
		requirementCheckResults.add(additionalCredits);
		
		//experience
		RequirementCheck expRequirement = new RequirementCheck("EXPERIENCE");
		if(!course793.isEmpty()){
			expRequirement.setPassed(true);
			expRequirement.getDetails().setCourses(course793);
		}else{
			List<String> expNotes = new ArrayList<String>();
			expNotes.add("Students must pass CSCE 793 ­ Internship in Software Engineering");
			expRequirement.getDetails().setNotes(expNotes);
		}
		requirementCheckResults.add(expRequirement);
		
		//time limit
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, record, "TIME_LIMIT_MSE");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(record, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, record, "MILESTONES_MSE");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	//INFAS requirement
	private static List<RequirementCheck> checkINFAS(StudentRecord record){
		//current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//hard code the core courses
		HashSet<String> coreCourseSet = new HashSet<String>();
		coreCourseSet.add("csce522"); coreCourseSet.add("csce715"); coreCourseSet.add("csce727");
		
		//hard code the courses which is not count as additional credits
		HashSet<String> nonAdditionalCourses = new HashSet<String>();
		nonAdditionalCourses.add("csce799"); nonAdditionalCourses.add("csce715");nonAdditionalCourses.add("csce727");
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		
		//coreCourses requirement
		
		RequirementCheck coreCourses = new RequirementCheck("CORE_COURSES_INFAS");
		List<CourseTaken> coreCoursesTaken = new ArrayList<CourseTaken>();
		List<CourseTaken> otherCoursesTaken = new ArrayList<CourseTaken>();
		for(int i=0; i<record.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(record.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				if(coreCourseSet.contains(record.getCoursesTaken().get(i).getCourse().getId())){
					coreCoursesTaken.add(record.getCoursesTaken().get(i));
				}else{
					otherCoursesTaken.add(record.getCoursesTaken().get(i));
				}
			}
		}
		if (coreCoursesTaken.size() == 3){
			coreCourses.setPassed(true);
		}
		coreCourses.getDetails().setCourses(coreCoursesTaken);
		requirementCheckResults.add(coreCourses);
		
		//additional credits
		RequirementCheck additionalCredits = new RequirementCheck("ADDITIONAL_CREDITS_INFAS");
		List<CourseTaken> validAdditionalCoursesTaken = new ArrayList<CourseTaken>();
		int credits500 = 0;int credits700 = 0; int non_csce_credits = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(!nonAdditionalCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				if(!record.getCoursesTaken().get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					non_csce_credits += Integer.parseInt(record.getCoursesTaken().get(i).getCourse().getNumCredits());
					if (non_csce_credits > 6){
						non_csce_credits = 6;
					}
				}else{
					credits500 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}
				//the valid course above 700
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700){
					credits700 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					validAdditionalCoursesTaken.add(otherCoursesTaken.get(i));
				}
			}
		}
		//for student who also enrolled in master's degree is tricky here, not fully considered yet.
		if(credits700 >= 9 && credits500+non_csce_credits >= 18){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setCourses(validAdditionalCoursesTaken);
		requirementCheckResults.add(additionalCredits);
		
		
		//time limit
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, record, "TIME_LIMIT_INFAS");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(record, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		return requirementCheckResults;
	}
}
