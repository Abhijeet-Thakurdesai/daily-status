package com.status.exception;

public class TeamModuleException extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public TeamModuleException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public TeamModuleException() {
		super();
	}
}
