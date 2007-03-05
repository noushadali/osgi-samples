package org.osgisamples.congress.servicelocator;

import org.osgisamples.provider.XmlWebServiceProvider;

public interface ServiceLocator {

	public XmlWebServiceProvider findService(final String rootElement);
	
	public XmlWebServiceProvider findService(final String rootElement, final String version);
	
}
