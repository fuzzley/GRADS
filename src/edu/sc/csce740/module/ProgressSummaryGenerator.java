package edu.sc.csce740.module;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import edu.sc.csce740.exception.StudentRecordNotFoundException;
import edu.sc.csce740.model.*;
import edu.sc.csce740.module.DataStore;

/**
 * ProgressSummaryGnerator Module. 
 * Generate student's progress summary given the student's id. 
 * Simulate student's progress summary given the student's id and a list of simulated courses taken.
 * @author Mingxiang Zhu
 * @version 1.0
 * @since 11/24/2015
 */
public class ProgressSummaryGenerator {
	//current year
	private static int year = Calendar.getInstance().get(Calendar.YEAR);
	private static int month = Calendar.getInstance().get(Calendar.MONTH);
	
	/**
	 * @param studentId Student's identification
	 * @return ProgressSummary Student's current progress report
	 * @throws StudentRecordNotFoundException
	 */
	public static ProgressSummary generateProgressSummary(String studentId) throws StudentRecordNotFoundException{
		StudentRecord transcript = DataStore.getTranscript(studentId);
		if (transcript == null) {
			throw new StudentRecordNotFoundException();
		}
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		if(transcript.getDegreeSought().getName().equalsIgnoreCase("PHD")){
			requirementCheckResults = checkPhD(transcript);
		}
		else if(transcript.getDegreeSought().getName().equalsIgnoreCase("MS")){
			requirementCheckResults = checkMS(transcript);
		}
		else if(transcript.getDegreeSought().getName().equalsIgnoreCase("MENG")){
			requirementCheckResults = checkMENG(transcript);
		}
		else if(transcript.getDegreeSought().getName().equalsIgnoreCase("MSE")){
			requirementCheckResults = checkMSE(transcript);
		}
		if (transcript.getCertificateSought() != null){
			if (transcript.getCertificateSought().getName().equalsIgnoreCase("INFAS")){
				List<RequirementCheck> requirementINFAS = checkINFAS(transcript);
				for(int i=0; i<requirementINFAS.size();i++){
					requirementCheckResults.add(requirementINFAS.get(i));
				}
			}
		}
		
		ProgressSummary ps = new ProgressSummary(transcript.getStudent(),
				transcript.getDepartment(), transcript.getTermBegan(),transcript.getDegreeSought(), 
				transcript.getCertificateSought(), transcript.getAdvisors(), transcript.getCommittee(), requirementCheckResults);

		return ps;
	}
	
	/**
	 * @param studentId Student's identification
	 * @param courses Simulated already taken courses
	 * @return ProgressSummary student's simulated progress report
	 * @throws StudentRecordNotFoundException
	 */
	public static ProgressSummary simulateCourses(String studentId, List<CourseTaken> courses) throws StudentRecordNotFoundException{
		StudentRecord transcript = DataStore.getTranscript(studentId);
		if (transcript == null) {
			throw new StudentRecordNotFoundException();
		}
		//adding simulated courses to transcript
		transcript.getCoursesTaken().addAll(courses);
		
		List<RequirementCheck> requirementCheckResults = new ArrayList<RequirementCheck>();
		if(transcript.getDegreeSought().getName().equalsIgnoreCase("PHD")){
			requirementCheckResults = checkPhD(transcript);
		}
		else if(transcript.getDegreeSought().getName().equalsIgnoreCase("MS")){
			requirementCheckResults = checkMS(transcript);
		}
		else if(transcript.getDegreeSought().getName().equalsIgnoreCase("MENG")){
			requirementCheckResults = checkMENG(transcript);
		}
		else if(transcript.getDegreeSought().getName().equalsIgnoreCase("MSE")){
			requirementCheckResults = checkMSE(transcript);
		}
		
		if (transcript.getCertificateSought() != null){
			if (transcript.getCertificateSought().getName().equalsIgnoreCase("INFAS")){
				List<RequirementCheck> requirementINFAS = checkINFAS(transcript);
				for(int i=0; i<requirementINFAS.size();i++){
					requirementCheckResults.add(requirementINFAS.get(i));
				}
			}
		}
		
		ProgressSummary ps = new ProgressSummary(transcript.getStudent(),
				transcript.getDepartment(), transcript.getTermBegan(),transcript.getDegreeSought(), 
				transcript.getCertificateSought(), transcript.getAdvisors(), transcript.getCommittee(), requirementCheckResults);

		return ps;
	}
	
