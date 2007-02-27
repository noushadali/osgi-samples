package org.osgisamples.congress.dispatcher;

import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPEnvelope;

public class SOAPDispatcher implements Dispatcher {

	public void method(SOAPEnvelope req, SOAPEnvelope resp) throws SOAPException  {
		System.out.println("Received SOAP message of type: " + req.getBody().getLocalName());
	}

	
	
}
