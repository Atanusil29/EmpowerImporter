package com.gantrex.exceptions;

public class EmpowerFilterException extends RuntimeException {

	private static final long serialVersionUID = -642115244229961449L;

	public EmpowerFilterException() {
		super();
	}

	public EmpowerFilterException(String msg) {
		super(msg);
	}

	public EmpowerFilterException(String msg, Throwable e) {
		super(msg, e);
	}

}
