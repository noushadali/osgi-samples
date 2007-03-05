package org.osgisamples.congress.dispatcher;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;

import org.apache.axis.message.SOAPEnvelope;
import org.osgisamples.congress.servicelocator.ServiceLocator;
import org.osgisamples.provider.XmlWebServiceProvider;

public class SOAPDispatcher implements Dispatcher {

	private static ServiceLocator locator;

	public static void setServiceLocator(ServiceLocator locator) {
		SOAPDispatcher.locator = locator; 
	}
	
	public void doService(SOAPEnvelope req, SOAPEnvelope resp) throws SOAPException  {
		String requestType = req.getBody().getChildNodes().item(0).getLocalName();
		System.out.println("Received SOAP message of type: " + requestType);
		try
		{
			XmlWebServiceProvider service = locator.findService(requestType);
			if(service != null)
			{
				System.out.println("Service found throug locator: " + service.getClass().getName());
				resp.getBody().addDocument(service.doService(req.getAsDocument()));
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
