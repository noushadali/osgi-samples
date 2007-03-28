package com.osgisamples.congress.webservice.registerparticipant;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.osgisamples.congress.provider.XmlWebServiceProvider;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		Properties props =  new Properties();
		props.put("rootElement", "CongressRegistrationRequest");
		props.put("version", context.getBundle().getHeaders().get("version"));
		context.registerService(XmlWebServiceProvider.class.getName(), new CongressRegistrationWebService(), props);
	}

	public void stop(BundleContext context) throws Exception {
	}

}
