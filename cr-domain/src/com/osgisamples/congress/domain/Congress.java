package com.osgisamples.congress.domain;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author Jettro.Coenradie
 */
public class Congress extends BaseDomain {
	private static final long serialVersionUID = 8698712257911318289L;
	private Long id;
	private String name;
	private String description;
	private Calendar startDateTime;
	private Calendar endDateTime;
	
	private Set<Session> sessions = new HashSet<Session>();
	private Set<CongressRegistration> registrations = new HashSet<CongressRegistration>();
	private Set<Stand> stands = new HashSet<Stand>();
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Calendar getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
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
	public Set<CongressRegistration> getRegistrations() {
		return registrations;
	}
	public void setRegistrations(Set<CongressRegistration> registrations) {
		this.registrations = registrations;
	}
	public Set<Session> getSessions() {
		return sessions;
	}
	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}
	public Set<Stand> getStands() {
		return stands;
	}
	public void setStands(Set<Stand> stands) {
		this.stands = stands;
	}
	public Calendar getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}
	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof Congress) {
			if (this == obj) {
				returnValue = true;
			} else {
				Congress congressToCheck = (Congress) obj;
				returnValue = new EqualsBuilder()
					.append(id, congressToCheck.getId())
					.append(name, congressToCheck.getName())
					.isEquals();
			}
		}
		return returnValue;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("ID",id)
			.append("Name",name)
			.append("Description",description)
			.append("StartDate",startDateTime)
			.append("EndDate",endDateTime)
			.append("Sessions",sessions.toArray())
			.append("Registrations",registrations.toArray())
			.append("Stands",stands.toArray())
			.toString();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,23)
			.append(name)
			.toHashCode();
	}
}
 