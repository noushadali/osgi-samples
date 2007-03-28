package com.osgisamples.congress.business.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.business.exceptions.CongressNotFoundException;
import com.osgisamples.congress.business.exceptions.RegistrantValidationException;
import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.domain.Registrant;

public class CongressManagerImpl implements CongressManager {
	private static Log logger = LogFactory.getLog(CongressManagerImpl.class);
	private CongressDao congressDao;
	private RegistrantDao registrantDao;
	
	public CongressManagerImpl(final CongressDao congressDao, final RegistrantDao registrantDao) {
		this.congressDao = congressDao;
		this.registrantDao = registrantDao;
	}
	
	public void storeCongress(Congress congress) {
		congressDao.storeCongress(congress);
	}

	public void registerNewRegistrantForCongress(final Registrant registrant, Congress searchCongress) 
			throws CongressNotFoundException, RegistrantValidationException {
		Congress congress;
		try {
			congress = congressDao.loadCongressByName(searchCongress.getName());
		} catch (ObjectRetrievalFailureException e) {
			logger.info(e.getMessage());
			throw new CongressNotFoundException(e.getMessage());
		}
		Registrant toRegisterRegistrant;
		try {
			toRegisterRegistrant = registrantDao.loadRegistrantByRegistrationNumber(registrant.getRegistrationNumber());
		} catch (ObjectRetrievalFailureException e) {
			logger.debug(e.getMessage());
			registrantDao.storeRegistrant(registrant);
			toRegisterRegistrant = registrant;
		}

		CongressRegistration congressRegistration = new CongressRegistration(congress,toRegisterRegistrant);
		congressDao.storeCongressRegistration(congressRegistration);
	}
}
