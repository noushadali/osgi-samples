package com.osgisamples.congress.webservice.registerparticipant;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.provider.XmlWebServiceProvider;

public class Activator implements BundleActivator {

	private ServiceTracker tracker;
	
	public void start(BundleContext context) throws Exception {
		CongressRegistrationWebService congressRegistrationWebService = new CongressRegistrationWebService();
		tracker = new ServiceTracker(context, CongressManager.class.getName(), null);
		congressRegistrationWebService.setCongressManagerTracker(
				tracker
				);
		tracker.open();
		
		Properties props =  new Properties();
		props.put("rootElement", "CongressRegistrationRequest");
		props.put("version", context.getBundle().getHeaders().get("Bundle-Version"));
		context.registerService(XmlWebServiceProvider.class.getName(), congressRegistrationWebService, props);
	}

	public void stop(BundleContext context) throws Exception {
		tracker.close();
		tracker = null;
	}

}
