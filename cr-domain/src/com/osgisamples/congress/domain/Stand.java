package com.osgisamples.congress.domain;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Stand extends BaseDomain {
	private static final long serialVersionUID = 7136222864070898223L;

	private Long id;
	private String company;
	
	private Set<StandKeeper> standKeepers;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<StandKeeper> getStandKeepers() {
		return standKeepers;
	}

	public void setStandKeepers(Set<StandKeeper> standKeepers) {
		this.standKeepers = standKeepers;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof Stand) {
			if (this == obj) {
				returnValue = true;
			} else {
				Stand standToCheck = (Stand) obj;
				returnValue = new EqualsBuilder()
					.append(id, standToCheck.getId())
					.append(company, standToCheck.getCompany())
					.isEquals();
			}
		}
		return returnValue;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("ID",id)
		.append("Company",company)
		.append("Stand keepers",standKeepers.toArray())
		.toString();
	}
}
