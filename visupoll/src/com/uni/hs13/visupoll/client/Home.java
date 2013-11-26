package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Home implements EntryPoint {
	
	
	VerticalPanel vContentPanel;
	VerticalPanel vSidebarPanel;
	VerticalPanel vCopyrightPanel;
	HorizontalPanel hMasterPanel;
		

	@Override
	public void onModuleLoad() {
	
	//generate Panels
	vContentPanel = new VerticalPanel(); 					// Content of Page (tabellaric, geographic view)
	vContentPanel.setSpacing(10);
	vContentPanel.setWidth("600px");						// special Panels are made so that they can be adjusted in width
		
	vSidebarPanel = new VerticalPanel(); 					// Help, About, Comment and Share buttons
	vSidebarPanel.setSpacing(10);
	
	
	//Load Widgets
	TabellaricViewWidget tabView = new TabellaricViewWidget();
	//GeographicViewWidget geoView = new GeographicViewWidget();
	SidebarWidget sidebar = new SidebarWidget();
	CopyrightWidget copyright = new CopyrightWidget();
	
	vContentPanel.add(tabView);
	vContentPanel.add(copyright);
	//vPanel_3.add(geoView);
	vSidebarPanel.add(sidebar);

	hMasterPanel = new HorizontalPanel(); 					// final panel
	hMasterPanel.setSpacing(10);
	hMasterPanel.add(vContentPanel);
	hMasterPanel.add(vSidebarPanel);

	AbsolutePanel aPanel = new AbsolutePanel();
	aPanel.add(hMasterPanel);

	RootPanel.get().add(aPanel);

	}
}
