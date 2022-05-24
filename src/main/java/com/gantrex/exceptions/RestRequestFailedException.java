package com.gantrex.exceptions;

public class RestRequestFailedException extends Exception {

	private static final long serialVersionUID = -1375393067933607918L;

	public RestRequestFailedException() {
		super();
	}

	public RestRequestFailedException(String msg) {
		super(msg);
	}

	public RestRequestFailedException(String msg, Throwable e) {
		super(msg, e);
	}
}
