package org.osgisamples.congress.dispatcher;

import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPEnvelope;

public interface Dispatcher {

	public void method(SOAPEnvelope req, SOAPEnvelope resp) throws SOAPException; 
}
