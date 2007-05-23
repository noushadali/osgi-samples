package com.osgisamples.congress.webservice.registerparticipant.dataholder;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;

public class CongressRegistrationRequestDataHolder {

	private Registrant registrant;
	private Congress congress;
	
	public Congress getCongress() {
		return congress;
	}
	public void setCongress(Congress congress) {
		this.congress = congress;
	}
	public Registrant getRegistrant() {
		return registrant;
	}
	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}
	
	
	
}
