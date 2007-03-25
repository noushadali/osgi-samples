package com.osgisamples.congress.persistence;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.osgisamples.congress.domain.Congress;

public class PersistentStorageImpl {
	private static PersistentStorageImpl storageInstance;
	private static Map<Long, Congress> congresses = new HashMap<Long, Congress>();
	
	private PersistentStorageImpl() {
		Congress nljug = new Congress();
		nljug.setId(new Long(1));
		nljug.setName("NLJug");
		nljug.setDescription("This is the biggest java conference from the netherlands");
		nljug.setStartDateTime(new GregorianCalendar(2007,5,18,9,0));
		nljug.setEndDateTime(new GregorianCalendar(2007,5,18,18,0));
		congresses.put(nljug.getId(), nljug);
	}
	
	public static PersistentStorageImpl getInstance() {
		if (storageInstance == null) {
			synchronized ("instance") {
				if (storageInstance == null) {
					storageInstance = new PersistentStorageImpl();
				}
			}
		}
		return storageInstance;
	}
	
	public Congress findCongress(final Congress congress) throws ObjectNotFoundException {
		if (congress.getId() == null || !congresses.containsKey(congress.getId())) {
			throw new ObjectNotFoundException(Congress.class,congress);
		}
		return congresses.get(congress.getId());
	}
}
