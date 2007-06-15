package com.osgisamples.congress.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class SessionRegistration extends BaseDomain {
	private Session registeredSession;

	public SessionRegistration(Session session) {
		this.registeredSession = session;
	}
	
	public Session getRegisteredSession() {
		return registeredSession;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof Congress) {
			if (this == obj) {
				returnValue = true;
			} else {
				Session sessionToCheck = (Session) obj;
				returnValue = new EqualsBuilder()
					.append(registeredSession, sessionToCheck)
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
		.append(registeredSession)
		.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Registered Session",registeredSession)
		.toString();
	}
	
}
