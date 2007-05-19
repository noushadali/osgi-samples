package com.osgisamples.congress.webservice.registerparticipant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.osgi.util.tracker.ServiceTracker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.schema.CongressRegistrationRequest;
import com.osgisamples.congress.schema.CongressRegistrationResponse;
import com.osgisamples.congress.schema.ObjectFactory;
import com.osgisamples.congress.webservice.registerparticipant.adapter.CongressRegistrationRequestAdapter;
import com.osgisamples.congress.webservice.registerparticipant.adapter.CongressRegistrationResponseAdapter;
import com.osgisamples.congress.webservice.registerparticipant.dataholder.CongressRegistrationRequestDataHolder;

public class CongressRegistrationWebService implements XmlWebServiceProvider {

	private ServiceTracker congressManagerTracker;
	
	private class MyValidationEventHandler extends DefaultValidationEventHandler {

		@Override
		public boolean handleEvent(ValidationEvent ve) {
			if (ve.getSeverity()==ValidationEvent.FATAL_ERROR ||  
					ve .getSeverity()==ValidationEvent.ERROR){
				throw new RuntimeException("Error validating XML: " + ve.getMessage());
			}
			else {
				return true;
			}
		}

	}
	
	public Document doService(final Node request) {
		Document xmlResponse = null;
		try {
			JAXBContext context = JAXBContext.newInstance(CongressRegistrationResponse.class.getPackage().getName());
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			SchemaFactory factory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(getClass().getClassLoader().getResource("WS-INF/CongressRegistration.xsd"));
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(new MyValidationEventHandler());
			
			CongressRegistrationRequest requestObject = (CongressRegistrationRequest) unmarshaller.unmarshal(request);

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
