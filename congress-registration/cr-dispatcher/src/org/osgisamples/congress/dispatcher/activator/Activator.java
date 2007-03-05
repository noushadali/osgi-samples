package org.osgisamples.congress.dispatcher.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgisamples.congress.dispatcher.SOAPDispatcher;
import org.osgisamples.congress.servicelocator.ServiceLocator;
import org.osgisamples.congress.servicelocator.XmlWebServiceProviderLocator;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		ServiceLocator locator = new XmlWebServiceProviderLocator(context);
		SOAPDispatcher.setServiceLocator(locator);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
