package com.gantrex.exceptions;

/**
 * Thrown when Configuration object is not yet initialized.
 * 
 */
public class ConfigNotInitializedException extends Exception {

	private static final long serialVersionUID = -3849539972056255792L;

	public ConfigNotInitializedException() {
		super();
	}

	public ConfigNotInitializedException(String msg) {
		super(msg);
	}

}
