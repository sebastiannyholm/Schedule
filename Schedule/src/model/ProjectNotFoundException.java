package model;

public class ProjectNotFoundException extends Exception {
	
	private String operation;
	
	public ProjectNotFoundException(String errorMessage, String operation) {
		super(errorMessage);
		this.operation = operation;
	}

	public String getOperation() {
		return operation;
	}
}
