import edu.sc.csce740.GRADS;


public class Driver {
	
	private static String coursesPath = "src/resources/courses.txt";
	private static String studentRecordsPath = "src/resources/students.txt";
	private static String usersPath = "src/resources/users.txt";

	public static void main(String[] args) {
		GRADS grads = new GRADS();
		try {
			grads.loadCourses(coursesPath);
			grads.loadRecords(studentRecordsPath);
			grads.loadUsers(usersPath);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		System.out.println("done");
	}

}