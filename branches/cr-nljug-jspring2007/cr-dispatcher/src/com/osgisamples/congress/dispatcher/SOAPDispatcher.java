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

	public void doService(final SOAPEnvelope req, final SOAPEnvelope resp)
			throws SOAPException {

		// obtain root request element name
		String requestType = obtainRequestType(req);

		// obtain request version from "version" attribute
		String version = obtainRequestedVersion(req);

		// find the right web service implementation version
		XmlWebServiceProvider service = locator.findService(requestType,
				version);

		if (service != null) {
			executeService(service, req, resp);
		} else {
			createError(resp, "No service found");
		}
	}

	public static void setServiceLocator(ServiceLocator locator) {
		SOAPDispatcher.locator = locator;
	}

	private void executeService(XmlWebServiceProvider service,
			SOAPEnvelope req, SOAPEnvelope resp) throws SOAPException {
		logger.debug("Service found throug locator: "
				+ service.getClass().getName());
		Document serviceResponse1 = service.doService(req.getBody()
				.getFirstChild());
		Document serviceResponse = serviceResponse1;
		resp.getBody().addDocument(serviceResponse);
	}

	private String obtainRequestType(SOAPEnvelope req) throws SOAPException {
		String requestType = req.getBody().getChildNodes().item(0)
				.getLocalName();
		logger.debug("Received SOAP message of type: " + requestType);
		return requestType;
	}

	private String obtainRequestedVersion(SOAPEnvelope req)
			throws SOAPException {
		String version = null;
		if (req.getBody().getChildNodes().item(0).hasAttributes()) {
			NamedNodeMap nodeMap = req.getBody().getChildNodes().item(0)
					.getAttributes();
			version = nodeMap.getNamedItem("version").getNodeValue();
			logger.debug("Received SOAP message of version" + version);
		}
		return version;
	}

	private void createError(SOAPEnvelope response, String errorText)
			throws SOAPException {
		SOAPFault fault = response.getBody().addFault();
		fault.setFaultCode("ServiceNotFound");
		fault.setFaultString(errorText);
	}
}
