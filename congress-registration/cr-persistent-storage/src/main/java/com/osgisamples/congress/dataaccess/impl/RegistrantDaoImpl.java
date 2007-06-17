package com.osgisamples.congress.dataaccess.impl;

import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.persistence.ObjectNotFoundException;

public class RegistrantDaoImpl extends BaseDao implements RegistrantDao {

	public Registrant loadRegistrant(Long registrantId) throws ObjectRetrievalFailureException {
		Registrant foundRegistrant = null;
		try {
			foundRegistrant = getPersistentStorage().loadRegistrantById(registrantId);
		} catch (ObjectNotFoundException e) {
			throw new ObjectRetrievalFailureException(Registrant.class,e.getMessage());
		}
		return foundRegistrant;
	}

	public Registrant loadRegistrantByRegistrationNumber(String registrationNumber) throws ObjectRetrievalFailureException {
		Registrant foundRegistrant = null;
		try {
			foundRegistrant = getPersistentStorage().loadRegistrantByRegistrationNumber(registrationNumber);
		} catch (ObjectNotFoundException e) {
			throw new ObjectRetrievalFailureException(Registrant.class,e.getMessage());
		}
		return foundRegistrant;
	}

	public void storeRegistrant(Registrant registrant) {
		getPersistentStorage().storeRegistrant(registrant);
	}

}
