package com.gantrex.exceptions;

public class CSRFNullException extends Exception {

	private static final long serialVersionUID = 2733173215033524790L;

	public CSRFNullException() {
		super();
	}

	public CSRFNullException(String msg) {
		super(msg);
	}

	public CSRFNullException(String msg, Throwable e) {
		super(msg, e);
	}

}
