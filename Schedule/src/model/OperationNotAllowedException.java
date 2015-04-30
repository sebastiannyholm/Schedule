package model;

public class OperationNotAllowedException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String operation;
	
	public OperationNotAllowedException(String errorMessage, String operation) {
		super(errorMessage);
		this.operation = operation;
	}

	public String getOperation() {
		return operation;
	}
	
	

}
