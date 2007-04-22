package com.osgisamples.congress.webservice.registerparticipant;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.util.tracker.ServiceTracker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.dispatcher.CongressRegistrationRequest;
import com.osgisamples.congress.dispatcher.CongressRegistrationResponse;
import com.osgisamples.congress.dispatcher.ObjectFactory;
import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.webservice.registerparticipant.adapter.CongressRegistrationRequestAdapter;
import com.osgisamples.congress.webservice.registerparticipant.adapter.CongressRegistrationResponseAdapter;
import com.osgisamples.congress.webservice.registerparticipant.dataholder.CongressRegistrationRequestDataHolder;

public class CongressRegistrationWebService implements XmlWebServiceProvider {

	private ServiceTracker congressManagerTracker;
	
	public Document doService(final Node request) {
		Document xmlResponse = null;
		try {
			JAXBContext context = JAXBContext.newInstance(CongressRegistrationResponse.class.getPackage().getName());
			CongressRegistrationRequest requestObject = (CongressRegistrationRequest) context.createUnmarshaller().unmarshal(request);

			CongressRegistrationResponse responseObject = registerCongress(requestObject);

			xmlResponse = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			context.createMarshaller().marshal(responseObject, xmlResponse);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return xmlResponse;
	}

	private CongressRegistrationResponse registerCongress(CongressRegistrationRequest requestObject) {
		CongressRegistrationResponse response = null;
		
		CongressRegistrationRequestAdapter requestAdapter = new CongressRegistrationRequestAdapter(); 
		
		CongressRegistrationRequestDataHolder dh = requestAdapter.adapt(requestObject);
		
		CongressManager service = (CongressManager) congressManagerTracker.getService();
		if(service != null) {
			String registrantNumber = service.registerNewRegistrantForCongress(dh.getRegistrant(), dh.getCongress());
			CongressRegistrationResponseAdapter responseAdapter = new CongressRegistrationResponseAdapter();
			responseAdapter.setRegistrantIdentificationNumber(registrantNumber);
			response = responseAdapter.createOkResponse();
		}
		else {
			response = new ObjectFactory().createCongressRegistrationResponse();
		}
		return response;
	}

	public void setCongressManagerTracker(ServiceTracker congressManagerTracker) {
		this.congressManagerTracker = congressManagerTracker;
	}

}
