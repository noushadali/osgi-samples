package com.osgisamples.congress.dataaccess.impl;

import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.persistence.ObjectNotFoundException;

public class CongressDaoImpl extends BaseDao implements CongressDao {

	public Congress loadCongress(Long congressId) throws ObjectRetrievalFailureException {
		Congress foundCongress = null;
		try {
			foundCongress = getPersistentStorage().loadCongressById(congressId);
		} catch (ObjectNotFoundException e) {
			throw new ObjectRetrievalFailureException(Congress.class,congressId.toString());
		}
		return foundCongress;
	}

	public Congress loadCongressByName(String congressName) throws ObjectRetrievalFailureException {
		Congress foundCongress = null;
		try {
			foundCongress = getPersistentStorage().loadCongressByName(congressName);
		} catch (ObjectNotFoundException e) {
			throw new ObjectRetrievalFailureException(Congress.class,congressName);
		}
		return foundCongress;
	}

	public void storeCongress(Congress congress) {
		getPersistentStorage().storeCongress(congress);
	}

	public void storeCongressRegistration(CongressRegistration congressRegistration) {
		Congress congress = congressRegistration.getRegisteredCongress();
		congress.getRegistrations().add(congressRegistration);
		storeCongress(congress);
	}

}
