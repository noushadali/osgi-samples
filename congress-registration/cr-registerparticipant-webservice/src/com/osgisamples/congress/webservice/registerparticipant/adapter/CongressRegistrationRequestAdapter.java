package com.osgisamples.congress.webservice.registerparticipant.adapter;

import generated.CongressRegistrationRequest;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.webservice.registerparticipant.dataholder.CongressRegistrationRequestDataHolder;

public class CongressRegistrationRequestAdapter {

	public CongressRegistrationRequestDataHolder adapt(final CongressRegistrationRequest request) {
		CongressRegistrationRequestDataHolder dataHolder = new CongressRegistrationRequestDataHolder();
		dataHolder.setCongress(adaptCongress(request.getCongress()));
		dataHolder.setRegistrant(adaptRegistrant(request.getRegistrant()));
		
		return dataHolder;
	}

	private Registrant adaptRegistrant(generated.Registrant xmlRegistrant) {
		Registrant registrant = new Registrant();
		registrant.setName(xmlRegistrant.getFirstName() + " " + xmlRegistrant.getLastName());
		registrant.setEmailAddress(xmlRegistrant.getEmail());
		return registrant;
	}

	private Congress adaptCongress(generated.Congress xmlCongress) {
		Congress congress = new Congress();
		congress.setId(new Long(xmlCongress.getCongressId()));
		congress.setName(xmlCongress.getCongressName());
		return congress;
	}
	
}
