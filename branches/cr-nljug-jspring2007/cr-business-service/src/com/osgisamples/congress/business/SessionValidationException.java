package com.osgisamples.congress.business;

public class SessionValidationException extends RuntimeException {
	private static final long serialVersionUID = -1742916780623800548L;

	public SessionValidationException(String message) {
		super(message);
	}
}
