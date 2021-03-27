package com.db.assignment.demo.ehs;

public class InvalidVersionException extends Exception{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public InvalidVersionException() {
		super();
	}

	public InvalidVersionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidVersionException(String message) {
		super(message);
	}

	public InvalidVersionException(Throwable cause) {
		super(cause);
	}
}
