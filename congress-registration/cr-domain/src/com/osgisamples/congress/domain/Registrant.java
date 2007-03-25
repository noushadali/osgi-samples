package com.osgisamples.congress.domain;

import java.util.Set;

public class Registrant extends BaseDomain {
	private static final long serialVersionUID = -2805016868966781769L;

	private Long id;
	private String name;
	private String company;
	private String emailAddress;
	private String registrationNumber;
	
	private Set<? extends CongressRole> congressRoles;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Set<? extends CongressRole> getCongressRoles() {
		return congressRoles;
	}

	public void setCongressRoles(Set<? extends CongressRole> congressRoles) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
