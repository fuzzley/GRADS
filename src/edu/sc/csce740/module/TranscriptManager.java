package edu.sc.csce740.module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import edu.sc.csce740.exception.StudentRecordNotFound;
import edu.sc.csce740.model.CourseTaken;
import edu.sc.csce740.model.Degree;
import edu.sc.csce740.model.Milestone;
import edu.sc.csce740.model.Professor;
import edu.sc.csce740.model.Student;
import edu.sc.csce740.model.StudentRecord;
import edu.sc.csce740.model.Term;

public class TranscriptManager {
	public static StudentRecord getTranscript(String fileName) {
		StudentRecord studentsRecords = null;
		Gson gson = new Gson();
		try
		{
			System.out.println("read JSON from file");
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			studentsRecords = gson.fromJson(br, StudentRecord.class);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return studentsRecords;
	}

	public static void updateTranscript(Student student, String department, Term termBegan, Degree degreeSought, Degree certificateSought,
					List<Degree> previousDegrees, List<Professor> advisors, List<Professor> committee, List<CourseTaken> coursesTaken, List<Milestone> milestoneSet,
					List<String> notes, String fileName) throws StudentRecordNotFound 
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
	
}
