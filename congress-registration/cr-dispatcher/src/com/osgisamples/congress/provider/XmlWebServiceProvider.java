package com.osgisamples.congress.provider;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface XmlWebServiceProvider {

	// TODO: Change this interface to pass a Reader and a writer for request and response.
//	 public void doService(final Reader request, final Writer response);
	public Document doService(final Node request);
	
}
