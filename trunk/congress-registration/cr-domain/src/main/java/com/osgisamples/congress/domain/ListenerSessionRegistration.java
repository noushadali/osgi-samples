package com.osgisamples.congress.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ListenerSessionRegistration extends SessionRegistration {
	private static final long serialVersionUID = 4131310408506924257L;

	private Participant listener;

	public ListenerSessionRegistration(Session session, Participant listener) {
		super(session);
		session.getListeners().add(this);
		this.listener = listener;
	}


	public Participant getListener() {
		return listener;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof ListenerSessionRegistration) {
			if (this == obj) {
				returnValue = true;
			} else {
				ListenerSessionRegistration listenerRegistrationToCheck = (ListenerSessionRegistration) obj;
				returnValue = new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(listener, listenerRegistrationToCheck)
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
		.appendSuper(super.hashCode())
		.append(listener)
		.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.appendSuper("Session Registration")
		.append("Listener",listener)
		.toString();
	}
}
