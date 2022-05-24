package com.gantrex.exceptions;

public class DocumentSizeExceedException extends RuntimeException {

	private static final long serialVersionUID = -1278082909993695397L;

	public DocumentSizeExceedException() {
		super();
	}

	public DocumentSizeExceedException(String msg) {
		super(msg);
	}

}
