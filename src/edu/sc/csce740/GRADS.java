package edu.sc.csce740;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.sc.csce740.exception.CoursesNotLoadedException;
import edu.sc.csce740.exception.LoggedInUserDoesNotHavePermissionException;
import edu.sc.csce740.exception.NoUserSetInSessionException;
import edu.sc.csce740.exception.StudentRecordNotFoundException;
import edu.sc.csce740.exception.StudentRecordsNotLoadedException;
import edu.sc.csce740.exception.StudentRecordsNotSavedException;
import edu.sc.csce740.exception.UserNotFoundException;
import edu.sc.csce740.exception.UsersNotLoadedException;
import edu.sc.csce740.model.Course;
import edu.sc.csce740.model.CourseTaken;
import edu.sc.csce740.model.ProgressSummary;
import edu.sc.csce740.model.StudentRecord;
import edu.sc.csce740.model.Term;
import edu.sc.csce740.model.User;
import edu.sc.csce740.module.DataStore;
import edu.sc.csce740.module.ProgressSummaryGenerator;
import edu.sc.csce740.module.Session;
import edu.sc.csce740.module.TranscriptManager;

public class GRADS implements GRADSIntf {

	@Override
    /**
     * Loads the list of system usernames and permissions.
     * @param usersFile the filename of the users file.
     * @throws Exception for I/O errors.  SEE NOTE IN CLASS HEADER.
     */
	public void loadUsers(String usersFile) throws UsersNotLoadedException {
		DataStore.loadUsers(usersFile);		
	}

	@Override
    /**
     * Loads the list of valid courses.
     * @param coursesFile the filename of the users file.
     * @throws Exception for I/O errors.  SEE NOTE IN CLASS HEADER.
     */
	public void loadCourses(String coursesFile) throws CoursesNotLoadedException {
		DataStore.loadCourses(coursesFile);		
	}

	@Override
    /**
     * Loads the list of system transcripts.
     * @param recordsFile the filename of the transcripts file.
     * @throws Exception for I/O errors.  SEE NOTE IN CLASS HEADER.
     */
	public void loadRecords(String recordsFile) throws StudentRecordsNotLoadedException {
		DataStore.loadRecords(recordsFile);
	}

	@Override
    /**
     * Sets the user id of the user currently using the system.
     * @param userId  the id of the user to log in.
     * @throws Exception  if the user id is invalid.  SEE NOTE IN CLASS HEADER.
     */
	public void setUser(String userId) throws UserNotFoundException {
		User user = DataStore.getPermissionByUserId(userId);
		if (user == null) {
			throw new UserNotFoundException();
		} //else
		
		Session.setUser(userId);
	}

	@Override
    /**
     * Closes the current session, logs the user out, and clears and session data.
     * @throws Exception  if the user id is invalid.  SEE NOTE IN CLASS HEADER.
     */
	public void clearSession() throws NoUserSetInSessionException {
		if (Session.getUser() == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		Session.clearSession();		
	}

	@Override
    /**
     * Gets the user id of the user currently using the system.
     * @return  the user id of the user currently using the system.
     */
	public String getUser() {
		return Session.getUser();
	}

	@Override
    /**
     * Gets a list of the userIds of the students that a GPC can view.
     * @return a list containing the userId of for each student in the
     *      system belonging to the current user 
     * @throws Exception is the current user is not a GPC.
     */
	public List<String> getStudentIDs() throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (!User.GPC_ROLE.equals(loggedInUser.getRole())) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		return DataStore.getStudentIDs();
	}

	@Override
    /**
     * Gets the raw student record data for a given userId.
     * @param userId  the identifier of the student.
     * @return  the student record data.
     * @throws Exception  if the form data could not be retrieved.  SEE NOTE IN 
     *      CLASS HEADER.
     */
	public StudentRecord getTranscript(String userId) throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException, StudentRecordNotFoundException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (!User.GPC_ROLE.equals(loggedInUser.getRole()) && !loggedInUserId.equals(userId)) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		 return TranscriptManager.getTranscript(userId);
	}

	@Override
    /**
     * Saves a new set of student data to the records data.  
     * @param userId the student ID to overwrite.
     * @param transcript  the new student record
     * @param permanent  a status flag indicating whether (if false) to make a 
     * temporary edit to the in-memory structure or (if true) a permanent edit.
     * @throws Exception  if the transcript data could not be saved or failed
     * a validity check.  SEE NOTE IN CLASS HEADER.
     */
	public void updateTranscript(String userId, StudentRecord transcript, Boolean permanent) 
			throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException, StudentRecordNotFoundException, StudentRecordsNotSavedException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (!User.GPC_ROLE.equals(loggedInUser.getRole()) && !loggedInUserId.equals(userId)) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		if (loggedInUserId.equals(userId) && permanent) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		TranscriptManager.updateTranscript(userId,  transcript, permanent);
	}

	@Override
    /**
     * Appends a note to a student record.  
     * @param userId the student ID to add a note to.
     * @param note  the note to append
     * @param permanent  a status flag indicating whether (if false) to make a 
     * temporary edit to the in-memory structure or (if true) a permanent edit.
     * @throws Exception  if the note could not be saved or a non-GPC tries to call. 
     * SEE NOTE IN CLASS HEADER.
     */
	public void addNote(String userId, String note, Boolean permanent)
			 throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException, StudentRecordNotFoundException, StudentRecordsNotSavedException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (!User.GPC_ROLE.equals(loggedInUser.getRole())) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		TranscriptManager.addNote(userId, note, permanent);
	}

	@Override
    /**
     * Generates progress summary
     * @param userId the student to generate the record for.
     * @returns the student's progress summary in a data class matching the I/O file.
     * @throws Exception  if the progress summary could not be generated.  
     * SEE NOTE IN CLASS HEADER.
     */
	public ProgressSummary generateProgressSummary(String userId)
			throws Exception {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (!User.GPC_ROLE.equals(loggedInUser.getRole()) && !loggedInUserId.equals(userId)) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		return ProgressSummaryGenerator.generateProgressSummary(userId);
	}

	@Override
    /**
     * Generates a new progress summary, assuming that the student passes the
     * provided set of prospective courses.
     * @param userId the student to generate the record for.
     * @param courses a list of the prospective courses.
     * @returns the student's hypothetical progress summary
     * @throws Exception  if the progress summary could not be generated or the courses  
     * are invalid. SEE NOTE IN CLASS HEADER.
     */
	public ProgressSummary simulateCourses(String userId,
			List<Course> courses) throws Exception {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (!User.GPC_ROLE.equals(loggedInUser.getRole()) && !loggedInUserId.equals(userId)) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		int month = Calendar.getInstance().get(Calendar.MONTH);
		String semester = "";
		if (month <= 4){
			semester = "SPRING";
		}
		else if(month <= 7){
			semester = "SUMMER";
		}else if(month <= 12){
			semester = "FALL";
		}
		Term term = new Term(semester, year);
		List<CourseTaken> courseTakens = new ArrayList<CourseTaken>();
		for (int i=0; i<courses.size();i++){
			//assume the simulated grade is a
			CourseTaken courseTaken = new CourseTaken(courses.get(i), term, "A");
			courseTakens.add(courseTaken);
		}
		return ProgressSummaryGenerator.simulateCourses(userId, courseTakens);
	}

}
