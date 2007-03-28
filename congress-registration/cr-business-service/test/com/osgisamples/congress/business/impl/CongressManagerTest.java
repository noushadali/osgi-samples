package com.osgisamples.congress.business.impl;
import static org.easymock.EasyMock.*;
import junit.framework.TestCase;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.business.CongressNotFoundException;
import com.osgisamples.congress.business.impl.CongressManagerImpl;
import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.domain.Registrant;

public class CongressManagerTest extends TestCase {
	private CongressManager congressManager;
	private CongressDao congressDaoMock;
	private RegistrantDao registrantDaoMock;

	@Override
	protected void setUp() throws Exception {
		congressDaoMock = createMock(CongressDao.class);
		registrantDaoMock = createMock(RegistrantDao.class);
		congressManager = new CongressManagerImpl(congressDaoMock,registrantDaoMock);
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

	private Congress createEasyCongress() {
		Congress congress = new Congress();
		congress.setId(new Long(1));
		congress.setName("nljug-jspring-2007");
		return congress;
	}

	private Registrant createRegistrant() {
		Registrant registrant = new Registrant();
		registrant.setId(new Long(1));
		registrant.setCompany("Accenture");
		registrant.setName("Jettro Coenradie");
		registrant.setEmailAddress("jettro.coenradie@accenture.com");
		registrant.setRegistrationNumber("12345678");
		return registrant;
	}
}
