package com.osgisamples.congress.dataaccess;

import com.osgisamples.congress.dataaccess.exceptions.SessionAllreadyExistsException;
import com.osgisamples.congress.domain.Participant;
import com.osgisamples.congress.domain.Session;

public interface SessionDao {
	public Session createSession(Session session) throws SessionAllreadyExistsException;
	public void createListenerSessionRegistration(Session session, Participant participant);
}
