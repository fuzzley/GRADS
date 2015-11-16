package edu.sc.csce740.model;

import java.util.ArrayList;

public class ProgressSummary {
	private Student student;
	private String department;
	private Term termBegan;
	private Degree degreeSought;
	private Degree certificateSought;
	private ArrayList<Professor> advisor;
	private ArrayList<Professor> committee;
	private ArrayList<RequirementCheck> requirementCheckResults;
	
	public ProgressSummary(Student student, String department, 
			Term termBegan, Degree degreeSought, Degree certificateSought,
			ArrayList<Professor> advisor, ArrayList<Professor> committee,
			ArrayList<RequirementCheck> requirementCheckResults){
		this.setStudent(student);
		this.setDepartment(department);
		this.setTermBegan(termBegan);
		this.setDegreeSought(degreeSought);
		this.setCertificateSought(certificateSought);
		this.setAdvisor(advisor);
		this.setCommittee(committee);
		this.setRequirementCheckResults(requirementCheckResults);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Term getTermBegan() {
		return termBegan;
	}

	public void setTermBegan(Term termBegan) {
		this.termBegan = termBegan;
	}

	public Degree getDegreeSought() {
		return degreeSought;
	}

	public void setDegreeSought(Degree degreeSought) {
		this.degreeSought = degreeSought;
	}

	public Degree getCertificateSought() {
		return certificateSought;
	}

	public void setCertificateSought(Degree certificateSought) {
		this.certificateSought = certificateSought;
	}

	public ArrayList<Professor> getAdvisor() {
		return advisor;
	}

	public void setAdvisor(ArrayList<Professor> advisor) {
		this.advisor = advisor;
	}

	public ArrayList<Professor> getCommittee() {
		return committee;
	}

	public void setCommittee(ArrayList<Professor> committee) {
		this.committee = committee;
	}

	public ArrayList<RequirementCheck> getRequirementCheckResults() {
		return requirementCheckResults;
	}

	public void setRequirementCheckResults(ArrayList<RequirementCheck> requirementCheckResults) {
		this.requirementCheckResults = requirementCheckResults;
	}
}
