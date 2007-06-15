package com.osgisamples.congress.webservice.registerparticipant.adapter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.domain.Session;
import com.osgisamples.congress.schema.CongressRegistrationRequest;
import com.osgisamples.congress.schema.Sessions;
import com.osgisamples.congress.webservice.registerparticipant.dataholder.CongressRegistrationRequestDataHolder;

public class CongressRegistrationRequestAdapter {

	public CongressRegistrationRequestDataHolder adapt(final CongressRegistrationRequest request) {
		CongressRegistrationRequestDataHolder dataHolder = new CongressRegistrationRequestDataHolder();
		dataHolder.setCongress(adaptCongress(request.getCongress()));
		dataHolder.setRegistrant(adaptRegistrant(request.getRegistrant()));
		dataHolder.setSessions(adaptSessions(request.getSessions()));
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
	
	private Set<Session> adaptSessions(Sessions xmlSessions) {
		Set<Session> sessions = new HashSet<Session>();
		List<com.osgisamples.congress.schema.Session> xmlSessionsList = xmlSessions.getSession();
		for (Iterator<com.osgisamples.congress.schema.Session> iter = xmlSessionsList.iterator(); iter.hasNext();) {
			com.osgisamples.congress.schema.Session xmlSession = iter.next();
			Session session = new Session();
			if (xmlSession.getSessionId() != null) {
				session.setId(new Long(xmlSession.getSessionId()));
			}
			session.setName(xmlSession.getSessionName());
			sessions.add(session);
		}
		return sessions;
	}
}
