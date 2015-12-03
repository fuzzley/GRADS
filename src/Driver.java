import edu.sc.csce740.GRADS;
import edu.sc.csce740.exception.StudentRecordNotFoundException;
import edu.sc.csce740.module.*;
import edu.sc.csce740.model.*;

import com.google.gson.Gson;

public class Driver {
	
	private static String coursesPath = "src/resources/courses.json";
	private static String studentRecordsPath = "src/resources/students.json";
	private static String usersPath = "src/resources/users.json";

	public static void main(String[] args) {
		GRADS grads = new GRADS();
		
		try {
			grads.loadCourses(coursesPath);
			grads.loadRecords(studentRecordsPath);
			grads.loadUsers(usersPath);
			grads.setUser("ggay");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
//
//		Gson gson = new Gson();
//		ProgressSummary ps;
//		try {
//			ps = ProgressSummaryGenerator.generateProgressSummary("infas2");
//			System.out.print(gson.toJson(ps));
//		} catch (StudentRecordNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			StudentRecord mHunt = grads.getTranscript("mhunt");
			System.out.println(mHunt.getStudent().getFirstName());
			mHunt.getStudent().setFirstName("Bob");
			
			StudentRecord updatedMHunt = grads.getTranscript("mhunt");
			System.out.println(updatedMHunt.getStudent().getFirstName());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		//System.out.println("done");
		

	}

}