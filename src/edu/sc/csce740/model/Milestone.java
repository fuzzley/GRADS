package edu.sc.csce740.model;

public class Milestone {
	private String milestone;
	private Term term;
	
	/**
	 * 
	 * @param milestone
	 * @param term
	 */
	public Milestone(String milestone, Term term){
		this.setMilestone(milestone);
		this.setTerm(term);
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
