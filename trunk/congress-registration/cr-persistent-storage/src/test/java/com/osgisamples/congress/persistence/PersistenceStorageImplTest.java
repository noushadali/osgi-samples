package com.osgisamples.congress.persistence;

import junit.framework.TestCase;

import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;

public class PersistenceStorageImplTest extends TestCase {
    private PersistentStorageImpl persistenceStorage;
    
    @Override
    protected void setUp() throws Exception {
        persistenceStorage = PersistentStorageImpl.getInstance();
    }
    
    public void testGetInstance() {
        assertNotNull(persistenceStorage);
    }
    
    public void testLoadNonExistingCongressById() {
        try {
            persistenceStorage.loadCongressById(new Long(99));
            fail("ObjectNotFoundException should have been thrown");
        } catch (ObjectNotFoundException e) {
            // as designed
        }
    }
    
    public void testLoadExistingCongressById() {
        Congress foundCongressByName = persistenceStorage.loadCongressByName("NLJug");
        Congress foundCongress = persistenceStorage.loadCongressById(foundCongressByName.getId());
        assertNotNull("A congress should have been found",foundCongress);
        assertEquals("The name of the found congress in wrong","NLJug", foundCongress.getName());
    }
    
    public void testLoadNonExistingCongressByName() {
        try {
            persistenceStorage.loadCongressByName("non-existing");
            fail("ObjectNotFoundException should have been thrown");
        } catch (ObjectNotFoundException e) {
            // as designed
        }
    }
    
    public void testLoadExistingCongressByName() {
        Congress foundCongress = persistenceStorage.loadCongressByName("NLJug");
        assertNotNull("A congress should have been found",foundCongress);
        assertEquals("The name of the found congress in wrong","NLJug", foundCongress.getName());
    }
    
    public void testStoreNewRegistrant() {
        Registrant registrant = new Registrant();
        registrant.setCompany("Test");
        registrant.setEmailAddress("test@test.nl");
        registrant.setName("Test user");
        persistenceStorage.storeRegistrant(registrant);
        assertNotNull(registrant.getId());
        assertNotNull(registrant.getRegistrationNumber());
    }
    
    public void testStoreNewCongress() {
        Congress congress = new Congress();
        congress.setName("TestCongress");
        persistenceStorage.storeCongress(congress);
        assertNotNull(congress.getId());
    }
}
