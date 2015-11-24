import edu.sc.csce740.GRADS;
import edu.sc.csce740.exception.StudentRecordNotFoundException;
import edu.sc.csce740.module.*;
import edu.sc.csce740.model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ProgressSummary ps;
		try {
			ps = ProgressSummaryGenerator.generateProgressSummary("mhunt");
			System.out.println(gson.toJson(ps));
		} catch (StudentRecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("done");
		

	}

}