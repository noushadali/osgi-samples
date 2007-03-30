package com.osgisamples.congress.business;

import java.util.Set;

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
	
	/**
	 * Returns a set with all registrants for the provided congress, if the data provided is not sufficient to
	 * identify a unique Congress a CongressNotFoundException is thrown.
	 * @param congress Congress item that contains items we can search on (mainly id or name)
	 * @return Set with Registrants for the congress
	 * @throws CongressNotFoundException Thrown if the provided congress information is not sifficient to uniquely
	 * identify a congress.
	 */
	public Set<Registrant> listAllRegistrantsForCongress(Congress congress) throws CongressNotFoundException;
}
