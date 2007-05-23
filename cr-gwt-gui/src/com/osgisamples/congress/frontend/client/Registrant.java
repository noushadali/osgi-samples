package com.osgisamples.congress.frontend.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Registrant implements IsSerializable {
	private Long id;
	private String name;
	private String company;
	private String emailAddress;
	private String registrationNumber;
	
	public Registrant() {
		// needed by gwt
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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

}
