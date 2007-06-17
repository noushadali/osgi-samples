package com.osgisamples.congress.domain;

public class Participant extends CongressRole {
	public Participant(Registrant registrant) {
		super(registrant);
	}

	private static final long serialVersionUID = 7502904163083167367L;
}
