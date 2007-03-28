package com.osgisamples.congress.business;

import com.osgisamples.congress.business.exceptions.CongressNotFoundException;
import com.osgisamples.congress.business.exceptions.RegistrantValidationException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;

/**
 * @author Jettro.Coenradie
 * Manager object that handles all calls to functionality related to the congress registration process.
 * It contains methods for storing new/updated congresses, registering participants, stands, sessions, etc.
 */
public interface CongressManager {
	/**
	 * Store a new or updated congress. 
	 * TODO validation exception thrown
	 * @param congress Congress item to store
	 */
	public void storeCongress(Congress congress);
	

	/**
	 * Creates a new CongressRegistration object for the provided (new) Registrant and congress. You do not need
	 * to provided stored instances of the registrant and congress. If the provided data is not sufficient to
	 * create a new registrant or find an existing registrant or congress, and exception is thrown.
	 * @param registrant Registrant object used to create a new Registrant or find an existing one
	 * @param congress Congress object used to find the stored congress
	 * @throws CongressNotFoundException thrown if the provided data is not enough to determine exactly one Congress
	 * @throws RegistrantValidationException thrown if the provided registrant is not correct
	 */
	public void registerNewRegistrantForCongress(Registrant registrant, Congress congress)
		throws CongressNotFoundException, RegistrantValidationException;
}
