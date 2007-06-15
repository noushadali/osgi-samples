package com.osgisamples.congress.frontend.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Session implements IsSerializable {
	private String name;
	private int numListeners;
	
	public Session() {
		// needed by gwt
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumListeners() {
		return numListeners;
	}
	public void setNumListeners(int numListeners) {
		this.numListeners = numListeners;
	}
}
