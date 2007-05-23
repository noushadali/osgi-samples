package com.osgisamples.congress.webservice.registerparticipant;

import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.domain.Session;
import com.osgisamples.congress.provider.XmlWebServiceProvider;
import com.osgisamples.congress.provider.exceptions.ServiceProviderException;
import com.osgisamples.congress.schema.CongressRegistrationRequest;
import com.osgisamples.congress.schema.CongressRegistrationResponse;
import com.osgisamples.congress.schema.ObjectFactory;
import com.osgisamples.congress.webservice.registerparticipant.adapter.CongressRegistrationRequestAdapter;
import com.osgisamples.congress.webservice.registerparticipant.adapter.CongressRegistrationResponseAdapter;
import com.osgisamples.congress.webservice.registerparticipant.dataholder.CongressRegistrationRequestDataHolder;

public class CongressRegistrationWebService implements XmlWebServiceProvider {
	private static final Log logger = LogFactory.getLog(CongressRegistrationWebService.class);
	private ServiceTracker congressManagerTracker;
	
	private class MyValidationEventHandler extends DefaultValidationEventHandler {

		@Override
		public boolean handleEvent(ValidationEvent ve) {
			if (ve.getSeverity()==ValidationEvent.FATAL_ERROR ||  
					ve .getSeverity()==ValidationEvent.ERROR){
				return false;
			} else {
				return true;
			}
		}

	}
	
	public Document doService(final Node request) {
		Document xmlResponse = null;
		JAXBContext context;
		Unmarshaller unmarshaller;
		try {
			context = JAXBContext.newInstance(CongressRegistrationResponse.class.getPackage().getName());
			unmarshaller = context.createUnmarshaller();
			SchemaFactory factory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(getClass().getClassLoader().getResource("WS-INF/CongressRegistration.xsd"));
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(new MyValidationEventHandler());
			
			CongressRegistrationRequest requestObject = (CongressRegistrationRequest) unmarshaller.unmarshal(request);

			CongressRegistrationResponse responseObject = null;
			responseObject = registerCongress(requestObject);

			xmlResponse = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			context.createMarshaller().marshal(responseObject, xmlResponse);
		} catch (UnmarshalException e) {
			logger.info("Unmarshal",e);
			throw new ServiceProviderException("Unmarshal Error while parsing the xml request : " + e.getLinkedException().getMessage());
		} catch (JAXBException e) {
			logger.info("Jaxb",e);
			throw new ServiceProviderException("JAXB Error while parsing the xml request : " + e.getMessage());
		} catch (SAXException e) {
			logger.info("Sax",e);
			throw new ServiceProviderException("Error obtaining or reading the xsd for validating the request : " + e.getMessage());
		} catch (ParserConfigurationException e) {
			logger.info("ParserConfiguration",e);
			throw new ServiceProviderException("Technical exception while configuring the response builder : " + e.getMessage());
		} catch (RuntimeException e) {
			logger.info("RuntimeException",e);
			throw e;
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
			service.updateSessionsForParticipantOfCongress(dh.getCongress(), dh.getRegistrant(), dh.getSessions());
			
			// TODO to be removed
			
			Set<Session> sessions = service.listAllSessionsForCongress(dh.getCongress());
			for (Session session : sessions) {
				logger.info("session : " + session.getName());
				logger.info("listeners : " + session.getListeners().size());
			}
			// till here
			
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
