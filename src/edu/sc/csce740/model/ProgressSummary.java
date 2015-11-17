package edu.sc.csce740.model;

import java.util.ArrayList;
import java.util.List;

public class ProgressSummary {
	private Student student;
	private String department;
	private Term termBegan;
	private Degree degreeSought;
	private Degree certificateSought;
	private List<Professor> advisors;
	private List<Professor> committee;
	private List<RequirementCheck> requirementCheckResults;
	
	public ProgressSummary(Student student, String department, 
			Term termBegan, Degree degreeSought, Degree certificateSought,
			List<Professor> advisors, List<Professor> committee,
			List<RequirementCheck> requirementCheckResults){
		this.setStudent(student);
		this.setDepartment(department);
		this.setTermBegan(termBegan);
		this.setDegreeSought(degreeSought);
		this.setCertificateSought(certificateSought);
		this.setAdvisors(advisors);
		this.setCommittee(committee);
		this.setRequirementCheckResults(requirementCheckResults);
	}
	
	public ProgressSummary(){
		this.student = new Student();
		this.department = "";
		this.termBegan = new Term();
		this.degreeSought = new Degree();
		this.certificateSought = new Degree();
		this.advisors = new ArrayList<Professor>();
		this.committee = new ArrayList<Professor>();
		this.requirementCheckResults = new ArrayList<RequirementCheck>();
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

	public List<Professor> getAdvisors() {
		return advisors;
	}

	public void setAdvisors(List<Professor> advisors) {
		this.advisors = advisors;
	}

	public List<Professor> getCommittee() {
		return committee;
	}

	public void setCommittee(List<Professor> committee) {
		this.committee = committee;
	}

	public List<RequirementCheck> getRequirementCheckResults() {
		return requirementCheckResults;
	}

	public void setRequirementCheckResults(List<RequirementCheck> requirementCheckResults) {
		this.requirementCheckResults = requirementCheckResults;
	}
}
