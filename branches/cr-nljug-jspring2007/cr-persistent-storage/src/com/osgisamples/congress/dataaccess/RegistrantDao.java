package com.osgisamples.congress.dataaccess;

import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.domain.Registrant;

public interface RegistrantDao {
	/**
	 * Storing can mean update or insert, if we insert a new Registrant, we 
	 * need to create a registration number
	 * @param registrant Registrant to store
	 */
	public void storeRegistrant(Registrant registrant);
	public Registrant loadRegistrant(Long registrantId) throws ObjectRetrievalFailureException;
	public Registrant loadRegistrantByRegistrationNumber(String registrationNumber) throws ObjectRetrievalFailureException;
}
