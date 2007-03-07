package org.osgisamples.congress.xmlservice;

import generated.CongressRegistrationRequest;
import generated.CongressRegistrationResponse;
import generated.ObjectFactory;

import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
			Document xmlResponse = null;
			try {
				xmlResponse = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				JAXBContext context = JAXBContext.newInstance(CongressRegistrationResponse.class.getPackage().getName());
				
				CongressRegistrationResponse resp = createMockResponse();
				
				context.createMarshaller().marshal(resp, xmlResponse);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return xmlResponse;
		}

		private CongressRegistrationResponse createMockResponse() {
			CongressRegistrationResponse resp = new ObjectFactory().createCongressRegistrationResponse();
			resp.setRegistrationCode(123);
			return resp;
		}
		
	}
	
}
