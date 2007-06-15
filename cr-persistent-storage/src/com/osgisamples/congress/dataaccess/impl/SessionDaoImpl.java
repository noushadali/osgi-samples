package com.osgisamples.congress.dataaccess.impl;

import com.osgisamples.congress.dataaccess.SessionDao;
import com.osgisamples.congress.dataaccess.exceptions.SessionAllreadyExistsException;
import com.osgisamples.congress.domain.ListenerSessionRegistration;
import com.osgisamples.congress.domain.Participant;
import com.osgisamples.congress.domain.Session;

public class SessionDaoImpl extends BaseDao implements SessionDao {

	public void createListenerSessionRegistration(Session session,Participant participant) {
		new ListenerSessionRegistration(session,participant);
	}

	public Session createSession(Session session) throws SessionAllreadyExistsException {
		getPersistentStorage().createSession(session);
		return session;
	}
}
