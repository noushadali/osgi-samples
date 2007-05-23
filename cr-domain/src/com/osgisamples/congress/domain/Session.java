package com.osgisamples.congress.domain;

import java.util.Set;

public class Session extends BaseDomain {
	private static final long serialVersionUID = 1571535785472684693L;

	private Long id;
	private String name;
	private String summary;
	private Congress congress;
	
	private Set<SpeakerSessionRegistration> speakers;
	private Set<ListenerSessionRegistration> listeners;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<ListenerSessionRegistration> getListeners() {
		return listeners;
	}
	public void setListeners(Set<ListenerSessionRegistration> listeners) {
		this.listeners = listeners;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<SpeakerSessionRegistration> getSpeakers() {
		return speakers;
	}
	public void setSpeakers(Set<SpeakerSessionRegistration> speakers) {
		this.speakers = speakers;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Congress getCongress() {
		return congress;
	}
	public void setCongress(Congress congress) {
		this.congress = congress;
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
