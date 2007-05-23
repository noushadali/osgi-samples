package com.osgisamples.congress.webservice.registerparticipant.adapter;

import com.osgisamples.congress.schema.CongressRegistrationResponse;
import com.osgisamples.congress.schema.ObjectFactory;

public class CongressRegistrationResponseAdapter {
	private String registrantNumber = "";
	
	public void setRegistrantIdentificationNumber(String registrantNumber) {
		this.registrantNumber = registrantNumber;
	}
	
	public CongressRegistrationResponse createOkResponse() {
		CongressRegistrationResponse response = new ObjectFactory().createCongressRegistrationResponse();
		response.setRegistrationCode(registrantNumber);
		return response;
	}
	
}
