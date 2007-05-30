package com.osgisamples.congress.frontend.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTClient implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DockPanel dockPanel = new DockPanel();
		dockPanel.setStyleName("dock");
		Panel toppanel = getTopPanel();
		Panel bottompanel = getBottomPanel();
		dockPanel.add(toppanel,DockPanel.NORTH);
		dockPanel.setCellHeight(toppanel, "50px");
		dockPanel.add(getCenterPanel(), DockPanel.CENTER);
		dockPanel.add(bottompanel,DockPanel.SOUTH);
		dockPanel.setCellHeight(bottompanel, "50px");
		dockPanel.add(getMenupanel(), DockPanel.WEST);
		RootPanel.get().add(dockPanel);
	}

	private Panel getMenupanel() {
		VerticalPanel menuPanel = new VerticalPanel();
		menuPanel.setStyleName("menu");
		menuPanel.add(createMenuItem("NLJug J-Spring 2007"));
		menuPanel.add(createMenuItem("SpringOne 2007"));
		menuPanel.add(createMenuItem("NLJug J-Fall 2007"));
		menuPanel.add(createMenuItem("Spring Experience 2007"));
		return menuPanel;
	}
	private Label createMenuItem(String text) {
		Label menuPanelItem = new Label(text);
		menuPanelItem.setStyleName("menuitem");
		return menuPanelItem;
	}
	
	private Panel getCenterPanel() {
		VerticalPanel centerPanel = new VerticalPanel();
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		centerPanel.setWidth("100%");
		Label centerPanelTitle = new Label("NLJug J-Spring 2007");
		centerPanelTitle.setStyleName("title");
		HorizontalPanel dataPanel = new HorizontalPanel();
		
		ShowSessionsComponent sessionsComponent = new ShowSessionsComponent();
		ShowRegistrantsComponent registrantsComponent = new ShowRegistrantsComponent();
		dataPanel.add(sessionsComponent);
		dataPanel.add(registrantsComponent);
		centerPanel.add(centerPanelTitle);
		centerPanel.add(dataPanel);
		return centerPanel;
	}

	private Panel getTopPanel() {
		Label topLabel = new Label("Congres Registration system");
		Image osgisamplesLogo = new Image("osgisampleslogo.jpg");
		HorizontalPanel topDockItem = new HorizontalPanel();
		topDockItem.setWidth("100%");
		topDockItem.setHeight("50px");
		topDockItem.setStyleName("pagetitle");
		topDockItem.add(topLabel);
		topDockItem.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		topDockItem.add(osgisamplesLogo);
		return topDockItem;
	}
	
	private Panel getBottomPanel() {
		Label poweredBy = new Label("Powered by Allard Buijze and Jettro Coenradie");
		HorizontalPanel bottomPanel = new HorizontalPanel();
		bottomPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		bottomPanel.setHeight("15px");
		bottomPanel.setStyleName("bottom");
		bottomPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bottomPanel.add(poweredBy);
		return bottomPanel;
	}
}
