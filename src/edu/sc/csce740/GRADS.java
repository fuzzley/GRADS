package edu.sc.csce740;

import java.util.List;

import edu.sc.csce740.model.CourseTaken;
import edu.sc.csce740.model.ProgressSummary;
import edu.sc.csce740.model.StudentRecord;
import edu.sc.csce740.module.*;

public class GRADS implements GRADSIntf {

	@Override
	public void loadUsers(String usersFile) throws Exception {
		DataStore.loadUsers(usersFile);		
	}

	@Override
	public void loadCourses(String coursesFile) throws Exception {
		DataStore.loadCourses(coursesFile);		
	}

	@Override
	public void loadRecords(String recordsFile) throws Exception {
		DataStore.loadStudentRecords(recordsFile);
	}

	@Override
	public void setUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSession() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getStudentIDs() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentRecord getTranscript(String userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTranscript(String userId, StudentRecord transcript,
			Boolean permanent) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNote(String userId, String note, Boolean permanent)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProgressSummary generateProgressSummary(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProgressSummary simulateCourses(String userId,
			List<CourseTaken> courses) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