	/** 
	 * get additional credits requirement
	 * @param otherCoursesTaken Taken courses which are not core courses
	 * @param nonAdditionalCourses A Hashset of course id's which are not considered as additional course requirement for a given degree 
	 * @param notes The notes to add to the additional credits requirement
	 * @param name The name of additional credits requirement, must be one of {ADDITIONAL_CREDITS_PHD, ADDITIONAL_CREDITS_MS, ADDITIONAL_CREDITS_MENG, ADDITIONAL_CREDITS_MSE, ADDITIONAL_CREDITS_INFAS}
	 * @param limit Number of credits needed to meet the requirement
	 * @return RequirementCheck
	 */
	private static RequirementCheck getAdditionalCreditsRequirement(List<CourseTaken> otherCoursesTaken, 
			HashSet<String> nonAdditionalCourses, List<String> notes, String name, int limit){
		RequirementCheck additionalCredits = new RequirementCheck(name);
		int credits = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(!nonAdditionalCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				//the valid course must be above 700
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700
						&& otherCoursesTaken.get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					credits += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}
			}
		}
		if(credits >= limit){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setNotes(notes);
		return additionalCredits;
	}
	
	/**
	 * gpa calculation
	 * @param courses A list of taken courses
	 * @return gpa The gpa of the taken courses
	 */
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
	
	/**
	 * get the time limit requirement for a given degree
	 * @param limit The years limitation for a given degree
	 * @param transcript The student's current transcript
	 * @param name The name of time limit requirement, must be one of {TIME_LIMIT_PHD, TIME_LIMIT_MS, TIME_LIMIT_MENG, TIME_LIMIT_MSE, TIME_LIMIT_INFAS}
	 * @return timeRequirement
	 */
	private static RequirementCheck getTimeLimitRequirement(int limit, StudentRecord transcript, String name){
		RequirementCheck timeRequirement = new RequirementCheck(name);
		if(year - Integer.parseInt(transcript.getTermBegan().getYear()) <= limit){
			String semester = transcript.getTermBegan().getSemester();
			//comparing semester according to current month
			if((semester.equalsIgnoreCase("SPRING") && month <= 4)
				|| (semester.equalsIgnoreCase("SUMMER") && month <= 7) 
				|| (semester.equalsIgnoreCase("FALL") && month <= 12)){
				timeRequirement.setPassed(true);
			}
		}
		List<String> timeNotes = new ArrayList<String>();
		timeNotes.add("A student must complete all degree requirements within a period of eight years after being admitted to the program as a regular student.");
		timeRequirement.getDetails().setNotes(timeNotes);
		return timeRequirement;
	}
	
	/**
	 * get gpa requirement check
	 * @param transcript The student's current transcript
	 * @param name The name of gpa requirement, must be GPA
	 * @return gpaRequirement
	 */
	private static RequirementCheck getGpaRequirement(StudentRecord transcript, String name){
		RequirementCheck gpaRequirement = new RequirementCheck(name);
		float current_gpa = getGpa(transcript.getCoursesTaken());
		gpaRequirement.getDetails().setGpa(Float.toString(current_gpa));
		if(current_gpa > 3){
			gpaRequirement.setPassed(true);
		}
		List<String> gpaNotes = new ArrayList<String>();
		gpaNotes.add("For graduation a student must have a 3.0 GPA on all graduate courses taken and all courses taken at the 700 level or above.");
		gpaRequirement.getDetails().setNotes(gpaNotes);
		return gpaRequirement;
	}
	
	/**
	 * check milestone requirement
	 * @param milestones A list of milestones for a given degree
	 * @param transcript The student's current transcript
	 * @param name The name of milestone requirement, must be one of {MILESTONES_PHD, MILESTONES_MS, MILESTONES_MENG, MILESTONES_MSE}
	 * @return milestoneRequirement
	 */
	private static RequirementCheck getMilestoneRequirement(HashSet<String> milestones, StudentRecord transcript, String name){
		RequirementCheck milestoneRequirement = new RequirementCheck(name);
		for(int i=0; i<transcript.getMilestonesSet().size();i++){
			milestones.remove(transcript.getMilestonesSet().get(i).getMilestone());
		}
		List<String> milestoneNotes = new ArrayList<String>();
		if(milestones.isEmpty()){
			milestoneRequirement.setPassed(true);
		}else{
			//put the incompleted milestones in the nodes
			for(String s: milestones){
				milestoneNotes.add("Missing milestone "+s);
			}
		}
		if (!milestoneNotes.isEmpty()){
			milestoneRequirement.getDetails().setNotes(milestoneNotes);
		}
		milestoneRequirement.getDetails().setMilestones(transcript.getMilestonesSet());
		return milestoneRequirement;
	}
	
	/**
	 * PHD requirement
	 * @param transcript The student's current transcript
	 * @return requirementCheckResults The requirementCheckResults for PHD
	 */
	private static List<RequirementCheck> checkPhD(StudentRecord transcript){
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
		for(int i=0; i<transcript.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(transcript.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				String semester = transcript.getTermBegan().getSemester();
				//acurate time based on semester
				if((semester.equalsIgnoreCase("SPRING") && month <= 4)
					|| (semester.equalsIgnoreCase("SUMMER") && month <= 7) 
					|| (semester.equalsIgnoreCase("FALL") && month <= 12)){
					
					if(coreCourseSet.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
						coreCoursesTaken.add(transcript.getCoursesTaken().get(i));
					}else{
						otherCoursesTaken.add(transcript.getCoursesTaken().get(i));
						if(transcript.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce899")){
							course899.add(transcript.getCoursesTaken().get(i));
							credits899 += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
						}
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
		List<String> additionalCreditsNotes = new ArrayList<String>();
		additionalCreditsNotes.add("Students must pass 20 hours of CSCE courses numbered above "
				+ "700 that are not any of the core courses (excluding CSCE 799 and 899).");
		
		additionalCreditsNotes.add("Courses become invalid after six years");
		
		RequirementCheck additionalCredits = getAdditionalCreditsRequirement(otherCoursesTaken, nonAdditionalCourses, 
				additionalCreditsNotes, "ADDITIONAL_CREDITS_PHD", 20);
		
		requirementCheckResults.add(additionalCredits);
		
		//degree based credits
		RequirementCheck degreeBasedCredits = new RequirementCheck("DEGREE_BASED_CREDITS_PHD");
		List<CourseTaken> validDegreeCoursesTaken = new ArrayList<CourseTaken>();
		
		int credits500 = 0; int credits700 = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(!nonDegreeCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				validDegreeCoursesTaken.add(otherCoursesTaken.get(i));
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700
						&& otherCoursesTaken.get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					credits500 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					credits700 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}else{
					credits500 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}
			}
		}
		for (int i=0; i<transcript.getPreviousDegrees().size();i++){
			if(master.contains(transcript.getPreviousDegrees().get(i).getName())){
				if (credits700 >= 24){
					degreeBasedCredits.setPassed(true);
				}
			}
			else if(credits700 >= 24 && credits500 >= 48){
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
		RequirementCheck timeRequirement = getTimeLimitRequirement(8, transcript, "TIME_LIMIT_PHD");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(transcript, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, transcript, "MILESTONES_PHD");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	/**
	 * MS requirement
	 * @param transcript The student's current transcript
	 * @return requirementCheckResults The requirementCheckResults for MS
	 */
	private static List<RequirementCheck> checkMS(StudentRecord transcript){
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
		for(int i=0; i<transcript.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(transcript.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				String semester = transcript.getTermBegan().getSemester();
				//acurate time based on semester
				if((semester.equalsIgnoreCase("SPRING") && month <= 4)
					|| (semester.equalsIgnoreCase("SUMMER") && month <= 7) 
					|| (semester.equalsIgnoreCase("FALL") && month <= 12)){
					if(coreCourseSet.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
						coreCoursesTaken.add(transcript.getCoursesTaken().get(i));
					}else{
						otherCoursesTaken.add(transcript.getCoursesTaken().get(i));
						if(transcript.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce799")){
							course799.add(transcript.getCoursesTaken().get(i));
							credits799 += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
						}
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
		List<String> additionalCreditsNotes = new ArrayList<String>();
		additionalCreditsNotes.add("Students must pass 8 hours of CSCE courses numbered above 700 that are "
				+ "not any of the core courses (excluding CSCE 799)");
		
		additionalCreditsNotes.add("CSCE 797 may not be applied toward the degree");
		
		RequirementCheck additionalCredits = getAdditionalCreditsRequirement(otherCoursesTaken, nonAdditionalCourses, 
				additionalCreditsNotes, "ADDITIONAL_CREDITS_MS", 8);
		
		requirementCheckResults.add(additionalCredits);
		
		//degree based credits
		RequirementCheck degreeBasedCredits = new RequirementCheck("DEGREE_BASED_CREDITS_MS");
		//all valid degree courses
		List<CourseTaken> validDegreeCoursesTaken = new ArrayList<CourseTaken>();
		
		int credits = 0; int non_csce_credits = 0; int csce798_credits = 0;
		for (int i=0; i<transcript.getCoursesTaken().size();i++){
			if(!nonDegreeCourses.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
				validDegreeCoursesTaken.add(transcript.getCoursesTaken().get(i));
				if(!transcript.getCoursesTaken().get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					non_csce_credits += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
					if (non_csce_credits > 6){
						non_csce_credits = 6;
					}
				}
				else if(transcript.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce798")){
					csce798_credits += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
					if(csce798_credits > 3){
						csce798_credits = 3;
					}
					
				}else{
					credits += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
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
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, transcript, "TIME_LIMIT_MS");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(transcript, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, transcript, "MILESTONES_MS");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	/**
	 * MENG requirement
	 * @param transcript The student's current transcript
	 * @return requirementCheckResults The requirementCheckResults for MENG
	 */
	private static List<RequirementCheck> checkMENG(StudentRecord transcript){
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
		for(int i=0; i<transcript.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(transcript.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				String semester = transcript.getTermBegan().getSemester();
				//acurate time based on semester
				if((semester.equalsIgnoreCase("SPRING") && month <= 4)
					|| (semester.equalsIgnoreCase("SUMMER") && month <= 7) 
					|| (semester.equalsIgnoreCase("FALL") && month <= 12)){
					
					if(coreCourseSet.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
						coreCoursesTaken.add(transcript.getCoursesTaken().get(i));
					}else{
						otherCoursesTaken.add(transcript.getCoursesTaken().get(i));
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
		List<String> additionalCreditsNotes = new ArrayList<String>();
		additionalCreditsNotes.add("Students must pass 11 hours of CSCE courses numbered above 700 that "
				+ "are not any of the above courses (excluding CSCE 799).");
		
		additionalCreditsNotes.add("CSCE 797 may not be applied toward the degree");
		
		RequirementCheck additionalCredits = getAdditionalCreditsRequirement(otherCoursesTaken, nonAdditionalCourses, 
				additionalCreditsNotes, "ADDITIONAL_CREDITS_MENG", 11);
		
		requirementCheckResults.add(additionalCredits);
		
		//degree based credits
		RequirementCheck degreeBasedCredits = new RequirementCheck("DEGREE_BASED_CREDITS_MENG");
		//all valid degree courses
		List<CourseTaken> validDegreeCoursesTaken = new ArrayList<CourseTaken>();
		
		int credits = 0; int non_csce_credits = 0; int csce798_credits = 0;
		for (int i=0; i<transcript.getCoursesTaken().size();i++){
			if(!nonDegreeCourses.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
				validDegreeCoursesTaken.add(transcript.getCoursesTaken().get(i));
				if(!transcript.getCoursesTaken().get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					non_csce_credits += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
					if (non_csce_credits > 6){
						non_csce_credits = 6;
					}
				}
				else if(transcript.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce798")){
					csce798_credits = Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
					if(csce798_credits > 3){
						csce798_credits = 3;
					}
					
				}else{
					credits += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
				}
			}
		}
		
		if (credits+non_csce_credits+csce798_credits >= 30){
			degreeBasedCredits.setPassed(true);
		}
		
		degreeBasedCredits.getDetails().setCourses(validDegreeCoursesTaken);
		requirementCheckResults.add(degreeBasedCredits);
		
		//time limit
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, transcript, "TIME_LIMIT_MENG");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(transcript, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, transcript, "MILESTONES_MENG");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	/**
	 * MSE requirement
	 * @param transcript The student's current transcript
	 * @return requirementCheckResults The requirementCheckResults for MSE
	 */
	private static List<RequirementCheck> checkMSE(StudentRecord transcript){
		//current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//hard code the core courses
		HashSet<String> coreCourseSet = new HashSet<String>();
		coreCourseSet.add("csce740"); coreCourseSet.add("csce741"); coreCourseSet.add("csce742");coreCourseSet.add("csce743");coreCourseSet.add("csce747");
		
		//hard code the courses which count as additional credits
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
		
		//coreCourses requirement, transcript 793
		List<CourseTaken> course793 = new ArrayList<CourseTaken>();
		
		RequirementCheck coreCourses = new RequirementCheck("CORE_COURSES_MSE");
		List<CourseTaken> coreCoursesTaken = new ArrayList<CourseTaken>();
		List<CourseTaken> otherCoursesTaken = new ArrayList<CourseTaken>();
		for(int i=0; i<transcript.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(transcript.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				String semester = transcript.getTermBegan().getSemester();
				//acurate time based on semester
				if((semester.equalsIgnoreCase("SPRING") && month <= 4)
					|| (semester.equalsIgnoreCase("SUMMER") && month <= 7) 
					|| (semester.equalsIgnoreCase("FALL") && month <= 12)){
					
					if(coreCourseSet.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
						coreCoursesTaken.add(transcript.getCoursesTaken().get(i));
					}else{
						otherCoursesTaken.add(transcript.getCoursesTaken().get(i));
						if(transcript.getCoursesTaken().get(i).getCourse().getId().equalsIgnoreCase("csce793")){
							course793.add(transcript.getCoursesTaken().get(i));
						}
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
		int credits = 0;
		for (int i=0; i<otherCoursesTaken.size();i++){
			if(additionalCourses.contains(otherCoursesTaken.get(i).getCourse().getId())){
				//the valid course must be above 700
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700
						&& otherCoursesTaken.get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					credits += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}
			}
		}
		if(credits >= 15){
			additionalCredits.setPassed(true);
		}
		List<String> additionalCreditsNotes = new ArrayList<String>();
		additionalCreditsNotes.add("Students must pass 15 hours of CSCE courses numbered above 700 that "
				+ "are not any of the core courses.");
		
		additionalCreditsNotes.add("The courses must be taken from the following list:");
		additionalCreditsNotes.add("CSCE 510 ­ System Programming");
		additionalCreditsNotes.add("CSCE 512 ­ System Performance Evaluation");
		additionalCreditsNotes.add("CSCE 515 ­ Computer Network Programming");
		additionalCreditsNotes.add("CSCE 516 ­ Computer Networks");
		additionalCreditsNotes.add("CSCE 520 ­ Database System Design");
		additionalCreditsNotes.add("CSCE 522 ­ Information Security Principles");
		additionalCreditsNotes.add("CSCE 547 ­ Windows Programming");
		additionalCreditsNotes.add("CSCE 721 ­ Physical Database Design");
		additionalCreditsNotes.add("CSCE 723 ­ Advanced Database Design");
		additionalCreditsNotes.add("CSCE 725 ­ Information Retrieval: Algorithms and Models");
		additionalCreditsNotes.add("CSCE 744 ­ Object­Oriented Analysis and Design");
		additionalCreditsNotes.add("CSCE 745 ­ Object­Oriented Programming Methods");
		additionalCreditsNotes.add("CSCE 767 ­ Interactive Computer Systems");
		additionalCreditsNotes.add("CSCE 782 ­ Multiagent systems");
		additionalCreditsNotes.add("CSCE 821 ­ Distributed Database Design");
		additionalCreditsNotes.add("CSCE 822 ­ Data Mining and Warehousing");
		additionalCreditsNotes.add("CSCE 826 ­ Cooperative Information Systems");
		additionalCreditsNotes.add("CSCE 846 ­ Software Reliability and Safety");
		additionalCreditsNotes.add("MGSC 872 ­ Project Management");
		additionalCredits.getDetails().setNotes(additionalCreditsNotes);
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
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, transcript, "TIME_LIMIT_MSE");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(transcript, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		//milestone requirement
		RequirementCheck milestoneRequirement = getMilestoneRequirement(milestones, transcript, "MILESTONES_MSE");
		requirementCheckResults.add(milestoneRequirement);
		
		return requirementCheckResults;
	}
	
	/**
	 * INFAS requirement
	 * @param transcript The student's current transcript
	 * @return requirementCheckResults The requirementCheckResults for INFAS
	 */
	private static List<RequirementCheck> checkINFAS(StudentRecord transcript){
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
		for(int i=0; i<transcript.getCoursesTaken().size();i++){
			//courses become invalid after 6 years
			if (year - Integer.parseInt(transcript.getCoursesTaken().get(i).getTerm().getYear()) <= 6){
				String semester = transcript.getTermBegan().getSemester();
				//acurate time based on semester
				if((semester.equalsIgnoreCase("SPRING") && month <= 4)
					|| (semester.equalsIgnoreCase("SUMMER") && month <= 7) 
					|| (semester.equalsIgnoreCase("FALL") && month <= 12)){
					if(coreCourseSet.contains(transcript.getCoursesTaken().get(i).getCourse().getId())){
						coreCoursesTaken.add(transcript.getCoursesTaken().get(i));
					}else{
						otherCoursesTaken.add(transcript.getCoursesTaken().get(i));
					}
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
				if(!transcript.getCoursesTaken().get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					non_csce_credits += Integer.parseInt(transcript.getCoursesTaken().get(i).getCourse().getNumCredits());
					if (non_csce_credits > 6){
						non_csce_credits = 6;
					}
				}else{
					credits500 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
				}
				//the valid course above 700
				if (Integer.parseInt(otherCoursesTaken.get(i).getCourse().getId().replaceAll("[^0-9]", "")) > 700
						&& otherCoursesTaken.get(i).getCourse().getId().replaceAll("[0-9]", "").equalsIgnoreCase("csce")){
					credits700 += Integer.parseInt(otherCoursesTaken.get(i).getCourse().getNumCredits());
					validAdditionalCoursesTaken.add(otherCoursesTaken.get(i));
				}
			}
		}
		
		//hard code possible master's degree
		HashSet<String> master = new HashSet<String>();
		master.add("MS"); master.add("MENG"); master.add("MSE");
				
		//for student who also enrolled in master's degree. 9 credits should be subtracted
		if(master.contains(transcript.getDegreeSought().getName())){
			if(credits700 >= 9 && credits500+non_csce_credits >= 27){
				additionalCredits.setPassed(true);
			}
		}
		else if(credits700 >= 9 && credits500+non_csce_credits >= 18){
			additionalCredits.setPassed(true);
		}
		additionalCredits.getDetails().setCourses(validAdditionalCoursesTaken);
		requirementCheckResults.add(additionalCredits);
		
		
		//time limit
		RequirementCheck timeRequirement = getTimeLimitRequirement(6, transcript, "TIME_LIMIT_INFAS");
		requirementCheckResults.add(timeRequirement);
		
		//gpa requirement
		RequirementCheck gpaRequirement = getGpaRequirement(transcript, "GPA");
		requirementCheckResults.add(gpaRequirement);
		
		return requirementCheckResults;
	}
}
