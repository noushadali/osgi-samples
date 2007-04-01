package com.osgisamples.congress.business.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.business.CongressNotFoundException;
import com.osgisamples.congress.business.RegistrantValidationException;
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

	public String registerNewRegistrantForCongress(final Registrant registrant, Congress searchCongress) 
			throws CongressNotFoundException, RegistrantValidationException {
		Congress congress = loadCongress(searchCongress);
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
		return toRegisterRegistrant.getRegistrationNumber();
	}

	public Set<Registrant> listAllRegistrantsForCongress(final Congress searchCongress) throws CongressNotFoundException {
		Congress congress = loadCongress(searchCongress);
		Set<CongressRegistration> congressRegistrations = congress.getRegistrations();
		Set<Registrant> registrants = new HashSet<Registrant>();
		for (CongressRegistration congressRegistration : congressRegistrations) {
			registrants.add(congressRegistration.getRegistrant());
		}
		return registrants;
	}

	private Congress loadCongress(Congress searchCongress) throws CongressNotFoundException {
		Congress congress;
		try {
			congress = congressDao.loadCongressByName(searchCongress.getName());
		} catch (ObjectRetrievalFailureException e) {
			logger.info(e.getMessage());
			throw new CongressNotFoundException(e.getMessage());
		}
		return congress;
	}
}
