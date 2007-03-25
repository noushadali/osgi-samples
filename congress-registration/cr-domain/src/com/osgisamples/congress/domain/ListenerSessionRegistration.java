package com.osgisamples.congress.domain;

public class ListenerSessionRegistration extends SessionRegistration {
	private static final long serialVersionUID = 4131310408506924257L;

	private Participant listener;

	public Participant getListener() {
		return listener;
	}

	public void setListener(Participant listener) {
		this.listener = listener;
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
