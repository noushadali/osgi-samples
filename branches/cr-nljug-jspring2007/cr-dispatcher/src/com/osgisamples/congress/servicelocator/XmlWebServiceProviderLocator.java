package com.osgisamples.congress.servicelocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.util.tracker.ServiceTracker;

import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.servicelocator.helpers.VersionComparator;

public class XmlWebServiceProviderLocator extends ServiceTracker implements
ServiceLocator {
	
	private static final Log log = LogFactory.getLog(XmlWebServiceProviderLocator.class);

	public XmlWebServiceProviderLocator(final BundleContext bundleContext) throws InvalidSyntaxException {
		super(
				bundleContext, 
				bundleContext.createFilter("(&(objectClass=" + XmlWebServiceProvider.class.getName() + ")(rootElement=*))"), 
				null
			);
		open();
	}

	public XmlWebServiceProvider findService(final String rootElement) {
		return findService(rootElement, null);
	}

	public XmlWebServiceProvider findService(final String rootElement, final String version) {
		XmlWebServiceProvider serviceProvider = null;
		ServiceReference serviceReference = findServiceReference(rootElement, version);
		if (serviceReference != null) {
			serviceProvider = (XmlWebServiceProvider) getService(serviceReference);
		}
		return serviceProvider;
	}

	public ServiceReference findServiceReference(final String rootElement) {
		return findServiceReference(rootElement, null);
	}

	public ServiceReference findServiceReference(final String rootElement, final String version) {
		ServiceReference[] serviceReferences = this.getServiceReferences();
		ServiceReference bestReference = null;
		
		if(serviceReferences != null && serviceReferences.length > 0) {
			for(ServiceReference currentReference : serviceReferences) {
				if(rootElement.equals(currentReference.getProperty("rootElement"))) {
					log.info("Found the right service (" + currentReference.getProperty("rootElement") + ") with version " + currentReference.getProperty("version"));
					if (version == null	|| VersionComparator.isCompatible(version, (String)currentReference.getProperty("version"))) {
						bestReference = chooseBestReference(bestReference,currentReference);
					}
				}
			}
		}
		if (bestReference != null) {
			log.info("Returning version: " + bestReference.getProperty("version"));
		}
		return bestReference;
	}

	private ServiceReference chooseBestReference(final ServiceReference firstReference, final ServiceReference secondReference) {
		ServiceReference bestReference = null;
		if (firstReference == null) {
			bestReference = secondReference;
		} else if (secondReference == null) {
			bestReference = firstReference;
		} else {
			Version firstReferenceVersion = new Version((String)firstReference.getProperty("version"));
			Version secondReferenceVersion = new Version((String)secondReference.getProperty("version"));
			if (firstReferenceVersion.compareTo(secondReferenceVersion) > 0) {
				bestReference = firstReference;
			} else {
				bestReference = secondReference;
			}
		}
		return bestReference;
	}
}
