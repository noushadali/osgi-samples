package com.osgisamples.congress.frontend.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CongressServiceAsync {
	void listRegistrants(AsyncCallback callback);
	void listSessions(AsyncCallback callback);
}
