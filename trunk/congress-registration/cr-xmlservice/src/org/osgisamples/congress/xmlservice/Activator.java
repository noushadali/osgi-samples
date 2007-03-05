package org.osgisamples.congress.xmlservice;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgisamples.provider.XmlWebServiceProvider;
import org.w3c.dom.Document;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		Properties props =  new Properties();
		props.put("rootElement", "HelloWorld");
		props.put("version", "1.1.0");
		context.registerService(XmlWebServiceProvider.class.getName(), new SomeService(), props);
	}

	public void stop(BundleContext context) throws Exception {
	}

	private static class SomeService implements XmlWebServiceProvider {

		public Document doService(final Document request) {
			request.getDocumentElement().setAttribute("iUnderstand", "HelloWorld");
			return request;
		}
		
	}
	
}
