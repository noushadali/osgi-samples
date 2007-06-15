package com.osgisamples.congress.business.impl;
import static org.easymock.EasyMock.*;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.business.CongressNotFoundException;
import com.osgisamples.congress.business.RegistrantNotFoundForCongressException;
import com.osgisamples.congress.business.SessionNotAvailableForCongressException;
import com.osgisamples.congress.business.SessionValidationException;
import com.osgisamples.congress.business.impl.CongressManagerImpl;
import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.SessionDao;
import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.dataaccess.exceptions.SessionAllreadyExistsException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.domain.Participant;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.domain.Session;

public class CongressManagerTest extends TestCase {
	private CongressManager congressManager;
	private CongressDao congressDaoMock;
	private RegistrantDao registrantDaoMock;
	private SessionDao sessionDaoMock;

	@Override
	protected void setUp() throws Exception {
		congressDaoMock = createMock(CongressDao.class);
		registrantDaoMock = createMock(RegistrantDao.class);
		sessionDaoMock = createMock(SessionDao.class);
		congressManager = new CongressManagerImpl(congressDaoMock,registrantDaoMock,sessionDaoMock);
	}
	
	public void testStoreCongress() {
		Congress congress = createEasyCongress();
		congressDaoMock.storeCongress(congress);
		replay(new Object[]{congressDaoMock,registrantDaoMock});
		congressManager.storeCongress(congress);
		verify(new Object[]{congressDaoMock,registrantDaoMock});
	}
	
	public void testRegisterNewRegistrantForCongress() {
		
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createEasyCongress());
		expect(registrantDaoMock.loadRegistrantByRegistrationNumber("12345678")).andThrow(
				new ObjectRetrievalFailureException(Registrant.class,"Registration number : 12345678"));
		registrantDaoMock.storeRegistrant(createRegistrant());
		congressDaoMock.storeCongressRegistration(new CongressRegistration(createEasyCongress(),createRegistrant()));
		replay(new Object[]{congressDaoMock,registrantDaoMock});

		Registrant searchRegistrant = createRegistrant();
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		congressManager.registerNewRegistrantForCongress(searchRegistrant, searchCongress);

