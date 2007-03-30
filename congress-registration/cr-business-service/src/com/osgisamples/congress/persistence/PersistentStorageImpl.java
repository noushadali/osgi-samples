package com.osgisamples.congress.persistence;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.domain.Registrant;

public class PersistentStorageImpl {
	private static PersistentStorageImpl storageInstance;
	private Map<Long, Congress> congresses = new HashMap<Long, Congress>();
	private Map<Long, Registrant> registrants = new HashMap<Long, Registrant>();
	private IdGenerator myIdGenerator = new IdGenerator();
	
	private PersistentStorageImpl() {
		Congress nljug = new Congress();
		nljug.setId(1L);
		nljug.setName("NLJug");
		nljug.setDescription("This is the biggest java conference from the netherlands");
		nljug.setStartDateTime(new GregorianCalendar(2007,5,18,9,0));
		nljug.setEndDateTime(new GregorianCalendar(2007,5,18,18,0));
		congresses.put(nljug.getId(), nljug);
		
		Registrant allard = new Registrant();
		allard.setCompany("Accenture Technology Solutions");
		allard.setEmailAddress("allard.buijze@accenture.com");
		allard.setId(1L);
		allard.setRegistrationNumber("1000001");
		allard.setName("Allard Buijze");
		
		new CongressRegistration(nljug,allard);
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
			if (registrant.getName().equals(registrationNumber)) {
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
		if (registrant.getId() == null) {
			registrant.setId(myIdGenerator.nextId());
		}
		if (registrant.getRegistrationNumber() == null) {
			registrant.setRegistrationNumber(String.valueOf(1000000L+registrant.getId().longValue()));
		}
		registrants.put(registrant.getId(), registrant);
	}
	
	public void storeCongress(final Congress congress) {
		if (congress.getId() == null) {
			congress.setId(myIdGenerator.nextId());
		}
		congresses.put(congress.getId(), congress);
	}
	
	private class IdGenerator {
		private long previousId;
		public synchronized Long nextId() {
			return ++previousId;
		}
	}
}
