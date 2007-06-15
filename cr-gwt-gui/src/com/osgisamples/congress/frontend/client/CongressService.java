package com.osgisamples.congress.frontend.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public interface CongressService extends RemoteService {
	/**
	 * @gwt.typeArgs <com.osgisamples.congress.frontend.client.Registrant> 
	 */
	public ArrayList listRegistrants();

	/**
	 * @gwt.typeArgs <com.osgisamples.congress.frontend.client.Session> 
	 */
	public ArrayList listSessions();
}
