package com.osgisamples.congress.domain;

import java.util.Set;

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
