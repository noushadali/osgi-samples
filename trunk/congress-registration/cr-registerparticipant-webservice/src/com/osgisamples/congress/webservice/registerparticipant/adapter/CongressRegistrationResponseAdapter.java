package com.osgisamples.congress.webservice.registerparticipant.adapter;

import generated.CongressRegistrationResponse;
import generated.ObjectFactory;

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
