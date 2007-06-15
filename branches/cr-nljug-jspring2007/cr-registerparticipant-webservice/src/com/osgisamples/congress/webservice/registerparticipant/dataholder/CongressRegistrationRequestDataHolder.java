package com.osgisamples.congress.webservice.registerparticipant.dataholder;

import java.util.HashSet;
import java.util.Set;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.domain.Session;

public class CongressRegistrationRequestDataHolder {

	private Registrant registrant;
	private Congress congress;
	private Set<Session> sessions = new HashSet<Session>();
	
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
	public Set<Session> getSessions() {
		return sessions;
	}
	public void addSession(Session session) {
		this.sessions.add(session);
	}
	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}
	
}
