package edu.sc.csce740.model;

public class RequirementCheck {
	private String name;
	private boolean passed;
	private RequirementDetail details;
	
	public RequirementCheck(String name, boolean passed, RequirementDetail details){
		this.setName(name);
		this.setPassed(passed);
		this.setDetails(details);
	}
	
	public RequirementCheck(String name){
		this.name = name;
		this.passed = false;
		this.details = new RequirementDetail();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public RequirementDetail getDetails() {
		return details;
	}

	public void setDetails(RequirementDetail details) {
		this.details = details;
	}
}
