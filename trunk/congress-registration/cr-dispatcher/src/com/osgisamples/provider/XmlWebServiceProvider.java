package com.osgisamples.provider;

import org.w3c.dom.Document;

public interface XmlWebServiceProvider {

	public Document doService(final Document request);
	
}
