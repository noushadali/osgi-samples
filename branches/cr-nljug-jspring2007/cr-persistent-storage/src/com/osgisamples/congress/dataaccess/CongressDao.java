package com.osgisamples.congress.dataaccess;

import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;

public interface CongressDao {
	public void storeCongress(Congress congress);
	public void storeCongressRegistration(CongressRegistration congressRegistration);
	public Congress loadCongress(Long congressId) throws ObjectRetrievalFailureException;
	public Congress loadCongressByName(String congressName) throws ObjectRetrievalFailureException;
}
