package com.osgisamples.congress.frontend.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ShowSessionsComponent extends UpdateableComposite {
	private Panel composite = new VerticalPanel();
	private Label titleBar = new Label("Sessions");
	private Grid sessionsGrid;

	public ShowSessionsComponent() {
		initWidget(composite);
		composite.setStyleName("widget");
		titleBar.setStyleName("title");
		composite.add(titleBar);
		getSessionsFromServer();
		startUpdateTimer(1);
	}

	public void update() {
		getSessionsFromServer();
	}

	private void getSessionsFromServer() {
		CongressServiceAsync serviceproxy = (CongressServiceAsync) GWT.create(CongressService.class);
		ServiceDefTarget target = (ServiceDefTarget) serviceproxy;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "registrants");
		
		AsyncCallback callback = new AsyncCallback() {
			public void onFailure(Throwable caught) {
				GWT.log("Error", caught);
			}

			public void onSuccess(Object result) {
				ArrayList sessions = (ArrayList) result;
				showSessionsComponent(sessions);
			}
		};
		serviceproxy.listSessions(callback);
		
	}
	
	private void showSessionsComponent(ArrayList sessions) {
		if (sessionsGrid != null) {
			composite.remove(sessionsGrid);
		}
		if (sessions != null && !sessions.isEmpty()) {
			sessionsGrid = new Grid(sessions.size()+1,2);
			sessionsGrid.addStyleName("grid");
			Label columnName = new Label("Name");
			columnName.setStyleName("header-column");
			Label columnNumListeners = new Label("Number of listeners");
			columnNumListeners.setStyleName("header-column");
			addRowToGrid(columnName, columnNumListeners, 0);
			int rowNum = 0;
			for (Iterator rows = sessions.iterator(); rows.hasNext();) {
				rowNum++;
				Session session = (Session)rows.next();
				Label labelName = new Label(session.getName());
				Label labelNumListener = new Label(String.valueOf(session.getNumListeners()));
				labelNumListener.setStyleName("numlisteners");
				addRowToGrid(labelName, labelNumListener, rowNum);
			}
		}
		composite.add(sessionsGrid);
	}
	
	private void addRowToGrid(Widget widgetName, Widget widgetNumber, int row) {
		sessionsGrid.setWidget(row, 0, widgetName);
		sessionsGrid.setWidget(row, 1, widgetNumber);
	}
	
	private void addRowToGrid(String name, String number, int row) {
		sessionsGrid.setText(row, 0, name);
		sessionsGrid.setText(row, 1, number);
	}

}
