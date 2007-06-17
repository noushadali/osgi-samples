package com.osgisamples.congress.business;

public class SessionNotAvailableForCongressException extends RuntimeException {
	private static final long serialVersionUID = 8604705598495707425L;

	public SessionNotAvailableForCongressException(String message) {
		super(message);
	}

}
