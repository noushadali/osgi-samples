package com.osgisamples.congress.dataaccess.exceptions;

public class SessionValidationException extends RuntimeException {
	private static final long serialVersionUID = 5207628292219540846L;

	public SessionValidationException(String message) {
		super(message);
	}

}
