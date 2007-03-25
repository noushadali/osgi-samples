package com.osgisamples.congress.domain;

public abstract class SessionRegistration extends BaseDomain {
	private Session registeredSession;

	public Session getRegisteredSession() {
		return registeredSession;
	}

	public void setRegisteredSession(Session registeredSession) {
		this.registeredSession = registeredSession;
	}
}
