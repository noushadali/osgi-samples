package com.osgisamples.congress.business;

import com.osgisamples.congress.business.exceptions.CongressNotFoundException;
import com.osgisamples.congress.business.exceptions.ParticipantNotFoundException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Participant;

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
	 * Creates a new CongressRegistration object for the provided participant and congress. You do not need
	 * to provided stored instances of the Participant and congress. If the provided data is not sufficient to
	 * determine the exact registrant or congress, and exception is thrown.
	 * @param participant Participant object used to find the stored participant
	 * @param congress Congress object used to find the stored congress
	 * @throws ParticipantNotFoundException thrown if not exactly one participant can be determined by the provided Participant
	 * @throws CongressNotFoundException thrown if not exactly one congress can be determined by the provided Congress
	 */
	public void registerParticipantForCongress(Participant participant, Congress congress)
		throws ParticipantNotFoundException, CongressNotFoundException;
}
