package com.osgisamples.congress.webservice.registerparticipant.adapter;

import generated.CongressRegistrationResponse;
import generated.ObjectFactory;

public class CongressRegistrationResponseAdapter {

	public CongressRegistrationResponse createOkResponse() {
		CongressRegistrationResponse response = new ObjectFactory().createCongressRegistrationResponse();
		response.setRegistrationCode(1);
		return response;
	}
	
}
