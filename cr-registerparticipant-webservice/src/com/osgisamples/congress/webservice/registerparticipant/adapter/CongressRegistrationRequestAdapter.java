package com.osgisamples.congress.webservice.registerparticipant.adapter;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.schema.CongressRegistrationRequest;
import com.osgisamples.congress.webservice.registerparticipant.dataholder.CongressRegistrationRequestDataHolder;

public class CongressRegistrationRequestAdapter {

	public CongressRegistrationRequestDataHolder adapt(final CongressRegistrationRequest request) {
		CongressRegistrationRequestDataHolder dataHolder = new CongressRegistrationRequestDataHolder();
		dataHolder.setCongress(adaptCongress(request.getCongress()));
		dataHolder.setRegistrant(adaptRegistrant(request.getRegistrant()));
		
		return dataHolder;
	}

	private Registrant adaptRegistrant(com.osgisamples.congress.schema.Registrant xmlRegistrant) {
		Registrant registrant = new Registrant();
		registrant.setName(xmlRegistrant.getFirstName() + " " + xmlRegistrant.getLastName());
		registrant.setEmailAddress(xmlRegistrant.getEmail());
		return registrant;
	}

	private Congress adaptCongress(com.osgisamples.congress.schema.Congress xmlCongress) {
		Congress congress = new Congress();
		if (xmlCongress.getCongressId() != null) {
			congress.setId(new Long(xmlCongress.getCongressId()));
		} else {
			congress.setName(xmlCongress.getCongressName());
		}
		return congress;
	}
	
}
