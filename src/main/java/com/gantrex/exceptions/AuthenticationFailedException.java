package com.gantrex.exceptions;

public class AuthenticationFailedException extends Exception {

	private static final long serialVersionUID = 1573232557357483487L;

	public AuthenticationFailedException() {
		super();
	}

	public AuthenticationFailedException(String msg) {
		super(msg);
	}

	public AuthenticationFailedException(String msg, Throwable e) {
		super(msg, e);
	}

	public AuthenticationFailedException(Throwable e) {
		super(e);
	}

}
