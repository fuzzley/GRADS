package edu.sc.csce740.module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import edu.sc.csce740.exception.StudentRecordNotFoundException;
import edu.sc.csce740.exception.StudentRecordsNotLoadedException;
import edu.sc.csce740.model.CourseTaken;
import edu.sc.csce740.model.Degree;
import edu.sc.csce740.model.Milestone;
import edu.sc.csce740.model.Professor;
import edu.sc.csce740.model.Student;
import edu.sc.csce740.model.StudentRecord;
import edu.sc.csce740.model.Term;

public class TranscriptManager {
	public static void updateTranscript(Student student, String department, Term termBegan, Degree degreeSought, Degree certificateSought,
					List<Degree> previousDegrees, List<Professor> advisors, List<Professor> committee, List<CourseTaken> coursesTaken, List<Milestone> milestoneSet,
					List<String> notes, String fileName) throws StudentRecordNotFoundException 
	{
		StudentRecord record = new StudentRecord();
		record.setAdvisors(advisors);
		record.setStudent(student);
		record.setCertificateSought(certificateSought);
		record.setCommittee(committee);
		record.setCoursesTaken(coursesTaken);
		record.setDepartment(department);
		record.setMilestonesSet(milestoneSet);
		record.setNotes(notes);
		record.setPreviousDegrees(previousDegrees);
		record.setTermBegan(termBegan);
		record.setDegreeSought(degreeSought);
		     
		Gson gson = new Gson();  
		    
		// convert java object to JSON format,  
		// and returned as JSON formatted string  
		  String json = gson.toJson(record);  
		    
		  try {  
		   //write converted json data to a file named "CountryGSON.json"  
		   FileWriter writer = new FileWriter(fileName);  
		   writer.write(json);  
		   writer.close();  
		    
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  }  
	}
	
	public static void updateTranscript(String userId, StudentRecord transcript, boolean permanent) throws StudentRecordNotFoundException {
		DataStore.updateTranscript(userId, transcript, permanent);
	}

	public static void addNote(String userId, String note, boolean permanent) throws StudentRecordNotFoundException {
		DataStore.addNote(userId, note, permanent);
	}

	public static StudentRecord getTranscript(String userId) throws StudentRecordNotFoundException {
		StudentRecord transcript = DataStore.getTranscript(userId);
		if (transcript == null) {
			throw new StudentRecordNotFoundException();
		} //else
		
		return transcript;
	}
}
