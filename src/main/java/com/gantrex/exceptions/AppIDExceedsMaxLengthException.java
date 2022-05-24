package com.gantrex.exceptions;

public class AppIDExceedsMaxLengthException extends RuntimeException {

	private static final long serialVersionUID = -1859340506241268381L;

	public AppIDExceedsMaxLengthException() {
		super();
	}

	public AppIDExceedsMaxLengthException(String msg) {
		super(msg);
	}

}
