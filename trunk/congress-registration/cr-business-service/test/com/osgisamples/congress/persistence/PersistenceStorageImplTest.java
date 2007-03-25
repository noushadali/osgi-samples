package com.osgisamples.congress.persistence;

import com.osgisamples.congress.domain.Congress;

import junit.framework.TestCase;

public class PersistenceStorageImplTest extends TestCase {
	private PersistentStorageImpl persistenceStorage;
	
	@Override
	protected void setUp() throws Exception {
		persistenceStorage = PersistentStorageImpl.getInstance();
	}

	public void testGetInstance() {
		assertNotNull(persistenceStorage);
	}

	public void testFindNonExistingCongress() {
		Congress congressToFind = new Congress();
		congressToFind.setId(new Long(99));
		try {
			persistenceStorage.findCongress(congressToFind);
			fail("ObjectNotFoundException should have been thrown");
		} catch (ObjectNotFoundException e) {
			// as designed
		}
	}

	public void testFindExistingCongress() {
		Congress congressToFind = new Congress();
		congressToFind.setId(new Long(1));
		Congress foundCongress = persistenceStorage.findCongress(congressToFind);
		assertNotNull("A congress should have been found",foundCongress);
		assertEquals("The name of the found congress in wrong","NLJug", foundCongress.getName());
	}

}
