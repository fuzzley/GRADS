package edu.sc.csce740.test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.sc.csce740.*;
import edu.sc.csce740.exception.LoggedInUserDoesNotHavePermissionException;
import edu.sc.csce740.exception.NoUserSetInSessionException;
import edu.sc.csce740.exception.UserNotFoundException;
import edu.sc.csce740.model.Course;
import edu.sc.csce740.model.ProgressSummary;
import edu.sc.csce740.model.StudentRecord;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;

public class GRADSTest {
	private GRADS grads;
	
	private static String coursesPath = "src/resources/courses.json";
	private static String studentRecordsPath = "src/resources/students.json";
	private static String usersPath = "src/resources/users.json";
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception{
		this.grads = new GRADS();
		this.grads.loadUsers(usersPath);
		this.grads.loadCourses(coursesPath);
		this.grads.loadRecords(studentRecordsPath);
	}
	
	@Test
	public void testSetUser_Valid() throws UserNotFoundException {
		grads.setUser("ggay");
		assert(grads.getUser() == "ggay");
	}

	@Test
	public void testSetUser_Invalid() throws UserNotFoundException{
		exception.expect(UserNotFoundException.class);
		grads.setUser("gggay");
	}
	
	@Test
	public void testClearSession_Valid() throws UserNotFoundException, NoUserSetInSessionException {
		grads.setUser("ggay");
		grads.clearSession();
		assert(grads.getUser() == null);
	}
	
	@Test
	public void testClearSession_Invalid() throws UserNotFoundException, NoUserSetInSessionException {
		exception.expect(NoUserSetInSessionException.class);
		grads.clearSession();
		assert(grads.getUser() == null);
	}
	
	@Test
	public void testGetUser() throws UserNotFoundException {
		grads.setUser("ggay");
		assert(grads.getUser() == "ggay");
	}
	
	@Test
	public void testGetStudentIds_Valid() throws UserNotFoundException, NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException {
		Set<String> expectedIds = new HashSet<String>();
		expectedIds.add("mhunt");
		expectedIds.add("phd1");
		expectedIds.add("phd2");
		expectedIds.add("ms1");
		expectedIds.add("ms2");
		expectedIds.add("meng1");
		expectedIds.add("meng2");
		expectedIds.add("mse1");
		expectedIds.add("mse2");
		expectedIds.add("infas1");
		expectedIds.add("infas2");
		
		grads.setUser("ggay");
		Set<String> ids = new HashSet<String>(grads.getStudentIDs());
		assert(expectedIds.equals(ids));
	}
	
	@Test
	public void testGetStudentIds_Invalid() throws UserNotFoundException, NoUserSetInSessionException, LoggedInUserDoesNotHavePermissionException {
		grads.setUser("rbob");
		exception.expect(LoggedInUserDoesNotHavePermissionException.class);
		grads.getStudentIDs();
	}
	
	@Test
	public void testGenerateProgressSummary_passedPhD() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/phdPassed.json";
		String studentId = "phd1";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_notPassedPhD() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/phdNotPassed.json";
		String studentId = "phd2";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_passedMS() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/msPassed.json";
		String studentId = "ms1";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_notPassedMS() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/msNotPassed.json";
		String studentId = "ms2";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_passedMENG() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/mengPassed.json";
		String studentId = "meng1";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_notPassedMENG() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/mengNotPassed.json";
		String studentId = "meng2";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_passedMSE() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/msePassed.json";
		String studentId = "mse1";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_notPassedMSE() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/mseNotPassed.json";
		String studentId = "mse2";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_passedINFAS() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/msInfasPassed.json";
		String studentId = "infas1";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_notPassedINFAS() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/msInfasNotPassed.json";
		String studentId = "infas2";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		//ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		
		ProgressSummary generated = grads.generateProgressSummary(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGenerateProgressSummary_invalid() throws Exception{
		this.grads.setUser("rbob");
		
		String studentId = "mhunt";
		
		exception.expect(LoggedInUserDoesNotHavePermissionException.class);
		grads.generateProgressSummary(studentId);
	}
	
	@Test
	public void testSimulateCourses_valid() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/simulateCourses.json";
		String studentId = "mhunt";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		ProgressSummary ps = gson.fromJson(recordsStr, ProgressSummary.class);
		List<Course>courses = new ArrayList<Course>();
		//Course csce513 = new Course("Computer Architecture", "csce513", "3");
		Course csce531 = new Course("Compiler Construction", "csce531", "3");
		Course csce750 = new Course("Analysis of Algorithms", "csce750", "3");
		//Course csce551 = new Course("Theory of Computation", "csce551","3");
		//courses.add(csce513);
		courses.add(csce531); courses.add(csce750); //courses.add(csce551);
		ProgressSummary generated = grads.simulateCourses(studentId, courses);
		
		assertEquals(gson.toJson(ps), gson.toJson(generated));
	}
	
