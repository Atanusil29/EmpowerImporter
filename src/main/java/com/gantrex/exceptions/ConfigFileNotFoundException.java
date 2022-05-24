package com.gantrex.exceptions;

/**
 * Thrown when config file specified by Environment Variable propertiesFile
 * doesn't exist.
 * 
 */
public class ConfigFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6725198222641901458L;

	public ConfigFileNotFoundException() {
		super();
	}

	public ConfigFileNotFoundException(String msg) {
		super(msg);
	}
}
