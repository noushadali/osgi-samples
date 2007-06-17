package com.osgisamples.congress.dataaccess.impl;

import com.osgisamples.congress.persistence.PersistentStorageImpl;

public class BaseDao {
	protected PersistentStorageImpl getPersistentStorage() {
		return PersistentStorageImpl.getInstance();
	}
}
