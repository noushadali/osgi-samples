package com.osgisamples.congress.persistence;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.osgisamples.congress.dataaccess.exceptions.SessionAllreadyExistsException;
import com.osgisamples.congress.dataaccess.exceptions.SessionValidationException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.domain.ListenerSessionRegistration;
import com.osgisamples.congress.domain.Participant;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.domain.Session;

public class PersistentStorageImpl {
	private static PersistentStorageImpl storageInstance;
	private Map<Long, Congress> congresses = new HashMap<Long, Congress>();
	private Map<Long, Registrant> registrants = new HashMap<Long, Registrant>();
	private IdGenerator myIdGenerator = new IdGenerator();
	
	private PersistentStorageImpl() {
		Congress nljug = new Congress();
		nljug.setId(myIdGenerator.nextId());
		nljug.setName("NLJug");
		nljug.setDescription("This is the biggest java conference from the netherlands");
		nljug.setStartDateTime(new GregorianCalendar(2007,5,18,9,0));
		nljug.setEndDateTime(new GregorianCalendar(2007,5,18,18,0));
		congresses.put(nljug.getId(), nljug);
		
		Registrant allard = new Registrant();
		allard.setCompany("Accenture Technology Solutions");
		allard.setEmailAddress("allard.buijze@accenture.com");
		allard.setId(myIdGenerator.nextId());
		allard.setRegistrationNumber(String.valueOf(1000000L + allard.getId()));
		allard.setName("Allard Buijze");
		Participant allardAsParticipant = new Participant(allard);
		allardAsParticipant.setId(myIdGenerator.nextId());
		allard.getCongressRoles().add(allardAsParticipant);
		new CongressRegistration(nljug,allard);

		Registrant jettro = new Registrant();
		jettro.setCompany("Accenture Technology Solutions");
		jettro.setEmailAddress("jettro.coenradie@accenture.com");
		jettro.setId(myIdGenerator.nextId());
		jettro.setRegistrationNumber(String.valueOf(1000000L + jettro.getId()));
		jettro.setName("Jettro Coenradie");
		Participant jettroAsParticipant = new Participant(jettro);
		jettroAsParticipant.setId(myIdGenerator.nextId());
		jettro.getCongressRoles().add(jettroAsParticipant);
		new CongressRegistration(nljug,jettro);
		
		Session osgiwebservices = new Session();
		osgiwebservices.setId(myIdGenerator.nextId());
		osgiwebservices.setName("OSGi and web service versioning");
		osgiwebservices.setSummary("This presentation explains everything about web service versioning and osgi");
		osgiwebservices.setCongress(nljug);
		nljug.getSessions().add(osgiwebservices);
		new ListenerSessionRegistration(osgiwebservices,allardAsParticipant);
		new ListenerSessionRegistration(osgiwebservices,jettroAsParticipant);

		Session osgiEnterprise = new Session();
		osgiEnterprise.setId(myIdGenerator.nextId());
		osgiEnterprise.setName("Enterprise OSGi");
		osgiEnterprise.setSummary("What can OSGi mean to the enterprise");
		osgiEnterprise.setCongress(nljug);
		nljug.getSessions().add(osgiEnterprise);
}
	
	public static PersistentStorageImpl getInstance() {
		if (storageInstance == null) {
			synchronized ("instance") {
				if (storageInstance == null) {
					storageInstance = new PersistentStorageImpl();
				}
			}
		}
		return storageInstance;
	}
	
	public Congress loadCongressById(final Long congressId) throws ObjectNotFoundException {
		if (congressId == null || !congresses.containsKey(congressId)) {
			throw new ObjectNotFoundException(Congress.class,congressId);
		}
		return congresses.get(congressId);
	}

	public Congress loadCongressByName(final String congressName) throws ObjectNotFoundException {
		if (congressName == null) {
			throw new ObjectNotFoundException(Congress.class,congressName);
		}
		Congress foundCongress = null;
		for (Entry<Long,Congress> congressesEntry : congresses.entrySet()) {
			Congress congress = congressesEntry.getValue();
			if (congress.getName().equals(congressName)) {
				foundCongress = congress;
				break;
			}
		}
		if (foundCongress == null) {
			throw new ObjectNotFoundException(Congress.class,congressName);
		}
		
		return foundCongress;
	}
	
	public Registrant loadRegistrantById(final Long registrantId) throws ObjectNotFoundException {
		if (registrantId == null || !registrants.containsKey(registrantId)) {
			throw new ObjectNotFoundException(Registrant.class,registrantId);
		}
		return registrants.get(registrantId);
	}

	public Registrant loadRegistrantByRegistrationNumber(final String registrationNumber) throws ObjectNotFoundException {
		if (registrationNumber == null) {
			throw new ObjectNotFoundException(Registrant.class,registrationNumber);
		}
		Registrant foundRegistrant = null;
		for (Entry<Long,Registrant> registrantEntry : registrants.entrySet()) {
			Registrant registrant = registrantEntry.getValue();
			if (registrant.getRegistrationNumber().equals(registrationNumber)) {
				foundRegistrant = registrant;
				break;
			}
		}
		if (foundRegistrant == null) {
			throw new ObjectNotFoundException(Registrant.class,registrationNumber);
		}
		
		return foundRegistrant;
	}

	public void storeRegistrant(final Registrant registrant) {
		if (registrant.getId() == null ) {
			Registrant existingRegistrant = checkForExistingRegistrantByEmail(registrant.getEmailAddress());
			if (existingRegistrant == null) {
				registrant.setId(myIdGenerator.nextId());
				registrant.setRegistrationNumber(String.valueOf(1000000L+registrant.getId().longValue()));
				Participant participant = new Participant(registrant);
				participant.setId(myIdGenerator.nextId());
				registrant.getCongressRoles().add(participant);
				registrants.put(registrant.getId(), registrant);
			} else {
				registrant.setCompany(existingRegistrant.getCompany());
				registrant.setId(existingRegistrant.getId());
				registrant.setName(existingRegistrant.getName());
				registrant.setRegistrationNumber(existingRegistrant.getRegistrationNumber());
				registrant.setCongressRoles(existingRegistrant.getCongressRoles());
			}
		}
	}
	
	private Registrant checkForExistingRegistrantByEmail(String email) {
		Registrant foundRegistrant = null;
		for (Entry<Long,Registrant> registrantEntry : registrants.entrySet()) {
			Registrant registrant = registrantEntry.getValue();
			if (registrant.getEmailAddress().equals(email)) {
				foundRegistrant = registrant;
				break;
			}
		}
		return foundRegistrant;
	}
	
	public void storeCongress(final Congress congress) {
		if (congress.getId() == null) {
			congress.setId(myIdGenerator.nextId());
		}
		congresses.put(congress.getId(), congress);
	}
	
	public void createSession(Session session) {
		if (session.getId() != null) {
			throw new SessionAllreadyExistsException("The session has an id which is not possible for a new session");
		}
		if (session.getCongress() == null) {
			throw new SessionValidationException("A new session must have a congress");
		}
		if (session.getName() == null || "".equals(session.getName())) {
			throw new SessionValidationException("A new session must have a name");
		}
		Congress foundCongress = loadCongressById(session.getCongress().getId());
		session.setId(myIdGenerator.nextId());
		session.setCongress(foundCongress);
		foundCongress.getSessions().add(session);
	}
	
	private class IdGenerator {
		private long previousId = 1;
		public synchronized Long nextId() {
			return ++previousId;
		}
	}
}
