package com.osgisamples.congress.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Session extends BaseDomain {
	private static final long serialVersionUID = 1571535785472684693L;

	private Long id;
	private String name;
	private String summary;
	private Congress congress;
	
	private Set<SpeakerSessionRegistration> speakers = new HashSet<SpeakerSessionRegistration>();
	private Set<ListenerSessionRegistration> listeners = new HashSet<ListenerSessionRegistration>();

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
		boolean returnValue = false;
		if (obj instanceof Session) {
			if (this == obj) {
				returnValue = true;
			} else {
				Session sessionToCheck = (Session) obj;
				returnValue = new EqualsBuilder()
					.append(id, sessionToCheck.getId())
					.append(name, sessionToCheck.getName())
					.isEquals();
			}
		}
		return returnValue;
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,27)
		.append(name)
		.toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("ID",id)
		.append("Name",name)
		.append("Summary",summary)
		.append("Congress",congress)
		.append("Listeners",listeners.toArray())
		.append("Speakers",speakers.toArray())
		.toString();
	}
}
