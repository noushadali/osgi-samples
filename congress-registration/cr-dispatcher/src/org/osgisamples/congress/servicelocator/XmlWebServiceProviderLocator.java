package org.osgisamples.congress.servicelocator;

import java.util.Arrays;
import java.util.Iterator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgisamples.provider.XmlWebServiceProvider;

public class XmlWebServiceProviderLocator extends ServiceTracker implements
ServiceLocator {

	public XmlWebServiceProviderLocator(BundleContext bundleContext) throws InvalidSyntaxException {
		super(bundleContext, bundleContext.createFilter("(&(objectClass=" + XmlWebServiceProvider.class.getName() + ")(rootElement=*))"), null);
		open();
	}

	public XmlWebServiceProvider findService(final String rootElement) {
		return findService(rootElement, null);
	}

	public XmlWebServiceProvider findService(final String rootElement, final String version) {
		ServiceReference[] refs = this.getServiceReferences();
		if(refs != null && refs.length > 0) {
			Iterator<ServiceReference> it = Arrays.asList(refs).iterator();
			while(it.hasNext()) {
				ServiceReference ref = it.next();
				if(rootElement.equals(ref.getProperty("rootElement"))) {
					System.out.println("Found the right service, now check for the version");
					if (version == null) {
						System.out.println("No special version requested");
						return (XmlWebServiceProvider) context.getService(ref);
					} else if (version.equals(ref.getProperty("version"))){
						System.out.println("Found the requested version");
						return (XmlWebServiceProvider) context.getService(ref);
					}
				}
			}
		}
		return null;
	}
}
