package edu.sc.csce740.model;

public class Term {
	private String semester;
	private String year;
	
	/**
	 * 
	 * @param semester
	 * @param year
	 */
	public Term(String semester, String year){
		this.setSemester(semester);
		this.setYear(year);
	}
	
	public Term(){
		this.semester = "";
		this.year = "";
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
}
