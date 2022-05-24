package com.gantrex.exceptions;

public class DocumentManagementException extends Exception {

	private static final long serialVersionUID = -1082370136515863365L;

	public DocumentManagementException() {
		super();
	}

	public DocumentManagementException(String msg) {
		super(msg);
	}

	public DocumentManagementException(String msg, Throwable e) {
		super(msg, e);
	}

	public DocumentManagementException(Throwable e) {
		super(e);
	}

}
