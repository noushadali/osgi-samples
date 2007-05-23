package com.osgisamples.congress.dispatcher;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.servicelocator.ServiceLocator;

public class SOAPDispatcher implements Dispatcher {
	private final static Log logger = LogFactory.getLog(SOAPDispatcher.class);
	private static ServiceLocator locator;

	public static void setServiceLocator(ServiceLocator locator) {
		SOAPDispatcher.locator = locator;
	}

	public void doService(SOAPEnvelope req, SOAPEnvelope resp) throws SOAPException  {
		String requestType = req.getBody().getChildNodes().item(0).getLocalName();
		logger.debug("Received SOAP message of type: " + requestType);

		String version = obtainRequestedVersion(req);

		XmlWebServiceProvider service = locator.findService(requestType, version);
		
		if(service != null) {
			logger.debug("Service found throug locator: " + service.getClass().getName());
			Document serviceResponse = service.doService(req.getBody().getFirstChild());
			resp.getBody().addDocument(serviceResponse);
		} else {
			createError(resp, "No service found");
		}
	}

	private String obtainRequestedVersion(SOAPEnvelope req) throws SOAPException {
		String version = null;
		if (req.getBody().getChildNodes().item(0).hasAttributes()) {
			NamedNodeMap nodeMap = req.getBody().getChildNodes().item(0).getAttributes();
			version = nodeMap.getNamedItem("version").getNodeValue();
			logger.debug("Received SOAP message of version" + version);
		}
		return version;
	}

	private void createError(SOAPEnvelope response, String errorText) throws SOAPException {
		SOAPFault fault = response.getBody().addFault();
		fault.setFaultCode("ServiceNotFound");
		fault.setFaultString(errorText);
	}
}
