package com.osgisamples.congress.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Registrant extends BaseDomain {
	private static final long serialVersionUID = -2805016868966781769L;

	private Long id;
	private String name;
	private String company;
	private String emailAddress;
	private String registrationNumber;
	
	private Set<CongressRole> congressRoles = new HashSet<CongressRole>();

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Set<? extends CongressRole> getCongressRoles() {
		return congressRoles;
	}

	public void setCongressRoles(Set<CongressRole> congressRoles) {
		this.congressRoles = congressRoles;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof Registrant) {
			if (this == obj) {
				returnValue = true;
			} else {
				Registrant registrantToCheck = (Registrant) obj;
				returnValue = new EqualsBuilder()
					.append(id, registrantToCheck.getId())
					.append(emailAddress, registrantToCheck.getEmailAddress())
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
			.append(id)
			.append(emailAddress)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("ID",id)
		.append("Name",name)
		.append("Company",company)
		.append("Email Address",emailAddress)
		.append("Registration number",registrationNumber)
		.append("Congress Roles",congressRoles.toArray())
		.toString();
	}
}
