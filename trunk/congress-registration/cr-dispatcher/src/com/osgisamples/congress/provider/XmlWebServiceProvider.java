package com.osgisamples.congress.provider;

import org.w3c.dom.Document;

public interface XmlWebServiceProvider {

	public Document doService(final Document request);
	
}
