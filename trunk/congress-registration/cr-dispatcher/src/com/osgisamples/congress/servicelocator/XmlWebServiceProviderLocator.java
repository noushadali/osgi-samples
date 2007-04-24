package com.osgisamples.congress.servicelocator;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.servicelocator.helpers.VersionComparator;

public class XmlWebServiceProviderLocator extends ServiceTracker implements
ServiceLocator {
	
	private static final Log log = LogFactory.getLog(XmlWebServiceProviderLocator.class);

	public XmlWebServiceProviderLocator(BundleContext bundleContext) throws InvalidSyntaxException {
		super(bundleContext, bundleContext.createFilter("(&(objectClass=" + XmlWebServiceProvider.class.getName() + ")(rootElement=*))"), null);
		open();
	}

	public XmlWebServiceProvider findService(final String rootElement) {
		return findService(rootElement, null);
	}

	public XmlWebServiceProvider findService(final String rootElement, final String version) {
		return (XmlWebServiceProvider) getService(findServiceReference(rootElement, version));
	}

	public ServiceReference findServiceReference(final String rootElement) {
		return findServiceReference(rootElement, null);
	}

	public ServiceReference findServiceReference(final String rootElement, final String version) {
		ServiceReference[] refs = this.getServiceReferences();
		if(refs != null && refs.length > 0) {
			Iterator<ServiceReference> it = Arrays.asList(refs).iterator();
			while(it.hasNext()) {
				ServiceReference ref = it.next();
				if(rootElement.equals(ref.getProperty("rootElement"))) {
					log.info("Found the right service, now check for the version");
					if (version == null) {
						log.info("No special version requested");
						return ref;
					} else if (VersionComparator.isCompatible(version, (String)ref.getProperty("version"))){
						log.info("Found the requested version");
						return ref;
					}
				}
			}
		}
		return null;
	}
}
