package com.gantrex.exceptions;

public class InvalidBusinessDocIDException extends Exception {

	private static final long serialVersionUID = -3239401704402761097L;

	public InvalidBusinessDocIDException() {
		super();
	}

	public InvalidBusinessDocIDException(String msg) {
		super(msg);
	}
}