		verify(new Object[]{congressDaoMock,registrantDaoMock});
	}
	
	public void testRegisterNewRegistrantForNonExistingCongress() {
		expect(congressDaoMock.loadCongressByName("non-existing")).andThrow(new ObjectRetrievalFailureException(Congress.class,"name : non-existing"));
		replay(new Object[]{congressDaoMock,registrantDaoMock});

		Registrant searchRegistrant = createRegistrant();
		Congress searchCongress = new Congress();
		searchCongress.setName("non-existing");
		try {
			congressManager.registerNewRegistrantForCongress(searchRegistrant, searchCongress);
			fail("A CongressNotFoundException should have been thrown");
		} catch (CongressNotFoundException e) {
			// as expected
		}
		verify(new Object[]{congressDaoMock,registrantDaoMock});
	}

	public void testRegisterExistingRegistrantForCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createEasyCongress());
		expect(registrantDaoMock.loadRegistrantByRegistrationNumber("12345678")).andReturn(createRegistrant());
		congressDaoMock.storeCongressRegistration(new CongressRegistration(createEasyCongress(),createRegistrant()));
		replay(new Object[]{congressDaoMock,registrantDaoMock});

		Registrant searchRegistrant = createRegistrant();
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		congressManager.registerNewRegistrantForCongress(searchRegistrant, searchCongress);

		verify(new Object[]{congressDaoMock,registrantDaoMock});
	}

	public void testListAllRegistrantsForCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createCongressWithRegistration());
		replay(new Object[]{congressDaoMock});
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Set<Registrant> registrants = congressManager.listAllRegistrantsForCongress(searchCongress);
		assertEquals(1, registrants.size());
		verify(new Object[]{congressDaoMock});
	}
	
	public void testRegisterSessionForCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createEasyCongress());
		expect(sessionDaoMock.createSession(createNewSession())).andReturn(createSession());
		replay(new Object[]{congressDaoMock,sessionDaoMock});
		
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Session session = createNewSession();
		String sessionId = congressManager.registerNewSessionForCongress(session, searchCongress);
		
		assertEquals("1", sessionId);
		verify(new Object[]{congressDaoMock,sessionDaoMock});
	}
	
	public void testRegisterExistingSessionForCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createEasyCongress());
		expect(sessionDaoMock.createSession(createNewSession())).andThrow(new SessionAllreadyExistsException("The session with name Session1 allready exists"));
		replay(new Object[]{congressDaoMock,sessionDaoMock});

		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Session session = createNewSession();
		try {
			congressManager.registerNewSessionForCongress(session, searchCongress);
			fail("A SessionValidationException should have been thrown");
		} catch (SessionValidationException e) {
			// as expected
		}
		
		verify(new Object[]{congressDaoMock,sessionDaoMock});
	}
	
	public void testRegisterSessionForNonExistingCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andThrow(new CongressNotFoundException("Congress with name nljug-jspring-2007 is not found"));
		replay(new Object[]{congressDaoMock,sessionDaoMock});

		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Session session = createNewSession();
			try {
				congressManager.registerNewSessionForCongress(session, searchCongress);
				fail("A  should have been thrown");
			} catch (CongressNotFoundException e) {
				// as expected
			}
		
		verify(new Object[]{congressDaoMock,sessionDaoMock});
	}

	public void testListAllSessionsForCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createCongressWithSessionAndRegistrant());
		replay(new Object[]{congressDaoMock});
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Set<Session> sessions = congressManager.listAllSessionsForCongress(searchCongress);
		assertEquals(1, sessions.size());
		verify(new Object[]{congressDaoMock});
	}

	public void testUpdateSessionsForRegisteredRegistrantAndRegisteredSessions() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createCongressWithSessionAndRegistrant());
		replay(new Object[]{congressDaoMock});
		
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Registrant registrant = createNewRegistrant();
		Set<Session> sessions = new HashSet<Session>();
		sessions.add(createSession());
		congressManager.updateSessionsForParticipantOfCongress(searchCongress, registrant, sessions);
		
		verify(new Object[]{congressDaoMock});
	}

	public void testUpdateSessionsForNonRegisteredRegistrantAndRegisteredSessions() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createCongressWithSessionAndRegistrant());
		replay(new Object[]{congressDaoMock});
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Registrant nonExistingRegistrantForCongress = new Registrant();
		nonExistingRegistrantForCongress.setName("Unknown");
		nonExistingRegistrantForCongress.setEmailAddress("test@test.nl");
		Set<Session> sessions = new HashSet<Session>();
		sessions.add(createSession());
		
		try {
			congressManager.updateSessionsForParticipantOfCongress(searchCongress, nonExistingRegistrantForCongress, sessions);
			fail("Expected RegistrantNotFoundForCongressException is not thrown");
		} catch (RegistrantNotFoundForCongressException e) {
			// as expected
		}
		
		verify(new Object[]{congressDaoMock});
	}
	
	public void testUpdateSessionsForRegisteredRegistrantAndNonRegisteredSessions() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andReturn(createCongressWithSessionAndRegistrant());
		replay(new Object[]{congressDaoMock});
		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
		Registrant registrant = createNewRegistrant();
		Set<Session> sessions = new HashSet<Session>();
		sessions.add(createSession());
		Session nonExistingSession = new Session();
		nonExistingSession.setName("unknown");
		sessions.add(nonExistingSession);
		
		try {
			congressManager.updateSessionsForParticipantOfCongress(searchCongress, registrant, sessions);
			fail("Expected SessionNotAvailableForCongressException is not thrown");
		} catch (SessionNotAvailableForCongressException e) {
			// as expected
		}
		
		verify(new Object[]{congressDaoMock});
	}

	public void testUpdateSessionsForRegisteredRegistrantAndRegisteredSessionsUnknownCongress() {
		expect(congressDaoMock.loadCongressByName("nljug-jspring-2007")).andThrow(new CongressNotFoundException("Congress with name nljug-jspring-2007 is not found"));
		replay(new Object[]{congressDaoMock,sessionDaoMock});

		Congress searchCongress = new Congress();
		searchCongress.setName("nljug-jspring-2007");
			try {
				congressManager.updateSessionsForParticipantOfCongress(searchCongress, null, null);
				fail("A CongressNotFoundException should have been thrown");
			} catch (CongressNotFoundException e) {
				// as expected
			}
		
		verify(new Object[]{congressDaoMock});
	}

	private Congress createCongressWithSessionAndRegistrant() {
		Congress congress = createCongressWithRegistration();
		Session session = createSession();
		session.setCongress(congress);
		congress.getSessions().add(session);
		return congress;
	}

	private Session createSession() {
		Session session = createNewSession();
		session.setId(new Long(1));
		return session;
	}

	private Session createNewSession() {
		Session session = new Session();
		session.setName("Session 1");
		session.setSummary("Summary for session 1");
		return session;
	}

	private Congress createEasyCongress() {
		Congress congress = new Congress();
		congress.setId(new Long(1));
		congress.setName("nljug-jspring-2007");
		return congress;
	}

	private Congress createCongressWithRegistration() {
		Congress congress = new Congress();
		congress.setId(new Long(1));
		congress.setName("nljug-jspring-2007");
		new CongressRegistration(congress,createRegistrant());
		
		return congress;
	}

	private Registrant createNewRegistrant() {
		Registrant registrant = new Registrant();
		registrant.setCompany("Accenture");
		registrant.setName("Jettro Coenradie");
		registrant.setEmailAddress("jettro.coenradie@accenture.com");
		return registrant;
	}

	private Registrant createRegistrant() {
		Registrant registrant = createNewRegistrant();
		registrant.setId(new Long(1));
		registrant.setRegistrationNumber("12345678");
		Participant participant = new Participant(registrant);
		participant.setId(new Long(1));
		registrant.getCongressRoles().add(participant);
		return registrant;
	}
}
