package com.osgisamples.congress.servicelocator;

import com.osgisamples.congress.provider.XmlWebServiceProvider;

public interface ServiceLocator {

	public XmlWebServiceProvider findService(final String rootElement);
	
	public XmlWebServiceProvider findService(final String rootElement, final String version);
	
}
