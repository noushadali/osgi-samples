package com.osgisamples.congress.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SpeakerSessionRegistration extends SessionRegistration {
	private static final long serialVersionUID = -4661510543850568533L;

	private Speaker speaker;

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof SpeakerSessionRegistration) {
			if (this == obj) {
				returnValue = true;
			} else {
				SpeakerSessionRegistration speakerRegistrationToCheck = (SpeakerSessionRegistration) obj;
				returnValue = new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(speaker, speakerRegistrationToCheck)
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
		.appendSuper(super.hashCode())
		.append(speaker)
		.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.appendSuper("Session Registration")
		.append("Speaker",speaker)
		.toString();
	}
}
