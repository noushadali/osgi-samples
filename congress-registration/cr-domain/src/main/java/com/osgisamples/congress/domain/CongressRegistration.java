package com.osgisamples.congress.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CongressRegistration extends BaseDomain {
	private static final long serialVersionUID = 3940000896484270343L;

	private Congress registeredCongress;
	private Registrant registrant;
	
	public CongressRegistration(Congress congress, Registrant registrant) {
		this.registeredCongress = congress;
		this.registrant = registrant;
		congress.getRegistrations().add(this);
	}
	
	public Congress getRegisteredCongress() {
		return registeredCongress;
	}
	public void setRegisteredCongress(Congress registeredCongress) {
		this.registeredCongress = registeredCongress;
	}
	public Registrant getRegistrant() {
		return registrant;
	}
	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof CongressRegistration) {
			if (this == obj) {
				returnValue = true;
			} else {
				CongressRegistration congressRegistrationToCheck = (CongressRegistration) obj;
				returnValue = new EqualsBuilder()
					.append(registeredCongress, congressRegistrationToCheck.getRegisteredCongress())
					.append(registrant, congressRegistrationToCheck.getRegistrant())
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
		.append(registeredCongress)
		.append(registrant)
		.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Congress",registeredCongress)
		.append("Registrant",registrant)
		.toString();
	}
}
