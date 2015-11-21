package edu.sc.csce740;

import java.util.List;

import edu.sc.csce740.exception.*;
import edu.sc.csce740.model.*;
import edu.sc.csce740.module.*;

public class GRADS implements GRADSIntf {

	@Override
	public void loadUsers(String usersFile) throws UsersNotLoadedException {
		DataStore.loadUsers(usersFile);		
	}

	@Override
	public void loadCourses(String coursesFile) throws CoursesNotLoadedException {
		DataStore.loadCourses(coursesFile);		
	}

	@Override
	public void loadRecords(String recordsFile) throws StudentRecordsNotLoadedException {
		DataStore.loadRecords(recordsFile);
	}

	@Override
	public void setUser(String userId) throws UserNotFoundException {
		User user = DataStore.getPermissionByUserId(userId);
		if (user == null) {
			throw new UserNotFoundException();
		} //else
		
		Session.setUser(userId);
	}

	@Override
	public void clearSession() throws NoUserSetInSessionException {
		if (Session.getUser() == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		Session.clearSession();		
	}

	@Override
	public String getUser() {
		return Session.getUser();
	}

	@Override
	public List<String> getStudentIDs() throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (loggedInUser.getRole() != User.GPC_ROLE) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		return DataStore.getStudentIDs();
	}

	@Override
	public StudentRecord getTranscript(String userId) throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException, StudentRecordNotFoundException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (loggedInUser.getRole() != User.GPC_ROLE && !loggedInUserId.equals(userId)) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		 return TranscriptManager.getTranscript(userId);
	}

	@Override
	public void updateTranscript(String userId, StudentRecord transcript, Boolean permanent) 
			throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException, StudentRecordNotFoundException, StudentRecordsNotSavedException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (loggedInUser.getRole() != User.GPC_ROLE && !loggedInUserId.equals(userId)) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		if (loggedInUserId.equals(userId) && permanent) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		TranscriptManager.updateTranscript(userId,  transcript, permanent);
	}

	@Override
	public void addNote(String userId, String note, Boolean permanent)
			 throws NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException, StudentRecordNotFoundException, StudentRecordsNotSavedException {
		String loggedInUserId = Session.getUser();
		if (loggedInUserId == null) {
			throw new NoUserSetInSessionException();
		} //else
		
		User loggedInUser = DataStore.getPermissionByUserId(loggedInUserId);
		if (loggedInUser.getRole() != User.GPC_ROLE) {
			throw new LoggedInUserDoesNotHavePermissionException();
		} //else
		
		TranscriptManager.addNote(userId, note, permanent);
	}

	@Override
	public ProgressSummary generateProgressSummary(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProgressSummary simulateCourses(String userId,
			List<Course> courses) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
