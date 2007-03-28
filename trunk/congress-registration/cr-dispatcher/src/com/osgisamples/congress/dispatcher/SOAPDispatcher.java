package com.osgisamples.congress.dispatcher;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;

import org.apache.axis.message.SOAPEnvelope;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.servicelocator.ServiceLocator;

public class SOAPDispatcher implements Dispatcher {

	private static ServiceLocator locator;

	public static void setServiceLocator(ServiceLocator locator) {
		SOAPDispatcher.locator = locator;
	}

	public void doService(SOAPEnvelope req, SOAPEnvelope resp) throws SOAPException  {
		String requestType = req.getBody().getChildNodes().item(0).getLocalName();
		String version = null;
		if (req.getBody().getChildNodes().item(0).hasAttributes()) {
			NamedNodeMap nodeMap = req.getBody().getChildNodes().item(0).getAttributes();
			for (int i = 0; i < nodeMap.getLength();i++) {
				if (Node.ATTRIBUTE_NODE == nodeMap.item(i).getNodeType()) {
					System.out.println("attribute found : " + nodeMap.item(i).getNodeName());
				} else {
					System.out.println("Not an attribute");
				}
			}
			version = nodeMap.getNamedItem("version").getNodeValue();
			System.out.println("Received SOAP message of version" + version);
		}

		System.out.println("Received SOAP message of type: " + requestType);
		try
		{
			XmlWebServiceProvider service = null;
			if (version == null) {
				service=locator.findService(requestType);
			} else {
				service=locator.findService(requestType, version);
			}
			if(service != null)
			{
				System.out.println("Service found throug locator: " + service.getClass().getName());
				try {
					resp.getBody().addDocument(service.doService(req.getAsDocument()));
				}
				catch (Throwable ex)
				{
					System.out.println("Caught an exception:");
					ex.printStackTrace();
				}
			}
			else
			{
				createError(resp, "No service found");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace(System.out);
			throw new RuntimeException(ex);
		}
	}

	private void createError(SOAPEnvelope response, String errorText) throws SOAPException {

		SOAPFault fault = response.getBody().addFault();
		fault.setFaultCode("ServiceNotFound");
		fault.setFaultString(errorText);
	}


}
