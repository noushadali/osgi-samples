package com.osgisamples.congress.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class CongressRole extends BaseDomain {
	private Long id;
	private Registrant registrant;
	
	public Registrant getRegistrant() {
		return registrant;
	}

	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof CongressRole) {
			if (this == obj) {
				returnValue = true;
			} else {
				CongressRole congressRole = (CongressRole) obj;
				returnValue = new EqualsBuilder()
					.append(id, congressRole.getId())
					.append(registrant, congressRole.getRegistrant())
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
		.append(id)
		.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",id)
		.append("Registrant",registrant)
		.toString();
	}
	
}
