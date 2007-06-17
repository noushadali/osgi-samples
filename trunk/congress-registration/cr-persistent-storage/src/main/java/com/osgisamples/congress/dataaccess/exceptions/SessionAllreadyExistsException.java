package com.osgisamples.congress.dataaccess.exceptions;

public class SessionAllreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -7586873030772511427L;

	public SessionAllreadyExistsException(String message) {
		super(message);
	}

}
