package com.osgisamples.congress.webservice.registerparticipant;

import generated.CongressRegistrationRequest;
import generated.CongressRegistrationResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.provider.XmlWebServiceProvider;

public class CongressRegistrationWebService implements XmlWebServiceProvider {

	CongressManager congressManager;
	
	public Document doService(final Document request) {
		Document xmlResponse = null;
		try {
			JAXBContext context = JAXBContext.newInstance(CongressRegistrationResponse.class.getPackage().getName());
			CongressRegistrationRequest requestObject =  (CongressRegistrationRequest) context.createUnmarshaller().unmarshal(request);

			CongressRegistrationResponse responseObject = registerCongress(requestObject);

			xmlResponse = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			context.createMarshaller().marshal(responseObject, xmlResponse);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return xmlResponse;
	}

	private CongressRegistrationResponse registerCongress(CongressRegistrationRequest requestObject) {
		// adapt the request object
//		congressManager.
		return null;
	}

}
