package com.osgisamples.congress.frontend.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTClient implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ShowSessionsComponent sessionsComponent = new ShowSessionsComponent();
		RootPanel.get().add(sessionsComponent);
		ShowRegistrantsComponent component = new ShowRegistrantsComponent();
		RootPanel.get().add(component);
	}

	public class DummyCommand implements Command {
		public void execute() {
			Window.alert("Menu item clicked");
		}
	}

}
