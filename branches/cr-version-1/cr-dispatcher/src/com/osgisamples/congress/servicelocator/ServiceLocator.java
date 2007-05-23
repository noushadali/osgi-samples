package com.osgisamples.congress.servicelocator;

import org.osgi.framework.ServiceReference;

import com.osgisamples.congress.provider.XmlWebServiceProvider;

public interface ServiceLocator {

	public ServiceReference findServiceReference(final String rootElement);
	
	public ServiceReference findServiceReference(final String rootElement, final String version);
	
	public XmlWebServiceProvider findService(final String rootElement);
	
	public XmlWebServiceProvider findService(final String rootElement, final String version);
}
