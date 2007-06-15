package com.osgisamples.congress.provider.exceptions;

public class ServiceProviderException extends RuntimeException {
	private static final long serialVersionUID = -7979378928672072254L;

	public ServiceProviderException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ServiceProviderException(String message) {
		super(message);
	}
}
