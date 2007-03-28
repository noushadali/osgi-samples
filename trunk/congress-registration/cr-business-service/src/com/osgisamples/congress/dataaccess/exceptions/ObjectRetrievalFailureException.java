package com.osgisamples.congress.dataaccess.exceptions;

public class ObjectRetrievalFailureException extends RuntimeException {
	private static final long serialVersionUID = 6408752088170437496L;

	public ObjectRetrievalFailureException(final Class clazz, final String objectPrimaryKey) {
		super(createMessage(clazz, objectPrimaryKey));
	}
	
	private static String createMessage(final Class clazz, final String objectPrimaryKey) {
		return "The object of type '" + clazz.toString() + "' with the primary key '" +
		objectPrimaryKey + "' could not be found";
	}
}
