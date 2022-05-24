package com.gantrex.exceptions;

public class TaskException extends Exception {

	private static final long serialVersionUID = 3016590236696181788L;

	public TaskException(String message) {
		super(message);
	}

	public TaskException(String message, Throwable e) {
		super(message, e);
	}

}