	@Test
	public void testSimulateCourses_invalid() throws Exception{
		this.grads.setUser("rbob");
		
		String studentId = "mhunt";
		
		List<Course>courses = new ArrayList<Course>();
		Course csce513 = new Course("Computer Architecture", "csce513", "3");
		Course csce531 = new Course("Compiler Construction", "csce531", "3");
		Course csce750 = new Course("Analysis of Algorithms", "csce750", "3");
		Course csce551 = new Course("Theory of Computation", "csce551","3");
		courses.add(csce513); courses.add(csce531); courses.add(csce750); courses.add(csce551);
		
		exception.expect(LoggedInUserDoesNotHavePermissionException.class);
		grads.simulateCourses(studentId, courses);
		
	}
	
	@Test
	public void testGetTranscript_valid() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/getTranscript.json";
		String studentId = "mhunt";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		
		StudentRecord generated = grads.getTranscript(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testGetTranscript_invalid() throws Exception{
		this.grads.setUser("rbob");
		
		String studentId = "mhunt";
		
		exception.expect(LoggedInUserDoesNotHavePermissionException.class);
		grads.getTranscript(studentId);
		
	}
	
	@Test
	public void testUpdateTranscript_valid() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/updateTranscript.json";
		String studentId = "mhunt";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		StudentRecord transcript = gson.fromJson(recordsStr, StudentRecord.class);
		
		grads.updateTranscript(studentId, transcript, false);
		StudentRecord generated = grads.getTranscript(studentId);
		
		assertEquals(transcript, generated);
	}
	
	@Test
	public void testUpdateTranscript_invalid() throws Exception{
		this.grads.setUser("rbob");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/updateTranscript.json";
		String studentId = "mhunt";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		StudentRecord transcript = gson.fromJson(recordsStr, StudentRecord.class);
		
		exception.expect(LoggedInUserDoesNotHavePermissionException.class);
		grads.updateTranscript(studentId, transcript, false);
		
	}
	
	@Test
	public void testAddNote_valid() throws Exception{
		this.grads.setUser("ggay");
		String recordsStr = null;
		BufferedReader br = null;
		
		//read correct progress summary from json file
		String fileName = "src/resources/test/addNote.json";
		String studentId = "mhunt";
		
		br = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		recordsStr = sb.toString();
		br.close();
		
		Gson gson = new Gson();
		
		String note = "this is a testing note";
		grads.addNote(studentId, note, false);
		StudentRecord generated = grads.getTranscript(studentId);
		
		assertEquals(recordsStr, gson.toJson(generated));
	}
	
	@Test
	public void testAddNote_invalid() throws Exception{
		this.grads.setUser("rbob");
		
		String studentId = "mhunt";
		
		String note = "this is a testing note";
		exception.expect(LoggedInUserDoesNotHavePermissionException.class);
		grads.addNote(studentId, note, false);
	}
	
	@After
	public void tearDown() throws NoUserSetInSessionException{
		this.grads = null;
	}

}
