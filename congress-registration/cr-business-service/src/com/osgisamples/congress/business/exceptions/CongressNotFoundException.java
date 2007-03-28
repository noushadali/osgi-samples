package com.osgisamples.congress.business.exceptions;

public class CongressNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7097870575990911222L;

	public CongressNotFoundException(String message) {
		super(message);
	}
}
