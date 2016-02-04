package model;

public class OperationNotAllowedException extends Exception {
	
	private String operation;
	
	public OperationNotAllowedException(String errorMessage, String operation) {
		super(errorMessage);
		this.operation = operation;
	}

	public String getOperation() {
		return operation;
	}
	
	

}
